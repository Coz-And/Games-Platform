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
public class MonopoliSimulator {

    @Autowired
    private MqttPublisher mqttPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    private final String[] EVENTI = {
            "PASSAGGIO_VIA", "ACQUISTO_PROPRIETA",
            "PAGAMENTO_AFFITTO", "CARTE_PROBABILITA", "VAI_IN_PRIGIONE"
    };

    @Scheduled(fixedDelay = 20000, initialDelay = 8000)
    public void simulaEvento() throws Exception {
        String tipoEvento = EVENTI[random.nextInt(EVENTI.length)];
        String giocatore = "GIOCATORE_" + (random.nextInt(4) + 1);

        Map<String, Object> evento = new HashMap<>();
        evento.put("tipo", tipoEvento);
        evento.put("giocoId", "MONO-001");
        evento.put("localeId", "locale-1");
        evento.put("giocatore", giocatore);
        evento.put("timestamp", LocalDateTime.now().toString());

        if (tipoEvento.equals("ACQUISTO_PROPRIETA")) {
            evento.put("proprieta", "Via Roma");
            evento.put("costo", random.nextInt(400) + 100);
        }
        if (tipoEvento.equals("PASSAGGIO_VIA")) {
            evento.put("bonus", 200);
        }

        mqttPublisher.publish(
                "connectedgames/locale-1/monopoli/MONO-001/eventi",
                objectMapper.writeValueAsString(evento)
        );
    }
}