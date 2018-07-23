package chequeado.connector;

import chequeado.Config;
import chequeado.Json;
import chequeado.MyLogger;
import chequeado.model.Message;
import chequeado.model.SendMessage;
import chequeado.model.SendPhoto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramConnector {

    private static final MyLogger logger = MyLogger.logger(TelegramConnector.class);

    private static final String BASE_URL = "https://api.telegram.org/bot";
    private static final String SEND_MESSAGE = "/sendMessage";
    private static final String SEND_PHOTO = "/sendPhoto";

    private final String apiToken;

    public TelegramConnector(Config config) {
        apiToken = config.getTelegramApiToken();
    }

    public void sendMessage(SendMessage message) {
        sendMessage(SEND_MESSAGE, message);
    }

    public void sendImage(SendPhoto message) {
        sendMessage(SEND_PHOTO, message);
    }

    private void sendMessage(String endpoint, Object message){
        RestTemplate template = new RestTemplate();
        logRequest(message);
        try {
            ResponseEntity<Message> response = template.postForEntity(endpoint(endpoint), message, Message.class);
            logResponse(response);
        } catch (RestClientResponseException e) {
            logger.info("Error response: {},  {}", e.getRawStatusCode(), e.getResponseBodyAsString());
        } catch (RestClientException e) {
            logger.info("It was another type!  {}", e.getClass().getSimpleName());
        }
    }

    private String endpoint(String path) {
        return BASE_URL + apiToken + path;
    }

    private void logRequest(Object request) {
        logger.info("Request content: {}", Json.print(request));
    }

    private void logResponse(ResponseEntity<Message> response) {
        logger.info("Response: Status [{}], Content: {}", response.getStatusCode(), Json.print(response.getBody()));
    }
}
