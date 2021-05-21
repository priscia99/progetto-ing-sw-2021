package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.exceptions.ActiveOnlyCommandException;
import it.polimi.ingsw.exceptions.UnknownCommandException;

import java.util.*;

public class CLI {

    private ArrayList<Command> commands = new ArrayList<>();

    public CLI(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void execute(String key) throws UnknownCommandException, ActiveOnlyCommandException {
        Command target = commands
                .stream()
                .filter(command -> command.identifiedBy(key))
                .findAny()
                .orElse(null);
        if (target == null) {
            throw new UnknownCommandException(String.format("No command identified by '%s' exists", key));
        }
        // FIXME add turn control
        if (target.isActiveOnly()) {
            throw new ActiveOnlyCommandException(String.format("The command identified by '%s' cannot be execute outside the turn", target.getKey()));
        }
        target.execute();
    }
}
