package com.example.eventhub2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/tiket")
@CrossOrigin(origins = "*")
public class TiketController {

    @Autowired
    private TiketRepository tiketRepository;

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private PendaftaranRepository pendaftaranRepository;

    @Autowired
    private EventRepository eventRepository;

    // ── Helper: cek apakah event sudah selesai ────────────────────────────────
    private boolean isEventSudahSelesai(Event ev) {
        try {
            LocalDate tglEvent = LocalDate.parse(
                ev.getTanggal().trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            );
            String[] parts = ev.getWaktu().split("[\\u2013\\-]");
            if (parts.length < 2) return false;

            String selesaiStr = parts[1].trim().replace(".", ":").replaceAll("[^0-9:]", "");
            LocalTime selesai = LocalTime.parse(selesaiStr, DateTimeFormatter.ofPattern("HH:mm"));
            LocalDate today   = LocalDate.now();
            LocalTime now     = LocalTime.now();

            if (today.isAfter(tglEvent)) return true;
            if (today.isEqual(tglEvent) && now.isAfter(selesai)) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // GET /api/tiket/pendaftaran/{pendaftaranId}
    @GetMapping("/pendaftaran/{pendaftaranId}")
    public Map<String, Object> getTiketByPendaftaran(@PathVariable Long pendaftaranId) {
        List<Tiket> list = tiketRepository.findByPendaftaranId(pendaftaranId);
        return Map.of("success", true, "tiket", list);
    }

    // POST /api/tiket/checkin
    @PostMapping("/checkin")
    public Map<String, Object> checkIn(@RequestBody Map<String, Object> body) {
        String kodeQr        = body.get("kodeQr").toString();
        Long penyelenggaraId = Long.parseLong(body.get("penyelenggaraId").toString());

        Optional<Tiket> tiketOpt = tiketRepository.findByKodeQr(kodeQr);
        if (tiketOpt.isEmpty()) {
            return Map.of("success", false, "message", "Tiket tidak ditemukan!");
        }

        Tiket tiket = tiketOpt.get();

        if ("digunakan".equals(tiket.getStatusTiket())) {
            return Map.of("success", false, "message", "Tiket sudah digunakan!");
        }
        if ("kadaluarsa".equals(tiket.getStatusTiket())) {
            return Map.of("success", false, "message", "Tiket sudah kadaluarsa!");
        }
        if (checkInRepository.existsByTiketId(tiket.getId())) {
            return Map.of("success", false, "message", "Peserta sudah check-in!");
        }

        // Cek apakah event sudah selesai
        Optional<Pendaftaran> pendOpt = pendaftaranRepository.findById(tiket.getPendaftaranId());
        if (pendOpt.isPresent()) {
            Optional<Event> evOpt = eventRepository.findById(pendOpt.get().getEventId());
            if (evOpt.isPresent() && isEventSudahSelesai(evOpt.get())) {
                return Map.of(
                    "success", false,
                    "message", "Check-in gagal! Waktu event sudah selesai (" + evOpt.get().getWaktu() + ")."
                );
            }
        }

        // Simpan check-in
        CheckIn checkIn = new CheckIn();
        checkIn.setTiketId(tiket.getId());
        checkIn.setPenyelenggaraId(penyelenggaraId);
        checkInRepository.save(checkIn);

        tiket.setStatusTiket("digunakan");
        tiketRepository.save(tiket);

        return Map.of("success", true, "message", "Check-in berhasil!");
    }
}