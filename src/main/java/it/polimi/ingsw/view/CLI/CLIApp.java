package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.exceptions.ActiveOnlyCommandException;
import it.polimi.ingsw.exceptions.UnknownCommandException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLIApp {

    public static void main(String[] args) throws UnknownCommandException, ActiveOnlyCommandException {
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new Command("a", () -> System.out.println("You entered a"), false, "print a"));
        commands.add(new Command("b", () -> System.out.println("You say b"), true, "print a"));
        CLI cli = new CLI(commands);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome");
        System.out.println("Chose an action: ");
        cli.execute(scanner.nextLine());
    }
}
