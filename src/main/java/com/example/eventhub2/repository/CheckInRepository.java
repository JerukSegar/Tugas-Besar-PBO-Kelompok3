package com.example.eventhub2.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventhub2.model.CheckIn;

import java.util.List;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    boolean existsByTiketId(Long tiketId);
    List<CheckIn> findByPenyelenggaraId(Long penyelenggaraId);
}
