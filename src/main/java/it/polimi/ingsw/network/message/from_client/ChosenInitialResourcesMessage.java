package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.io.Serializable;
import java.util.HashMap;

public class ChosenInitialResourcesMessage implements Message<ServerController>, Serializable {

    private static final long serialVersionUID = 1L;
    private final HashMap<ResourcePosition, ResourceType> resourcesToAdd;
    private final String username;

    public ChosenInitialResourcesMessage(HashMap<ResourcePosition, ResourceType> resources, String username){
        this.resourcesToAdd = resources;
        this.username = username;
    }

    public void execute(ServerController target) {
        target.chooseInitialResources(resourcesToAdd, username);
    }
}
