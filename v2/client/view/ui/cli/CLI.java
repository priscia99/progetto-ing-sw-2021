package v2.client.view.ui.cli;

import it.polimi.ingsw.exceptions.UnknownCommandException;
import it.polimi.ingsw.view.ui.cli.Command;

import java.util.*;


public class CLI {

    private ArrayList<Command> commands = new ArrayList<>();

    public CLI(ArrayList<Command> commands) {
        this.commands.add(new Command(
                "help",
                "Show the possible actions",
                false,
                () -> {
                    this.commands.forEach(command -> System.out.printf("   > %s - %s\n", command.getKey(), command.getDescription()));
                }
                ));
        this.commands.addAll(commands);
    }

    public void execute(String key) throws UnknownCommandException {
        Command target = commands.stream().filter(command -> command.identifiedBy(key)).findAny().orElse(null);
        if (target == null) {
            throw new UnknownCommandException(String.format("No command identified by '%s' exists", key));
        }
        target.execute();
    }

    private void parseCommand(String command) {

    }
}
