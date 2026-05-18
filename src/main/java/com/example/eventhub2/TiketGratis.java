package com.example.eventhub2;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("GRATIS")
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