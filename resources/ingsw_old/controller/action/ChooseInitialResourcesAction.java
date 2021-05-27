package it.polimi.ingsw_old.controller.action;

import it.polimi.ingsw_old.model.game.Game;
import it.polimi.ingsw_old.model.resource.ResourcePosition;
import it.polimi.ingsw_old.model.resource.ResourceType;

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
