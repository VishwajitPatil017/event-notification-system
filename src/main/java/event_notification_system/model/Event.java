package event_notification_system.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Map;

@Data
public class Event {
    @Id
    private String eventId;
    private EventType eventType;
    private Map<String, Object> payload;
    private String callbackUrl;
    private Instant receivedAt;

    public Event() {
    }
    public Event(String eventId, EventType eventType, Map<String, Object> payload, String callbackUrl, Instant receivedAt) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.payload = payload;
        this.callbackUrl = callbackUrl;
        this.receivedAt = receivedAt;
    }

    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public EventType getEventType() {
        return eventType;
    }
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    public Map<String, Object> getPayload() {
        return payload;
    }
    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
    public String getCallbackUrl() {
        return callbackUrl;
    }
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
    public Instant getReceivedAt() {
        return receivedAt;
    }
    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }
}
