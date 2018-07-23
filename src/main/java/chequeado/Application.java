package chequeado;

import chequeado.model.Update;
import chequeado.service.AuthService;
import chequeado.service.CommandService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    private static final MyLogger logger = MyLogger.logger(Application.class);

    private final AuthService authService;
    private final CommandService commandService;

    public Application(AuthService authService, CommandService commandService) {
        this.authService = authService;
        this.commandService = commandService;
    }

    @RequestMapping(value = {"/update", "/{token}/update"}, method = RequestMethod.POST)
    public ResponseEntity message(@PathVariable(required = false) String token, @RequestBody Update update) {
        logger.setRequestTag();
        logger.info("Request payload: {}", Json.print(update));

        if (!authService.authorized(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        commandService.resolveCommand(update);
        logger.clearRequestTag();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static void main(String[] args) {
        logger.setRequestTag();
        SpringApplication.run(Application.class, args);
        logger.clearRequestTag();
    }

}

