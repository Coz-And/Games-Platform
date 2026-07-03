package com.connectedgames.simulator;

import com.connectedgames.mqtt.MqttPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class BocceSimulator {

    @Autowired
    private MqttPublisher mqttPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    @Scheduled(fixedDelay = 40000, initialDelay = 15000)
    public void simulaLancio() throws Exception {
        double distanzaPallino = Math.round(random.nextDouble() * 200) / 100.0;
        String giocatore = random.nextBoolean() ? "GIOCATORE_1" : "GIOCATORE_2";

        Map<String, Object> evento = new HashMap<>();
        evento.put("tipo", "LANCIO_BOCCIA");
        evento.put("giocoId", "BOCC-001");
        evento.put("localeId", "locale-1");
        evento.put("giocatore", giocatore);
        evento.put("distanzaDalPallino", distanzaPallino);
        evento.put("timestamp", LocalDateTime.now().toString());

        mqttPublisher.publish(
                "connectedgames/locale-1/bocce/BOCC-001/eventi",
                objectMapper.writeValueAsString(evento)
        );
    }
}