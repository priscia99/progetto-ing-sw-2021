package it.polimi.ingsw.server;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.utils.CustomRunnable;

/**
 * Class that models a message decoder on the server. It decodes incoming messages for the server and execute them.
 */
public class ServerMessageDecoder implements Observer<Message<ServerController>> {

    private ServerController controller;

    /**
     * Create a new ServerMessageDecoder object with a given ServerController.
     * @param controller the ServerController to link
     */
    public ServerMessageDecoder(ServerController controller){
        this.controller = controller;
    }


    /**
     * Try to apply message execution on game controller
     * @param message
     */
    @Override
    public void update(Message<ServerController> message) {
        controller.tryAction(() -> message.execute(controller));
    }
}
