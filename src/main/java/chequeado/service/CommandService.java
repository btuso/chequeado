package chequeado.service;

import chequeado.MyLogger;
import chequeado.command.CommandParser;
import chequeado.command.Commands;
import chequeado.model.Message;
import chequeado.model.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class CommandService {

    private static final MyLogger logger = MyLogger.logger(CommandService.class);

    private final CommandParser parser;
    private final Map<Commands, Consumer<Message>> commandHandler = new HashMap<>();

    public CommandService(BotService botService, CommandParser parser) {
        this.parser = parser;

        commandHandler.put(Commands.ECHO, botService::echo);
        commandHandler.put(Commands.NO_OP, botService::noOp);
        commandHandler.put(Commands.CHECK, botService::check);
        commandHandler.put(Commands.ADD_PHOTO, botService::addPhoto);
        commandHandler.put(Commands.IS_THIS_CHECKED, botService::check);
    }


    public void resolveCommand(Update update) {
        Message message = update.getMessage();

        if (message == null) {
            logger.info("Update {} came with no message.", update.getUpdateId());
            return;
        }

        String messageText = message.getText() != null ? message.getText() : message.getCaption();
        if (messageText == null) {
            logger.info("Update {} came with no message or caption text.", update.getUpdateId());
            return;
        }

        String commandRequest = messageText.split(" ")[0];
        if (!parser.isValidCommand(commandRequest)) {
            return;
        }

        Commands command = parser.parseCommand(commandRequest);
        commandHandler.get(command).accept(message);
    }

}
