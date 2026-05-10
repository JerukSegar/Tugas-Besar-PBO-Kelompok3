package com.example.eventhub2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface PendaftaranRepository extends JpaRepository<Pendaftaran, Long> {
    boolean existsByUserIdAndEventId(Long userId, Long eventId);
    List<Pendaftaran> findByUserId(Long userId);
    Pendaftaran findByUserIdAndEventId(Long userId, Long eventId);
    @Transactional
    void deleteByUserIdAndEventId(Long userId, Long eventId);
}
