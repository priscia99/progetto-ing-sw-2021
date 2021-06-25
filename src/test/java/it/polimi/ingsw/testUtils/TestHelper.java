package it.polimi.ingsw.testUtils;


import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

public class TestHelper {

    static public void makeHimRich(Player player) throws GameException {
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.COIN, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SHIELD, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SERVANT, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.STONE, 10));
    }

}
