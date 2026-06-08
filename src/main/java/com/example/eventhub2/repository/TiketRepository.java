package com.example.eventhub2.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventhub2.model.Tiket;

import java.util.List;
import java.util.Optional;

public interface TiketRepository extends JpaRepository<Tiket, Long> {
    List<Tiket> findByPendaftaranId(Long pendaftaranId);
    Optional<Tiket> findByKodeQr(String kodeQr);
}
