package chequeado;

import chequeado.command.CommandParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandParserTest {

    private CommandParser parser;

    @Before
    public void setUp() {
        parser = new CommandParser();
    }

    @Test
    public void commandsMustStartWithABackslash() {
        String command = "notavalidcommand";

        boolean result = parser.isValidCommand(command);

        assertFalse(result);
    }

    @Test
    public void commandsCanContainUpperCaseLettersAndNumbers() {
        String command = "/validCommand1";

        boolean result = parser.isValidCommand(command);

        assertTrue(result);
    }

    @Test
    public void commandsCanContainUnderscoresAndDashes() {
        String command = "/a-valid_command";

        boolean result = parser.isValidCommand(command);

        assertTrue(result);
    }

    @Test
    public void commandsCantContainSpaces() {
        String command = "/invalid command";

        boolean result = parser.isValidCommand(command);

        assertFalse(result);
    }

    @Test
    public void commandsCantContainBackslashesInTheMiddle() {
        String command = "/invalid/command";

        boolean result = parser.isValidCommand(command);

        assertFalse(result);
    }

    @Test
    public void commandsCanContainBotName() {
        String command = "/command@botname";

        boolean result = parser.isValidCommand(command);

        assertTrue(result);
    }

    @Test
    public void botNamesCanContainUppercaseLettersAndNumbers() {
        String command = "/command@botName11";

        boolean result = parser.isValidCommand(command);

        assertTrue(result);
    }

    @Test
    public void botNamesCanContainUnderscoresAndDashes() {
        String command = "/command@bot_name-boi";

        boolean result = parser.isValidCommand(command);

        assertTrue(result);
    }

    @Test
    public void botNamesCantStartWithBackslashes() {
        String command = "/command@/botname";

        boolean result = parser.isValidCommand(command);

        assertFalse(result);
    }

    @Test
    public void botNamesCantContainBackslashes() {
        String command = "/command@bot/name";

        boolean result = parser.isValidCommand(command);

        assertFalse(result);
    }

    @Test
    public void thereCanOnlyBeOneAtSymbol() {
        String command = "/command@bot@name";

        boolean result = parser.isValidCommand(command);

        assertFalse(result);
    }

    @Test
    public void commandsCanContainQuestionMarks() {
        String command = "/command?@botname";

        boolean result = parser.isValidCommand(command);

        assertTrue(result);
    }
}