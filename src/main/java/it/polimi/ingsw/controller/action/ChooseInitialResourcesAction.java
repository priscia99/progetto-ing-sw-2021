package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.server_model.resource.ResourcePosition;
import it.polimi.ingsw.server_model.resource.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseInitialResourcesAction extends Action{
    private final HashMap<ResourcePosition, ResourceType> resourcesToAdd;
    private final String username;

    public ChooseInitialResourcesAction(HashMap<ResourcePosition, ResourceType> resources, String username){
        this.resourcesToAdd = resources;
        this.username = username;
    }

    @Override
    public void execute(Game game) {
        game.getPlayerByUsername(username).pickedInitialResources(resourcesToAdd);
        game.tryStart();
    }
}
