package event_notification_system.processor;

import event_notification_system.model.Event;
import event_notification_system.model.EventType;
import event_notification_system.util.HttpClientUtil;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class EventProcessor implements Runnable{

    private final EventType type;
    private final BlockingQueue<Event> queue;
    private final Random random = new Random();

    public EventProcessor(EventType type, BlockingQueue<Event> queue) {
        this.type = type;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Event event = queue.take();
                int delay = switch (type) {
                    case EMAIL -> 5000;
                    case SMS -> 3000;
                    case PUSH -> 2000;
                };

                Thread.sleep(delay);

                boolean failed = random.nextInt(10) == 0;
                Map<String, Object> payload = new HashMap<>();
                payload.put("eventId", event.getEventId());
                payload.put("eventType", type);
                payload.put("processedAt", Instant.now().toString());

                if (failed) {
                    payload.put("status", "FAILED");
                    payload.put("errorMessage", "Simulated processing failure");
                } else {
                    payload.put("status", "COMPLETED");
                }

                HttpClientUtil.sendCallback(event.getCallbackUrl(), payload);
            } catch (Exception e) {
                // log
            }
        }
    }
}
