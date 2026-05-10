package com.example.eventhub2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // POST /api/auth/register
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, Object> body) {
        String nama     = (String) body.get("nama");
        String email    = (String) body.get("email");
        String password = (String) body.get("password");
        String role     = (String) body.get("role");

        if (nama == null || email == null || password == null) {
            return Map.of("success", false, "message", "Semua field wajib diisi!");
        }
        if (userRepository.existsByEmail(email)) {
            return Map.of("success", false, "message", "Email sudah terdaftar!");
        }

        User user = new User();
        user.setNama(nama);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role != null ? role : "peserta");
        userRepository.save(user);

        return Map.of("success", true, "message", "Akun berhasil dibuat!");
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> body) {
        String email    = (String) body.get("email");
        String password = (String) body.get("password");

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            return Map.of("success", false, "message", "Email atau password salah!");
        }

        User user = userOpt.get();
        Map<String, Object> result = new HashMap<>();
        result.put("success",   true);
        result.put("token",     "TOKEN-" + user.getId());
        result.put("id",        user.getId());
        result.put("nama",      user.getNama());
        result.put("email",     user.getEmail());
        result.put("role",      user.getRole());
        result.put("telp",      user.getTelp());
        result.put("kota",      user.getKota());
        result.put("institusi", user.getInstitusi());
        return result;
    }

    // PUT /api/auth/update/{id}
    @PutMapping("/update/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return Map.of("success", false, "message", "User tidak ditemukan!");
        }

        User user = userOpt.get();
        if (body.containsKey("nama"))      user.setNama((String) body.get("nama"));
        if (body.containsKey("telp"))      user.setTelp((String) body.get("telp"));
        if (body.containsKey("kota"))      user.setKota((String) body.get("kota"));
        if (body.containsKey("institusi")) user.setInstitusi((String) body.get("institusi"));
        if (body.containsKey("password") && !((String) body.get("password")).isBlank()) {
            user.setPassword((String) body.get("password"));
        }
        userRepository.save(user);

        Map<String, Object> result = new HashMap<>();
        result.put("success",   true);
        result.put("message",   "Profil berhasil diperbarui!");
        result.put("nama",      user.getNama());
        result.put("telp",      user.getTelp());
        result.put("kota",      user.getKota());
        result.put("institusi", user.getInstitusi());
        return result;
    }

    // DELETE /api/auth/delete/{id}
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return Map.of("success", false, "message", "User tidak ditemukan!");
        }
        userRepository.deleteById(id);
        return Map.of("success", true, "message", "Akun berhasil dihapus!");
    }
}