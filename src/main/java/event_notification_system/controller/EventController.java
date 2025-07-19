    package event_notification_system.controller;


    import event_notification_system.dto.EventRequest;
    import event_notification_system.service.EventService;
    import jakarta.validation.Valid;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.*;

    @RestController
    @RequestMapping("/api/events")
    public class EventController {

        private static final Logger logger = LoggerFactory.getLogger(EventController.class);

        private final EventService eventService;

        public EventController(EventService eventService) {
            this.eventService = eventService;
        }

        /**
         * Accepts an event request and submits it for asynchronous processing.
         *
         * @param request The event request payload containing event type, payload, and callback URL.
         * @return A response entity with event ID and success message if accepted; error message otherwise.
         */
        @PostMapping
        public ResponseEntity<?> submitEvent(@RequestBody @Valid EventRequest request) {
            logger.info("Received event submission: type {}, callbackUrl {}",
                    request.getEventType(), request.getCallbackUrl());

            try {
                String eventId = eventService.handleEvent(request);
                logger.info("Event accepted with ID: {}", eventId);

                return ResponseEntity.ok(Map.of("eventId", eventId, "message", "Event accepted for processing."
                ));
            } catch (IllegalArgumentException ex) {
                logger.warn("Invalid event request: {}", ex.getMessage());

                return ResponseEntity.badRequest().body(Map.of( "error", "Invalid request", "details", ex.getMessage()
                ));
            } catch (Exception ex) {
                logger.error("Failed to process event", ex);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to process event", "details", ex.getMessage()
                ));
            }
        }


    }
