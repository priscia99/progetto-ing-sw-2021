package it.polimi.ingsw.client.view.ui.cli;

import java.util.ArrayList;
import java.util.HashMap;

public class CLICommandsBuilder {
    static ArrayList<Command> getCommands(){
        ArrayList<Command> toReturn = new ArrayList<>();
        toReturn.add(getViewCommand());
        toReturn.add(getHelpCommand());
        toReturn.add(getTurnCommand());
        toReturn.add(getActionCommand());
        toReturn.add(getActivateCommand());
        toReturn.add(getDropCommand());
        return toReturn;
    }

    private static Command getViewCommand(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("p", "[player] The name of the player whose content is to be displayed");
        parameters.put("v", "[type] Type of content to be displayed");
        return new Command(
                "view",
                "Displays the content of a certain player.",
                false,
                parameters
        );
    }

    private static Command getTurnCommand(){
        return new Command("turn", "Displays turn order and the current turn player.", false, new HashMap<>());
    }

    private static Command getHelpCommand(){
        return new Command("help", "Displays list of commands.", false, new HashMap<>());
    }

    private static Command getActionCommand(){
        return new Command("action", "Display list of action turn available.", false, new HashMap<>());
    }

    private static Command getActivateCommand(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("c", "[card-id] The id of the leader card to activate.");
        return new Command("activate", "Activate leader card selected.", false, parameters);
    }

    private static Command getDropCommand(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("c", "[card-id] The id of the leader card to drop.");
        return new Command("drop", "Drop leader card selected.", false, parameters);
    }
}
