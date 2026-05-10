package com.example.eventhub2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PendaftaranRepository pendaftaranRepository;

    @Autowired
    private TiketRepository tiketRepository;


    // ── Helper: cek apakah event sudah mulai ─────────────────────────────────
    private boolean isEventSudahMulai(Event ev) {
        try {
            LocalDate tglEvent = LocalDate.parse(
                ev.getTanggal().trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            );
            String[] parts  = ev.getWaktu().split("[\\u2013\\-]");
            String mulaiStr = parts[0].trim().replace(".", ":").replaceAll("[^0-9:]", "");
            LocalTime mulai = LocalTime.parse(mulaiStr, DateTimeFormatter.ofPattern("HH:mm"));
            LocalDate today = LocalDate.now();
            LocalTime now   = LocalTime.now();

            if (today.isAfter(tglEvent)) return true;
            if (today.isEqual(tglEvent) && now.isAfter(mulai)) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // GET /api/events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // GET /api/events/{id}
    @GetMapping("/{id}")
    public Map<String, Object> getEventById(@PathVariable Long id) {
        Optional<Event> ev = eventRepository.findById(id);
        if (ev.isEmpty()) {
            return Map.of("success", false, "message", "Event tidak ditemukan!");
        }
        return Map.of("success", true, "event", ev.get());
    }

    // POST /api/events
    @PostMapping
    public Map<String, Object> createEvent(@RequestBody Map<String, Object> body) {
        if (body.get("nama") == null) {
            return Map.of("success", false, "message", "Field wajib tidak boleh kosong!");
        }

        Event ev = new Event();
        ev.setNama((String) body.get("nama"));
        ev.setKategori((String) body.get("kategori"));
        ev.setTanggal((String) body.get("tanggal"));
        ev.setWaktu((String) body.get("waktu"));
        ev.setLokasi((String) body.get("lokasi"));
        ev.setVenue((String) body.get("venue"));
        ev.setDeskripsi((String) body.get("deskripsi"));
        ev.setPenyelenggara((String) body.get("penyelenggara"));
        ev.setHarga((String) body.get("harga"));
        ev.setTipeHarga((String) body.get("tipeHarga"));
        ev.setKapasitas(Integer.parseInt(body.get("kapasitas").toString()));
        ev.setTerisi(0);
        ev.setCreatedBy(Long.parseLong(body.get("createdBy").toString()));
        eventRepository.save(ev);

        return Map.of("success", true, "message", "Event berhasil dibuat!", "id", ev.getId());
    }

    // PUT /api/events/{id}
    @PutMapping("/{id}")
    public Map<String, Object> updateEvent(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Optional<Event> evOpt = eventRepository.findById(id);
        if (evOpt.isEmpty()) {
            return Map.of("success", false, "message", "Event tidak ditemukan!");
        }

        Event ev = evOpt.get();
        Long createdBy = Long.parseLong(body.get("createdBy").toString());
        if (!ev.getCreatedBy().equals(createdBy)) {
            return Map.of("success", false, "message", "Kamu tidak berhak mengedit event ini!");
        }

        if (body.containsKey("nama"))          ev.setNama((String) body.get("nama"));
        if (body.containsKey("kategori"))      ev.setKategori((String) body.get("kategori"));
        if (body.containsKey("tanggal"))       ev.setTanggal((String) body.get("tanggal"));
        if (body.containsKey("waktu"))         ev.setWaktu((String) body.get("waktu"));
        if (body.containsKey("lokasi"))        ev.setLokasi((String) body.get("lokasi"));
        if (body.containsKey("venue"))         ev.setVenue((String) body.get("venue"));
        if (body.containsKey("deskripsi"))     ev.setDeskripsi((String) body.get("deskripsi"));
        if (body.containsKey("penyelenggara")) ev.setPenyelenggara((String) body.get("penyelenggara"));
        if (body.containsKey("harga"))         ev.setHarga((String) body.get("harga"));
        if (body.containsKey("tipeHarga"))     ev.setTipeHarga((String) body.get("tipeHarga"));
        if (body.containsKey("kapasitas"))     ev.setKapasitas(Integer.parseInt(body.get("kapasitas").toString()));
        eventRepository.save(ev);

        return Map.of("success", true, "message", "Event berhasil diperbarui!");
    }

    // DELETE /api/events/{id}
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteEvent(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Optional<Event> evOpt = eventRepository.findById(id);
        if (evOpt.isEmpty()) {
            return Map.of("success", false, "message", "Event tidak ditemukan!");
        }

        Event ev = evOpt.get();
        Long createdBy = Long.parseLong(body.get("createdBy").toString());
        if (!ev.getCreatedBy().equals(createdBy)) {
            return Map.of("success", false, "message", "Kamu tidak berhak menghapus event ini!");
        }

        eventRepository.delete(ev);
        return Map.of("success", true, "message", "Event berhasil dihapus!");
    }

    // GET /api/events/kelola/{userId}
    @GetMapping("/kelola/{userId}")
    public List<Event> getEventByPenyelenggara(@PathVariable Long userId) {
        return eventRepository.findByCreatedBy(userId);
    }

    // POST /api/events/daftar
    @PostMapping("/daftar")
    public Map<String, Object> daftarEvent(@RequestBody Map<String, Object> body) {
        Long userId  = Long.parseLong(body.get("userId").toString());
        Long eventId = Long.parseLong(body.get("eventId").toString());

        Optional<Event> evOpt = eventRepository.findById(eventId);
        if (evOpt.isEmpty()) {
            return Map.of("success", false, "message", "Event tidak ditemukan!");
        }

        Event ev = evOpt.get();
        if (isEventSudahMulai(ev)) {
            return Map.of("success", false, "message", "Pendaftaran ditutup! Event ini sudah dimulai.");
        }
        if (ev.getTerisi() >= ev.getKapasitas()) {
            return Map.of("success", false, "message", "Event sudah penuh!");
        }
        if (pendaftaranRepository.existsByUserIdAndEventId(userId, eventId)) {
            return Map.of("success", false, "message", "Kamu sudah terdaftar di event ini!");
        }

        String kode = "EVH-" + java.time.Year.now().getValue() + "-"
                + String.format("%03d", userId) + String.format("%03d", eventId);

        Pendaftaran p = new Pendaftaran();
        p.setUserId(userId);
        p.setEventId(eventId);
        p.setKodeTiket(kode);
        p.setStatus("upcoming");
        pendaftaranRepository.save(p);

        // Generate tiket gratis atau berbayar
        Tiket tiket;
        if ("gratis".equalsIgnoreCase(ev.getTipeHarga())) {
            TiketGratis tg = new TiketGratis();
            tg.setPendaftaranId(p.getId());
            tg.setKodeQr("QR-" + kode);
            tg.generateTiketLangsung();
            tiket = tg;
        } else {
            TiketBerbayar tb = new TiketBerbayar();
            tb.setPendaftaranId(p.getId());
            tb.setKodeQr("QR-" + kode);
            tiket = tb;
        }
        tiketRepository.save(tiket);

        ev.setTerisi(ev.getTerisi() + 1);
        eventRepository.save(ev);

        return Map.of(
            "success",   true,
            "message",   "Berhasil mendaftar event!",
            "kodeTiket", kode,
            "kodeQr",    "QR-" + kode,
            "tipeHarga", ev.getTipeHarga()
        );
    }

    // GET /api/events/saya/{userId}
    @GetMapping("/saya/{userId}")
    public List<Map<String, Object>> getEventSaya(@PathVariable Long userId) {
        List<Pendaftaran> list = pendaftaranRepository.findByUserId(userId);
        return list.stream().map(p -> {
            Map<String, Object> m = new HashMap<>();
            Optional<Event> evOpt = eventRepository.findById(p.getEventId());
            if (evOpt.isPresent()) {
                Event ev = evOpt.get();
                m.put("eventId",       ev.getId());
                m.put("nama",          ev.getNama());
                m.put("kategori",      ev.getKategori());
                m.put("tanggal",       ev.getTanggal());
                m.put("waktu",         ev.getWaktu());
                m.put("lokasi",        ev.getLokasi());
                m.put("venue",         ev.getVenue());
                m.put("penyelenggara", ev.getPenyelenggara());
                m.put("harga",         ev.getHarga());
                m.put("tipeHarga",     ev.getTipeHarga());
            }
            m.put("pendaftaranId", p.getId());
            m.put("kodeTiket",     p.getKodeTiket());
            m.put("status",        p.getStatus());
            return m;
        }).collect(Collectors.toList());
    }

    // POST /api/events/batal
    @Transactional
    @PostMapping("/batal")
    public Map<String, Object> batalDaftar(@RequestBody Map<String, Object> body) {
        Long userId  = Long.parseLong(body.get("userId").toString());
        Long eventId = Long.parseLong(body.get("eventId").toString());

        Pendaftaran p = pendaftaranRepository.findByUserIdAndEventId(userId, eventId);
        if (p == null) {
            return Map.of("success", false, "message", "Data pendaftaran tidak ditemukan!");
        }

        List<Tiket> tikets = tiketRepository.findByPendaftaranId(p.getId());
        boolean sudahCheckIn = tikets.stream().anyMatch(t -> "digunakan".equals(t.getStatusTiket()));
        if (sudahCheckIn) {
            return Map.of("success", false, "message", "Tidak dapat dibatalkan karena sudah check-in!");
        }

        if (!tikets.isEmpty()) {
            tiketRepository.deleteAll(tikets);
        }
        pendaftaranRepository.deleteByUserIdAndEventId(userId, eventId);

        Optional<Event> evOpt = eventRepository.findById(eventId);
        evOpt.ifPresent(ev -> {
            ev.setTerisi(Math.max(0, ev.getTerisi() - 1));
            eventRepository.save(ev);
        });

        return Map.of("success", true, "message", "Pendaftaran berhasil dibatalkan!");
    }

    // GET /api/events/peserta/{eventId}
    @GetMapping("/peserta/{eventId}")
    public Map<String, Object> getPesertaEvent(@PathVariable Long eventId) {
        Optional<Event> evOpt = eventRepository.findById(eventId);
        if (evOpt.isEmpty()) {
            return Map.of("success", false, "message", "Event tidak ditemukan!");
        }

        List<Pendaftaran> list = pendaftaranRepository.findAll()
            .stream()
            .filter(p -> p.getEventId().equals(eventId))
            .collect(Collectors.toList());

        return Map.of("success", true, "total", list.size(), "peserta", list);
    }
}