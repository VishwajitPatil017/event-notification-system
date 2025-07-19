Event Notification System

A Java-based backend system to accept event requests (EMAIL/SMS), process them asynchronously, and send status callbacks once done.
Features
- REST API to submit events
- Asynchronous event processing using thread pool
- Callback mechanism to notify client system
- Dockerized app and MongoDB support

Tech Stack
- Java 17
- Spring Boot
- Maven
- MongoDB
- Docker & Docker Compose

Running the Application with Docker
1. Clone the Repository
bash
git clone https://github.com/your-username/event-notification-system.git
cd event-notification-system

Build and Run using Docker Compose

**docker-compose up --build**

API Reference
Endpoint:
POST /api/events
Request Body Example:

{
"eventType": "EMAIL",
"payload": {
"recipient": "vishwajitpatil1224@gmail.com",
"message": "Welcome to our service!"
},
"callbackUrl": "http://client-system.com/api/event-status"
}
Success Response:

{
  "eventId": "97f5e216-b64e-41ac-ae3d-723cd7907867",
  "message": "Event accepted for processing."
}
Callback Request Format
Success Example:


{
  "eventId": "97f5e216-b64e-41ac-ae3d-723cd7907867",
  "status": "COMPLETED",
  "eventType": "EMAIL",
  "processedAt": "2025-07-01T12:34:56Z"
}

{
  "eventId": "97f5e216-b64e-41ac-ae3d-723cd7907867",
  "status": "FAILED",
  "eventType": "EMAIL",
  "processedAt": "2025-07-01T12:34:56Z",
  "error": "Invalid recipient email"
}

Docker Setup Details
Dockerfile
Builds the Spring Boot app into a Docker image.

docker-compose.yml
Starts:

event-api: the backend app

mongo: MongoDB instance

MongoDB will be accessible at mongodb://localhost:27017.


Author
Vishwajit Patil
Backend Java Developer
