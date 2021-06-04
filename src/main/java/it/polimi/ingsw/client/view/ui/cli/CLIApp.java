package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.exceptions.UnknownCommandException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLIApp {

    public static void main(String[] args) {
        ArrayList<Command> commands = new ArrayList<>();
        CLI cli = new CLI(commands);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome");
        System.out.println("Chose an action: ");
        try {
            cli.execute(scanner.nextLine());
        }catch (UnknownCommandException e){
            System.out.println(e.getMessage());
        }
    }
}
