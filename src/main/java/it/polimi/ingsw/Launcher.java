package it.polimi.ingsw;

/**
 * Class used to launch the project
 */
public class Launcher {
    // aws: 52.3.250.239
    private static int port = 5000;

    /**
     * Manages args in input and start the selected application
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if(args.length==0){
            throw new Exception("Specify which component to start | server | cli | gui |");
        }

        String toStart = args[0];
        if(toStart.equals("cli") || toStart.equals(("gui"))){
            if(args.length == 1){
                throw new Exception("Specify the server IP");
            }else{
                String selectedIP = args[1];
                ClientApp.main(toStart, selectedIP, Launcher.port);
            }
        } else if(toStart.equals("server")){
            ServerApp.main();
        }else{
            throw new Exception("Cannot parse argument, select between | server | cli | gui |");
        }

    }
}
