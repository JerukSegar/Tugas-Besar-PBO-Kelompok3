package com.example.eventhub2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ForgotPasswordController
 * Menangani alur lupa password dengan OTP dummy (untuk keperluan demo/tugas).
 *
 * Flow:
 *   POST /api/forgot-password/request    → cek email, generate OTP dummy
 *   POST /api/forgot-password/verify-otp → verifikasi kode OTP
 *   POST /api/forgot-password/reset      → simpan password baru
 */
@RestController
@RequestMapping("/api/forgot-password")
public class ForgotPasswordController {

    // OTP dummy statis — sesuai permintaan dosen (tidak perlu kirim email)
    private static final String DUMMY_OTP = "123456";

    // Simpan sesi OTP sementara di memori: email -> OTP
    // (Untuk produksi gunakan database / Redis dengan expiry)
    private final Map<String, String> otpStore = new HashMap<>();

    @Autowired
    private UserRepository userRepository;

    // ----------------------------------------------------------------
    // 1. Request OTP — cek apakah email terdaftar
    // ----------------------------------------------------------------
    @PostMapping("/request")
    public ResponseEntity<Map<String, Object>> requestOtp(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String email = body.get("email");

        if (email == null || email.isBlank()) {
            response.put("success", false);
            response.put("message", "Email tidak boleh kosong.");
            return ResponseEntity.badRequest().body(response);
        }

        // Cek apakah email terdaftar di database
        Optional<User> userOpt = userRepository.findByEmail(email.trim());
        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Email tidak ditemukan. Pastikan email sudah terdaftar.");
            return ResponseEntity.ok(response);
        }

        // Simpan OTP dummy ke store sementara
        otpStore.put(email.trim(), DUMMY_OTP);

        // Di sini seharusnya kirim email — tapi karena dummy, kita skip
        System.out.println("[DEBUG] OTP untuk " + email + " adalah: " + DUMMY_OTP);

        response.put("success", true);
        response.put("message", "Kode OTP telah 'dikirim' ke email Anda.");
        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------
    // 2. Verify OTP — cocokkan kode yang dimasukkan user
    // ----------------------------------------------------------------
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String email = body.get("email");
        String otp   = body.get("otp");

        if (email == null || otp == null) {
            response.put("success", false);
            response.put("message", "Data tidak lengkap.");
            return ResponseEntity.badRequest().body(response);
        }

        String storedOtp = otpStore.get(email.trim());

        if (storedOtp == null) {
            response.put("success", false);
            response.put("message", "Sesi OTP tidak ditemukan. Silakan ulangi dari awal.");
            return ResponseEntity.ok(response);
        }

        if (!storedOtp.equals(otp.trim())) {
            response.put("success", false);
            response.put("message", "Kode OTP salah. Periksa kembali kode Anda.");
            return ResponseEntity.ok(response);
        }

        // OTP cocok — tandai verified (masih simpan di store untuk step reset)
        response.put("success", true);
        response.put("message", "OTP berhasil diverifikasi.");
        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------
    // 3. Reset Password — simpan password baru ke database
    // ----------------------------------------------------------------
    @PostMapping("/reset")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String email       = body.get("email");
        String newPassword = body.get("newPassword");

        if (email == null || newPassword == null || newPassword.isBlank()) {
            response.put("success", false);
            response.put("message", "Data tidak lengkap.");
            return ResponseEntity.badRequest().body(response);
        }

        // Pastikan OTP sudah pernah di-request untuk email ini
        if (!otpStore.containsKey(email.trim())) {
            response.put("success", false);
            response.put("message", "Sesi reset tidak valid. Silakan ulangi dari awal.");
            return ResponseEntity.ok(response);
        }

        // Cari user dan update password
        Optional<User> userOpt = userRepository.findByEmail(email.trim());
        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "User tidak ditemukan.");
            return ResponseEntity.ok(response);
        }

        User user = userOpt.get();

        // ⚠️ CATATAN: Jika project menggunakan BCrypt, uncomment baris di bawah:
        // user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        // Untuk sementara (plain text / sesuai implementasi existing):
        user.setPassword(newPassword);

        userRepository.save(user);

        // Hapus OTP dari store setelah berhasil
        otpStore.remove(email.trim());

        response.put("success", true);
        response.put("message", "Password berhasil diubah. Silakan login.");
        return ResponseEntity.ok(response);
    }
}