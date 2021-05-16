package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.exceptions.UnknownCommandException;

import java.util.*;

public class CLI {

    private ArrayList<Command> commands = new ArrayList<>();

    public CLI(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void execute(String key) throws UnknownCommandException {
        Command target = commands.stream().filter(command -> command.identifiedBy(key)).findAny().orElse(null);
        if (target == null) {
            throw new UnknownCommandException(String.format("No command identified by '%s' exists", key));
        }
        target.execute();
    }
}
