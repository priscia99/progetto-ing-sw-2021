package v2;

import it.polimi.ingsw.network.server.Server;

import java.io.IOException;

public class ServerApp
{
    public static void main( String[] args )
    {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
