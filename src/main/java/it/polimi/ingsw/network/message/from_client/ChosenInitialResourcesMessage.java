package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.resource.ConsumeTarget;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;
import java.util.HashMap;

public class ChosenInitialResourcesMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 35L;
    private final ConsumeTarget resourcesToAdd;
    private final String username;

    public ChosenInitialResourcesMessage(ConsumeTarget resources, String username){
        this.resourcesToAdd = resources;
        this.username = username;
    }

    public void execute(ServerController target) throws Exception {
        target.chooseInitialResources(resourcesToAdd, username);
    }
}
