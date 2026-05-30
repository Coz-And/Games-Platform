package com.connectedgames.sync;

import com.connectedgames.model.EventoSensore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SyncService {

    @Autowired
    private EventoRepository eventoRepository;

    @Value("${central.server.url}")
    private String centralServerUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Sincronizza ogni 10 secondi
    @Scheduled(fixedDelay = 10000)
    public void sincronizza() {
        List<EventoSensore> eventi =
                eventoRepository.findBySincronizzatoFalse();

        if (eventi.isEmpty()) return;

        System.out.println("🔄 Sincronizzo " + eventi.size() +
                " eventi con il central server...");

        for (EventoSensore evento : eventi) {
            try {
                restTemplate.postForObject(
                        centralServerUrl + "/api/eventi",
                        evento.getPayload(),
                        String.class
                );
                evento.setSincronizzato(true);
                eventoRepository.save(evento);
                System.out.println("✅ Evento sincronizzato: " + evento.getTipo());
            } catch (Exception e) {
                System.err.println("⚠️ Impossibile sincronizzare — " +
                        "server non raggiungibile, riprovo dopo.");
            }
        }
    }
}