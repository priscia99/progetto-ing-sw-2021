package it.polimi.ingsw.client.view.ui.cli;

import java.util.*;

import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.view.representation.RepresentationBuilder;
import it.polimi.ingsw.client.view.ui.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.network.auth_data.*;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;

public class CLI implements UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private static final String TITLE = " ___  ___  ___   __  ______  ____ ____   __       ___    ____    ____   ____ __  __  ___  __  __   __   ___  __  __   ___  ____\n" +
            " ||\\\\//|| // \\\\ (( \\ | || | ||    || \\\\ (( \\     // \\\\  ||       || \\\\ ||    ||\\ || // \\\\ || (( \\ (( \\ // \\\\ ||\\ ||  //   ||   \n" +
            " || \\/ || ||=||  \\\\    ||   ||==  ||_//  \\\\     ((   )) ||==     ||_// ||==  ||\\\\|| ||=|| ||  \\\\   \\\\  ||=|| ||\\\\|| ((    ||== \n" +
            " ||    || || || \\_))   ||   ||___ || \\\\ \\_))     \\\\_//  ||       || \\\\ ||___ || \\|| || || || \\_)) \\_)) || || || \\||  \\\\__ ||___\n" +
            "                                                                                                                               \n";

    private final Integer out = 0;

    private ArrayList<Command> commands = new ArrayList<>();
    Scanner in = new Scanner(System.in);

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

    @Override
    public AuthData requestAuth(){
        AuthData authData = null;
        synchronized (out) {
            String playerUsername;
            String choice;
            System.out.println(ANSI_CYAN + TITLE + ANSI_RESET);
            System.out.printf("Enter an username:\n\t> ");
            playerUsername = in.nextLine();
            do {
                System.out.print("Welcome, " + playerUsername + "! Would you like to join [J] or create [C] a lobby?\n\t> ");
                choice = in.nextLine();
                if (choice.equalsIgnoreCase("J")) {
                    String lobbyToJoin;
                    System.out.print("Enter a valid lobby ID: \n\t> ");
                    lobbyToJoin = in.nextLine();
                    authData = AuthData.joinLobby(playerUsername, lobbyToJoin);
                } else if (choice.equalsIgnoreCase("C")) {
                    int playerNumber;
                    do {
                        System.out.print("Enter the maximum number of players for this lobby [1-4]: \n\t> ");
                        playerNumber = in.nextInt();
                    } while (playerNumber < 1 || playerNumber > 4);
                    authData = AuthData.createLobby(playerUsername, playerNumber);
                }
            } while (!choice.equalsIgnoreCase("J") && !choice.equalsIgnoreCase("C"));
        }
        return authData;
    }

    @Override
    public void displayAuthFail(String errType) {
        synchronized (out) {
            System.out.print("Failed to login! ");
            switch (errType) {
                case "full_lobby":
                    System.out.println("Full lobby!");
                    break;
                case "invalid_lobby":
                    System.out.println("Invalid lobby!");
                    break;
                default:
                    System.out.println("Generic error while connecting to server.");
                    break;
            }
            System.out.println("Trying again...");
        }
    }

    @Override
    public void displayLobbyJoined(String lobbyId){
        synchronized (out) {
            System.out.println("User logged successfully in lobby with ID: " + ANSI_YELLOW + lobbyId + ANSI_RESET);
        }
    }

    @Override
    public void displayLobbyCreated(String lobbyId){
        synchronized (out) {
            System.out.println("Lobby created succeffully in lobby with ID: " + ANSI_YELLOW + lobbyId + ANSI_RESET);
        }
    }

    @Override
    public void displayNewTurn(String player, Boolean myTurn){
        synchronized (out) {
            if (myTurn) {
                System.out.println(player + ", IT'S YOUR TURN!");
            } else {
                System.out.println("It's " + player + "'s turn.");
            }
        }
    }

    @Override
    public void displayGameStarted(){
        synchronized (out) {
            System.out.println("Game has started!");
        }
    }

    @Override
    public void displayLeaderCard(ClientLeaderCard clientLeaderCard) {
        synchronized (out) {
            String outputFormat = "%s Lv%d - Victory Points: %d - [";
        }
    }

    public HashMap<ResourcePosition, ResourceType> chooseInitialResources(int toChoose){
        HashMap<ResourcePosition, ResourceType> resources = new HashMap<>();
        synchronized (out) {
            int chosenResources = 0;
            boolean validSelection = true;
            String tempResource, tempDepot;
            ResourcePosition resourcePosition = null;
            ResourceType resourceType = null;

            System.out.println("You have to choose " + toChoose + " resource/s!");
            while (chosenResources < toChoose) {
                do {
                    validSelection = true;
                    System.out.print("Choose resources #" + (chosenResources + 1) +
                            " [STONE | SERVANT | SHIELD | COIN]: \n\t> ");
                    tempResource = in.nextLine();
                    switch (tempResource) {
                        case "STONE":
                            resourceType = ResourceType.STONE;
                            break;
                        case "SERVANT":
                            resourceType = ResourceType.SERVANT;
                            break;
                        case "SHIELD":
                            resourceType = ResourceType.SHIELD;
                            break;
                        case "COIN":
                            resourceType = ResourceType.COIN;
                            break;
                        default:
                            validSelection = false;
                            System.out.println("Please choose a valid resource type!");
                    }
                } while (!validSelection);

                do {
                    validSelection = true;
                    System.out.print("Choose in which depot you want to put this resource [FIRST | SECOND | THIRD]: \n\t> ");
                    tempDepot = in.nextLine();
                    switch (tempDepot) {
                        case "FIRST":
                            resourcePosition = ResourcePosition.FIRST_DEPOT;
                            break;
                        case "SECOND":
                            resourcePosition = ResourcePosition.SECOND_DEPOT;
                            break;
                        case "THIRD":
                            resourcePosition = ResourcePosition.THIRD_DEPOT;
                            break;
                        default:
                            validSelection = false;
                            System.out.println("Please choose a valid resource position!");
                    }
                } while (!validSelection);
                resources.put(resourcePosition, resourceType);
                chosenResources++;
            }
        }
        return resources;
    }

    public ArrayList<String> chooseInitialLeaders(){
        ArrayList<String> chosenLeaderCards = new ArrayList<>();
        synchronized (out) {
            int cardsChosen = 0;
            boolean validSelection = true;
            System.out.println("You have to choose 2 leader cards!");
            System.out.println("Your leader cards are: ");
            // TODO print leader cards
            while (cardsChosen < 2) {
                String chosenCardId;
                validSelection = true;
                do {
                    validSelection = true;
                    System.out.print("Choose leader cards #" + (cardsChosen + 1) + " [type card ID]: \n\t> ");
                    chosenCardId = in.nextLine();
                    System.out.println("You have chosen " + chosenCardId);
                    if (chosenLeaderCards.contains(chosenCardId)) {
                        validSelection = false;
                        System.out.println("You cannot choose the same leader card!");
                    }
                    // TODO check if the id is valid (if player has a leader card with that id)
                } while (!validSelection);
                chosenLeaderCards.add(chosenCardId);
                cardsChosen++;
            }
        }
        return chosenLeaderCards;
    }

    @Override
    public void displayLeaderCardDeck(ClientLeaderCardDeck clientLeaderCardDeck) {
        synchronized (out) {
            System.out.println(RepresentationBuilder.render(clientLeaderCardDeck));
        }
    }
}

