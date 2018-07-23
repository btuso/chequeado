package chequeado.command;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CommandParser {

    private static final Pattern COMMAND_PATTERN = Pattern.compile("^/[a-zA-Z0-9-_?]+(?:@[a-zA-Z0-9-_]+)?");

    public boolean isValidCommand(String command){
        return COMMAND_PATTERN.matcher(command).matches();
    }

    public Commands parseCommand(String command){
        String sanitized = command.split("@")[0];
        sanitized = sanitized.replaceAll("/", "");
        return Commands.getCommandFor(sanitized);
    }
}
