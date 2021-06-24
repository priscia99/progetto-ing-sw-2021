package it.polimi.ingsw;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if(args.length==0){
            throw new Exception("Specify which component to start | server | cli | gui |");
        }
        if(args.length!=1){
            throw new Exception("Wrong arguments, specify only one from | server | cli | gui |");
        }
        String toStart = args[0];
        switch (toStart) {
            case "server" -> ServerApp.main(args);
            case "cli", "gui" -> ClientApp.main(args);
            default -> throw new Exception("Cannot parse argument, select between | server | cli | gui |");
        }
    }
}
