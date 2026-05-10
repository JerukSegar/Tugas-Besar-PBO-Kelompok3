package com.example.eventhub2;

/**
 * Peserta adalah turunan dari User (inheritance).
 * Role: peserta — dapat mendaftar dan menghadiri event.
 */
public class Peserta extends User {

    public Peserta() {
        setRole("peserta");
    }

    public String tampilkanDashboard() {
        return "Dashboard Peserta: " + getNama();
    }

    public String lihatSemuaEvent() {
        return "Melihat semua event: " + getNama();
    }

    public String lihatEventSaya() {
        return "Melihat event saya: " + getNama();
    }

    public String lihatTiketSaya() {
        return "Melihat tiket saya: " + getNama();
    }

    public String bukaCheckIn() {
        return "Membuka check-in: " + getNama();
    }
}