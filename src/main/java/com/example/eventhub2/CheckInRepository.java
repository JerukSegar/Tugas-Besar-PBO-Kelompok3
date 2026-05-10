package com.example.eventhub2;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    boolean existsByTiketId(Long tiketId);
    List<CheckIn> findByPenyelenggaraId(Long penyelenggaraId);
}
