package com.example.eventhub2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/laporan")
@CrossOrigin(origins = "*")
public class LaporanController {

    @Autowired
    private LaporanRepository laporanRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PendaftaranRepository pendaftaranRepository;

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private TiketRepository tiketRepository;

    // GET /api/laporan/penyelenggara/{userId}
    @GetMapping("/penyelenggara/{userId}")
    public Map<String, Object> getLaporanPenyelenggara(@PathVariable Long userId) {
        List<Event> events = eventRepository.findByCreatedBy(userId);

        List<Map<String, Object>> hasil = events.stream().map(ev -> {
            Map<String, Object> m = new HashMap<>();
            m.put("eventId",   ev.getId());
            m.put("namaEvent", ev.getNama());
            m.put("tanggal",   ev.getTanggal());
            m.put("kapasitas", ev.getKapasitas());
            m.put("terisi",    ev.getTerisi());

            long totalCheckin = pendaftaranRepository.findAll().stream()
                .filter(p -> p.getEventId().equals(ev.getId()))
                .flatMap(p -> tiketRepository.findByPendaftaranId(p.getId()).stream())
                .filter(t -> "digunakan".equals(t.getStatusTiket()))
                .count();
            m.put("totalCheckin", totalCheckin);

            long pendapatan = 0;
            if (!"gratis".equalsIgnoreCase(ev.getTipeHarga())) {
                try {
                    pendapatan = Long.parseLong(ev.getHarga()) * ev.getTerisi();
                } catch (Exception ignored) {}
            }
            m.put("totalPendapatan", pendapatan);
            return m;
        }).collect(Collectors.toList());

        return Map.of("success", true, "laporan", hasil, "totalEvent", events.size());
    }

    // GET /api/laporan/event/{eventId}
    @GetMapping("/event/{eventId}")
    public Map<String, Object> getLaporanEvent(@PathVariable Long eventId) {
        Optional<Event> evOpt = eventRepository.findById(eventId);
        if (evOpt.isEmpty()) {
            return Map.of("success", false, "message", "Event tidak ditemukan!");
        }

        Event ev = evOpt.get();

        List<Pendaftaran> peserta = pendaftaranRepository.findAll().stream()
            .filter(p -> p.getEventId().equals(eventId))
            .collect(Collectors.toList());

        long totalCheckin = peserta.stream()
            .flatMap(p -> tiketRepository.findByPendaftaranId(p.getId()).stream())
            .filter(t -> "digunakan".equals(t.getStatusTiket()))
            .count();

        Map<String, Object> result = new HashMap<>();
        result.put("success",      true);
        result.put("namaEvent",    ev.getNama());
        result.put("tanggal",      ev.getTanggal());
        result.put("kapasitas",    ev.getKapasitas());
        result.put("totalPeserta", peserta.size());
        result.put("totalCheckin", totalCheckin);
        return result;
    }

    // POST /api/laporan/generate/{eventId}
    @PostMapping("/generate/{eventId}")
    public Map<String, Object> generateLaporan(
            @PathVariable Long eventId,
            @RequestBody Map<String, Object> body) {

        Optional<Event> evOpt = eventRepository.findById(eventId);
        if (evOpt.isEmpty()) {
            return Map.of("success", false, "message", "Event tidak ditemukan!");
        }

        Event ev = evOpt.get();
        Long penyelenggaraId = Long.parseLong(body.get("penyelenggaraId").toString());

        List<Pendaftaran> peserta = pendaftaranRepository.findAll().stream()
            .filter(p -> p.getEventId().equals(eventId))
            .collect(Collectors.toList());

        long totalCheckin = peserta.stream()
            .flatMap(p -> tiketRepository.findByPendaftaranId(p.getId()).stream())
            .filter(t -> "digunakan".equals(t.getStatusTiket()))
            .count();

        long pendapatan = 0;
        if (!"gratis".equalsIgnoreCase(ev.getTipeHarga())) {
            try {
                pendapatan = Long.parseLong(ev.getHarga()) * peserta.size();
            } catch (Exception ignored) {}
        }

        Laporan laporan = laporanRepository.findByEventId(eventId).orElse(new Laporan());
        laporan.setEventId(eventId);
        laporan.setPenyelenggaraId(penyelenggaraId);
        laporan.setTotalPeserta(peserta.size());
        laporan.setTotalCheckin((int) totalCheckin);
        laporan.setTotalPendapatan(pendapatan);
        laporanRepository.save(laporan);

        return Map.of(
            "success",          true,
            "message",          laporan.generateLaporan(),
            "totalPeserta",     peserta.size(),
            "totalCheckin",     totalCheckin,
            "totalPendapatan",  pendapatan
        );
    }
}