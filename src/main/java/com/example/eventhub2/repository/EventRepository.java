package com.example.eventhub2.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventhub2.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCreatedBy(Long createdBy);
}
