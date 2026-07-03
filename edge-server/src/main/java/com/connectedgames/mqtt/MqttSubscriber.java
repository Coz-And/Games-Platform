package com.connectedgames.mqtt;

import com.connectedgames.model.EventoSensore;
import com.connectedgames.sync.EventoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class MqttSubscriber {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Autowired
    private EventoRepository eventoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        try {
            MqttClient client = new MqttClient(brokerUrl, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.err.println("❌ Connessione MQTT persa: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    gestisciMessaggio(topic, new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

            client.connect(options);
            client.subscribe("connectedgames/#");
            System.out.println("✅ Edge Server connesso e in ascolto su connectedgames/#");

        } catch (MqttException e) {
            System.err.println("❌ Errore MQTT edge: " + e.getMessage());
        }
    }

    private void gestisciMessaggio(String topic, String payload) {
        try {
            System.out.println("📥 [" + topic + "] " + payload);
            JsonNode json = objectMapper.readTree(payload);

            String giocoId = json.has("giocoId") ?
                    json.get("giocoId").asText() : "unknown";
            String localeId = json.has("localeId") ?
                    json.get("localeId").asText() : "unknown";
            String tipo = json.has("tipo") ?
                    json.get("tipo").asText() : "unknown";

            EventoSensore evento = new EventoSensore(
                    giocoId, localeId, tipo, payload);
            eventoRepository.save(evento);

        } catch (Exception e) {
            System.err.println("❌ Errore parsing messaggio: " + e.getMessage());
        }
    }
}