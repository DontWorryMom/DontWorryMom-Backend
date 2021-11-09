package com.backend.Deserializers;

import java.io.IOException;

import com.backend.Models.EmailNotification;
import com.backend.Models.Notification;
import com.backend.Models.TextNotification;
import com.backend.Models.Notification.NotificationType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificationDeserializer extends JsonDeserializer<Notification> {

    static ObjectMapper mapper = new ObjectMapper();

    public static NotificationType getNotificationType(String typeStr) {
        switch (typeStr) {
            case "EMAIL_NOTIFICATION":
                return NotificationType.EMAIL_NOTIFICATION;
            case "TEXT_NOTIFICATION":
                return NotificationType.TEXT_NOTIFICATION;
            default:
                return null;
        }
    }

    @Override
    public Notification deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.readValueAsTree();
        NotificationType type = getNotificationType(node.get("type").asText());
        switch (type) {
            case EMAIL_NOTIFICATION:
                return EmailNotification.parse(node);
            case TEXT_NOTIFICATION:
                return TextNotification.parse(node);
            default:
                throw new RuntimeException("Unable to parse Notification");
        }
    }
}