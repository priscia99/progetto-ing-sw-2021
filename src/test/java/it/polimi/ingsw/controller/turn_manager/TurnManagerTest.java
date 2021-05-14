package it.polimi.ingsw.controller.turn_manager;

import it.polimi.ingsw.server_model.card.LeaderCard;
import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.server_model.game.Player;
import it.polimi.ingsw.server_model.marble.Orientation;
import it.polimi.ingsw.controller.turn_manager.action_params.DropLeaderCardParams;
import it.polimi.ingsw.controller.turn_manager.action_params.PickResourcesParams;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TurnManagerTest {

    private TurnManager turnManager;
    private Game game;

    @BeforeEach
    public void setUp(){
        game = new Game();
        ArrayList<Player> players = MockProvider.getMockPlayers();
        game.setup(players);
        turnManager = game.getTurnManager();
    }

    @Test
    @DisplayName("Test main action done state")
    public void testMainActionDone(){
        turnManager.startTurn();
        Assertions.assertFalse(turnManager.isMainActionDone());
        LeaderCard leaderCard = MockProvider.getLeaderCardMock();
        game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().addLeader(leaderCard);
        DropLeaderCardParams params = new DropLeaderCardParams(leaderCard.getId());
        turnManager.executeDropLeaderCard(params);
        Assertions.assertFalse(turnManager.isMainActionDone());
        ArrayList<Integer> positions = new ArrayList<>(Arrays.asList(-1, -1, -1, -1));
        PickResourcesParams pickParams = new PickResourcesParams(Orientation.HORIZONTAL,1, positions );
        turnManager.executePickResources(pickParams);
        Assertions.assertFalse(turnManager.isMainActionDone());
    }
}
