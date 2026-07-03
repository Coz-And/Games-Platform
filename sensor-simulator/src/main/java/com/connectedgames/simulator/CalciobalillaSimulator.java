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
public class CalciobalillaSimulator {

    @Autowired
    private MqttPublisher mqttPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    private int punteggioRosso = 0;
    private int punteggioBlue = 0;
    private boolean partitaInCorso = false;

    // Simula inizio partita ogni 30 secondi
    @Scheduled(fixedDelay = 30000, initialDelay = 5000)
    public void simulaPartita() throws Exception {
        if (!partitaInCorso) {
            iniziaPartita();
        }
    }

    private void iniziaPartita() throws Exception {
        partitaInCorso = true;
        punteggioRosso = 0;
        punteggioBlue = 0;

        Map<String, Object> evento = new HashMap<>();
        evento.put("tipo", "INIZIO_PARTITA");
        evento.put("giocoId", "CALC-001");
        evento.put("localeId", "locale-1");
        evento.put("timestamp", LocalDateTime.now().toString());

        mqttPublisher.publish(
                "connectedgames/locale-1/calciobalilla/CALC-001/eventi",
                objectMapper.writeValueAsString(evento)
        );

        // Simula 5 goal casuali
        for (int i = 0; i < 5; i++) {
            Thread.sleep(2000);
            simulaGoal();
        }

        terminaPartita();
    }

    private void simulaGoal() throws Exception {
        String squadra = random.nextBoolean() ? "ROSSO" : "BLUE";
        if (squadra.equals("ROSSO")) punteggioRosso++;
        else punteggioBlue++;

        Map<String, Object> evento = new HashMap<>();
        evento.put("tipo", "GOAL");
        evento.put("squadra", squadra);
        evento.put("punteggioRosso", punteggioRosso);
        evento.put("punteggioBlue", punteggioBlue);
        evento.put("giocoId", "CALC-001");
        evento.put("timestamp", LocalDateTime.now().toString());

        mqttPublisher.publish(
                "connectedgames/locale-1/calciobalilla/CALC-001/eventi",
                objectMapper.writeValueAsString(evento)
        );
    }

    private void terminaPartita() throws Exception {
        partitaInCorso = false;

        Map<String, Object> evento = new HashMap<>();
        evento.put("tipo", "FINE_PARTITA");
        evento.put("giocoId", "CALC-001");
        evento.put("punteggioRosso", punteggioRosso);
        evento.put("punteggioBlue", punteggioBlue);
        evento.put("vincitore", punteggioRosso > punteggioBlue ? "ROSSO" : "BLUE");
        evento.put("timestamp", LocalDateTime.now().toString());

        mqttPublisher.publish(
                "connectedgames/locale-1/calciobalilla/CALC-001/eventi",
                objectMapper.writeValueAsString(evento)
        );
    }
}