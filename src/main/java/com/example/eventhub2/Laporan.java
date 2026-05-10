package com.example.eventhub2;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "laporan")
public class Laporan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "penyelenggara_id", nullable = false)
    private Long penyelenggaraId;

    @Column(name = "total_peserta")
    private int totalPeserta;

    @Column(name = "total_checkin")
    private int totalCheckin;

    @Column(name = "total_pendapatan")
    private long totalPendapatan;

    @Column(name = "dibuat_at")
    private LocalDateTime dibuatAt;

    @PrePersist
    protected void onCreate() {
        this.dibuatAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getPenyelenggaraId() {
        return penyelenggaraId;
    }

    public void setPenyelenggaraId(Long v) {
        this.penyelenggaraId = v;
    }

    public int getTotalPeserta() {
        return totalPeserta;
    }

    public void setTotalPeserta(int totalPeserta) {
        this.totalPeserta = totalPeserta;
    }

    public int getTotalCheckin() {
        return totalCheckin;
    }

    public void setTotalCheckin(int totalCheckin) {
        this.totalCheckin = totalCheckin;
    }

    public long getTotalPendapatan() {
        return totalPendapatan;
    }

    public void setTotalPendapatan(long v) {
        this.totalPendapatan = v;
    }

    public LocalDateTime getDibuatAt() {
        return dibuatAt;
    }

    public void setDibuatAt(LocalDateTime v) {
        this.dibuatAt = v;
    }

    // Method dari class diagram
    public String generateLaporan() {
        return "Laporan event ID: " + eventId + " berhasil digenerate!";
    }

    public String tampilkanLaporan() {
        return "Laporan | Peserta: " + totalPeserta + " | Check-in: " + totalCheckin;
    }
}