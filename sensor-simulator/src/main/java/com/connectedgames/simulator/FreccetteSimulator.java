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
public class FreccetteSimulator {

    @Autowired
    private MqttPublisher mqttPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    @Scheduled(fixedDelay = 45000, initialDelay = 10000)
    public void simulaTiro() throws Exception {
        int punteggio = random.nextInt(60) + 1;
        String zona = punteggio >= 50 ? "BULLSEYE" :
                punteggio >= 40 ? "ALTA" : "NORMALE";

        Map<String, Object> evento = new HashMap<>();
        evento.put("tipo", "TIRO");
        evento.put("giocoId", "FREC-001");
        evento.put("localeId", "locale-1");
        evento.put("punteggio", punteggio);
        evento.put("zona", zona);
        evento.put("timestamp", LocalDateTime.now().toString());

        mqttPublisher.publish(
                "connectedgames/locale-1/freccette/FREC-001/eventi",
                objectMapper.writeValueAsString(evento)
        );
    }
}