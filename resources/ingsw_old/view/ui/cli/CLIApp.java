package it.polimi.ingsw_old.view.ui.cli;

import it.polimi.ingsw_old.exceptions.UnknownCommandException;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIApp {

    public static void main(String[] args) {
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new Command("a", "test a" , false, () -> System.out.println("You entered a")));
        commands.add(new Command("b", "test a" , true, () -> System.out.println("You say b")));
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
