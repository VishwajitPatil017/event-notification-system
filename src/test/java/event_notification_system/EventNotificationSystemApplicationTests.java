package event_notification_system;

import event_notification_system.dto.EventRequest;
import event_notification_system.model.EventType;
import event_notification_system.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
class EventNotificationSystemApplicationTests {

	@Autowired
	private EventService service;

	@Test
	void testValidEmailEvent() {
		EventRequest request = new EventRequest();
		request.setEventType(EventType.EMAIL);
		request.setCallbackUrl("http://localhost:8081/test");
		Map<String, Object> payload = new HashMap<>();
		payload.put("recipient", "user@example.com");
		payload.put("message", "Hello!");
		request.setPayload(payload);

		String eventId = service.handleEvent(request);
		assertNotNull(eventId);
	}

	@Test
	void testInvalidEventType() {
		// simulate validation test for null or unknown enum
	}

}
