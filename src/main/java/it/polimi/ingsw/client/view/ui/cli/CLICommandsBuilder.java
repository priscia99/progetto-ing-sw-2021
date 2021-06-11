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
        toReturn.add(getMarbleMarketCommand());
        toReturn.add(getCardMarketCommand());
        toReturn.add(getEndTurnCommand());
        toReturn.add(getSwapDepotsCommand());
        toReturn.add(buyCommand());
        toReturn.add(pickCommand());
        return toReturn;
    }

    private static Command getViewCommand(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("p", "[player] The name of the player whose content is to be displayed");
        parameters.put("t", "[type] Type of content to be displayed");
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
        return new Command("actions", "Display list of action turn available.", false, new HashMap<>());
    }

    private static Command getActivateCommand(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("c", "[card-id] The id of the leader card to activate.");
        return new Command("activate", "Activate leader card selected.", true, parameters);
    }

    private static Command getDropCommand(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("c", "[card-id] The id of the leader card to drop.");
        return new Command("drop", "Drop leader card selected.", true, parameters);
    }

    private static Command getMarbleMarketCommand(){
        return new Command("marblemarket", "View content of marble market", false, new HashMap<>());
    }

    private static Command getCardMarketCommand(){
        return new Command("cardmarket", "View content of card market", false, new HashMap<>());
    }

    private static Command getEndTurnCommand(){
        return new Command("endturn", "Ends turn", true, new HashMap<>());
    }

    private static Command getSwapDepotsCommand() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("a", "[first-index] The first depot index to move");
        parameters.put("b", "[second-index] The second depot inted to move");
        return new Command("swap", "Swap depots", true, parameters);
    }

    private static Command buyCommand(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("c", "[card-id] Id of the card to buy");
        return new Command("buy", "Buy development card", true, parameters);
    }

    private static Command pickCommand(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("o", "[orientation] The orientation of marbles to pick. Can be | HORIZONTAL | VERTICAL |");
        parameters.put("i", "[second-index] The index of marble market");
        return new Command("pick", "Pick resources from marble market", true, parameters);
    }
}
