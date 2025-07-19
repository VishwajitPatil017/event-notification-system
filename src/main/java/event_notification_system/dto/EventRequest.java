package event_notification_system.dto;

import event_notification_system.model.EventType;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.util.Map;

public class EventRequest {
    @NotNull(message = "eventType is required")
    private EventType eventType;
    private Map<String, Object> payload;
    @URL(message = "callbackUrl must be a valid URL")
    @NotNull(message = "callbackUrl is required")
    private String callbackUrl;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;

    }
}

