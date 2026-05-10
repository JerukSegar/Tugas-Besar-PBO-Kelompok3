package com.example.eventhub2;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LaporanRepository extends JpaRepository<Laporan, Long> {
    Optional<Laporan> findByEventId(Long eventId);
    List<Laporan> findByPenyelenggaraId(Long penyelenggaraId);
}
