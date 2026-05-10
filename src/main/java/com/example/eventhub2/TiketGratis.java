package com.example.eventhub2;

/**
 * TiketGratis adalah turunan dari Tiket (inheritance).
 * Tiket langsung aktif tanpa pembayaran.
 */
public class TiketGratis extends Tiket {

    public TiketGratis() {
        setStatusTiket("aktif");
    }

    public String generateTiketLangsung() {
        setStatusTiket("aktif");
        return "Tiket gratis berhasil di-generate! Kode QR: " + getKodeQr();
    }

    @Override
    public String tampilkanDetailTiket() {
        return "Tiket Gratis | QR: " + getKodeQr() + " | Status: " + getStatusTiket();
    }
}