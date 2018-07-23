package chequeado;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class Config {

    private static final String TELEGRAM_API_TOKEN = "TELEGRAM_API_TOKEN";
    private static final String REQUEST_API_TOKEN = "REQUEST_API_TOKEN";

    private final String telegramApiToken;
    private final String requestApiToken;

    public Config() {
        String telegramApiToken = System.getenv(TELEGRAM_API_TOKEN);

        if (StringUtils.isEmpty(telegramApiToken)) {
            throw new RuntimeException("Api token environment variable can't be empty!");
        }
        this.telegramApiToken = telegramApiToken;
        this.requestApiToken = System.getenv(REQUEST_API_TOKEN);
    }

    public String getTelegramApiToken() {
        return telegramApiToken;
    }

    public String getRequestApiToken() {
        return requestApiToken;
    }

}
