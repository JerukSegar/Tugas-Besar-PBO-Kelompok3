package com.example.eventhub2;

/**
 * Penyelenggara adalah turunan dari User (inheritance).
 * Role: penyelenggara — dapat membuat dan mengelola event.
 */
public class Penyelenggara extends User {

    public Penyelenggara() {
        setRole("penyelenggara");
    }

    public String tampilkanDashboard() {
        return "Dashboard Penyelenggara: " + getNama();
    }

    public String kelolaEvent() {
        return "Mengelola event oleh: " + getNama();
    }

    public String lihatLaporan() {
        return "Melihat laporan event oleh: " + getNama();
    }
}