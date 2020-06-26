package chequeado.command;

import static java.util.Arrays.stream;

public enum Commands {
    NO_OP(""),
    CHECK("chequear"),
    IS_THIS_CHECKED("estoEstaChequeado?"),
    ADD_PHOTO("agregarFoto"),
    ADD_MEDIA("agregarMedia"),
    END_ADD_MEDIA("finAgregarMedia"),
    ECHO("echo")
    ;

    private final String commandName;

    Commands(String commandName) {
        this.commandName = commandName;
    }

    public static Commands getCommandFor(String text) {
        return stream(Commands.values())
                .filter(command -> command.commandName.equalsIgnoreCase(text))
                .findFirst()
                .orElse(NO_OP);
    }
}
