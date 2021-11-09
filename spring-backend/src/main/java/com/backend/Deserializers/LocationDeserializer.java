package com.backend.Deserializers;

import java.io.IOException;

import com.backend.Models.CrashDetected;
import com.backend.Models.Location;
import com.backend.Models.Notification.NotificationType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationDeserializer extends JsonDeserializer<Location> {

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
    public Location deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.readValueAsTree();
        if (node.has("maxAcceleration")) {
            return CrashDetected.parse(node);
        } else {
            return Location.parse(node);
        }
    }
}