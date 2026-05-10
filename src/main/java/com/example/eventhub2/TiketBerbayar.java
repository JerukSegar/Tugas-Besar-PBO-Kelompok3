package com.example.eventhub2;

/**
 * TiketBerbayar adalah turunan dari Tiket (inheritance).
 * Memerlukan verifikasi pembayaran sebelum aktif.
 */
public class TiketBerbayar extends Tiket {

    private String statusPembayaran;

    public TiketBerbayar() {
        setStatusTiket("menunggu_pembayaran");
        this.statusPembayaran = "belum_bayar";
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public String verifikasiPembayaran() {
        if ("sudah_bayar".equals(this.statusPembayaran)) {
            setStatusTiket("aktif");
            return "Pembayaran terverifikasi! Tiket aktif. QR: " + getKodeQr();
        }
        return "Pembayaran belum dikonfirmasi. Status: " + this.statusPembayaran;
    }

    @Override
    public String tampilkanDetailTiket() {
        return "Tiket Berbayar | QR: " + getKodeQr()
                + " | Status Tiket: " + getStatusTiket()
                + " | Status Bayar: " + statusPembayaran;
    }
}