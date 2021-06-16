package it.polimi.ingsw.server;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.utils.CustomRunnable;

public class ServerMessageDecoder implements Observer<Message<ServerController>> {

    private ServerController controller;

    public ServerMessageDecoder(ServerController controller){
        this.controller = controller;
    }


    @Override
    public void update(Message<ServerController> message) {
        controller.tryAction(() -> message.execute(controller));
    }
}
