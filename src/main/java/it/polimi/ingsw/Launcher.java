package it.polimi.ingsw;

public class Launcher {
    private static String ip = "52.3.250.239";
    private static int port = 5000;

    public static void main(String[] args) throws Exception {
        if(args.length==0){
            throw new Exception("Specify which component to start | server | cli | gui |");
        }

        String toStart = args[0];
        if(args.length > 1 && args[1].equals("local")) ip = "127.0.0.1";
        switch (toStart) {
            case "server" -> ServerApp.main();
            case "cli", "gui" -> ClientApp.main(toStart, Launcher.ip, Launcher.port);
            default -> throw new Exception("Cannot parse argument, select between | server | cli | gui |");
        }
    }
}
