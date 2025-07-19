package event_notification_system.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpClientUtil {
    /*public static void sendCallback(String url, Map<String, Object> payload) {
        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(payload)))
                    .build();
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            // log
        }
    }*/

    public static void sendCallback(String url, Map<String, Object> payload) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");

            StringEntity entity = new StringEntity(new ObjectMapper().writeValueAsString(payload));
            post.setEntity(entity);

            try (CloseableHttpResponse response = client.execute(post)) {
                // You can log status if needed: response.getStatusLine().getStatusCode()
            }
        } catch (Exception e) {
            // log error
        }
    }

}
