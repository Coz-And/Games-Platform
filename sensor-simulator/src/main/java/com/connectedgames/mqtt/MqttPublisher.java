package com.connectedgames.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class MqttPublisher {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    private MqttClient client;

    @PostConstruct
    public void init() {
        try {
            client = new MqttClient(brokerUrl, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);
            client.connect(options);
            System.out.println("✅ Connesso al broker MQTT: " + brokerUrl);
        } catch (MqttException e) {
            System.err.println("❌ Errore connessione MQTT: " + e.getMessage());
        }
    }

    public void publish(String topic, String payload) {
        try {
            if (client != null && client.isConnected()) {
                MqttMessage message = new MqttMessage(payload.getBytes());
                message.setQos(1);
                client.publish(topic, message);
                System.out.println("📤 [" + topic + "] " + payload);
            }
        } catch (MqttException e) {
            System.err.println("❌ Errore pubblicazione MQTT: " + e.getMessage());
        }
    }
}