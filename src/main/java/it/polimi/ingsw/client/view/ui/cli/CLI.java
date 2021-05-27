package it.polimi.ingsw.client.view.ui.cli;

import java.util.*;
import it.polimi.ingsw.client.view.ui.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.network.auth_data.*;

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
        String playerUsername;
        String choice;
        System.out.println(ANSI_CYAN + TITLE + ANSI_RESET);
        System.out.printf("Enter an username:\n\t>");
        playerUsername = in.nextLine();
        do{
            System.out.print("Welcome, " + playerUsername + "! Would you like to join [J] or create [C] a lobby?\n\t>");
            choice = in.nextLine();
            if(choice.equalsIgnoreCase("J")) {
                String lobbyToJoin;
                System.out.print("Enter a valid lobby ID: \n\t>");
                lobbyToJoin = in.nextLine();
                authData = AuthData.joinLobby(playerUsername, lobbyToJoin);
            }
            else if (choice.equalsIgnoreCase("C")){
                int playerNumber;
                do{
                    System.out.print("Enter the maximum number of players for this lobby [1-4]: \n\t>");
                    playerNumber = in.nextInt();
                }while(playerNumber<1 || playerNumber>4);
                authData = AuthData.createLobby(playerUsername, playerNumber);
                }
        }while(!choice.equalsIgnoreCase("J") && !choice.equalsIgnoreCase("C"));
        return authData;
    }

    @Override
    public void displayAuthFail(String errType) {
        System.out.print("Failed to login! ");
        switch (errType){
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

    @Override
    public void displayLobbyJoined(String lobbyId){
        System.out.println("User logged successfully in lobby with ID: " + lobbyId);
    }

    @Override
    public void displayLobbyCreated(String lobbyId){
        System.out.println("Lobby created succeffully in lobby with ID: " + lobbyId);
    }

    @Override
    public void displayNewTurn(String player, Boolean myTurn){
        if(myTurn){
            System.out.println(player + ", IT'S YOUR TURN!");
        } else {
            System.out.println("It's " + player + "'s turn.");
        }
    }

    @Override
    public void displayGameStarted(){
        System.out.println("Game has started!");
    }
}
