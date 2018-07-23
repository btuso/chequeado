package chequeado;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class Json {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String print(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "{\"message\": \"Error reading object.\"}";
        }
    }
}
