package com.connectedgames.sync;

import com.connectedgames.model.EventoSensore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoRepository extends JpaRepository<EventoSensore, Long> {
    List<EventoSensore> findBySincronizzatoFalse();
}