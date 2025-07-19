package event_notification_system.service;

import event_notification_system.dto.EventRequest;
import event_notification_system.model.Event;
import event_notification_system.model.EventType;
import event_notification_system.processor.EventProcessor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final Map<EventType, BlockingQueue<Event>> queues = Map.of(
            EventType.EMAIL, new LinkedBlockingQueue<>(),
            EventType.SMS, new LinkedBlockingQueue<>(),
            EventType.PUSH, new LinkedBlockingQueue<>()
    );

    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    @PostConstruct
    public void init() {
        queues.forEach((type, queue) ->
                executor.submit(() -> new EventProcessor(type, queue).run())
        );
    }


    public String handleEvent(EventRequest request) {
        try {
            String eventId = UUID.randomUUID().toString();
            Event event = new Event(
                    eventId,
                    request.getEventType(),
                    request.getPayload(),
                    request.getCallbackUrl(),
                    Instant.now()
            );

            BlockingQueue<Event> queue = queues.get(request.getEventType());
            if (queue == null) {
                throw new IllegalArgumentException("Unsupported event type: " + request.getEventType());
            }

            queue.add(event);
            logger.info("Event queued: eventId {}, type {}", eventId, request.getEventType());
            return eventId;
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid event: {}", e.getMessage());
            throw e; // Let controller handle this as 400 Bad Request
        } catch (Exception e) {
            logger.error("Failed to queue event", e);
            throw new RuntimeException("Internal error while queuing event", e); // controller will map to 500
        }
    }


    @PreDestroy
    public void shutdown() {
        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }
    }
}
