package chequeado.service;

import chequeado.Config;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    private final String requestApiToken;

    public AuthService(Config config) {
        requestApiToken = config.getRequestApiToken();
    }

    public boolean authorized(String token){
        if (StringUtils.isEmpty(requestApiToken)){
            return true;
        }
        return requestApiToken.equals(token);
    }
}
