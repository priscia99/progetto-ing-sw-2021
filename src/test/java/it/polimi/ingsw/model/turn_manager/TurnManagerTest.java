package it.polimi.ingsw.model.turn_manager;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.marble.Orientation;
import it.polimi.ingsw.model.turn_manager.action_params.DropLeaderCardParams;
import it.polimi.ingsw.model.turn_manager.action_params.PickResourcesParams;
import it.polimi.ingsw.model.turn_manager.turn_action.PickResourcesAction;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
        HashMap<String, Object> netDropParams = new HashMap<>();
        netDropParams.put("cardIndex", leaderCard.getId());
        DropLeaderCardParams params = DropLeaderCardParams.fromMap(netDropParams);
        turnManager.executeDropLeaderCard(params);
        Assertions.assertFalse(turnManager.isMainActionDone());
        HashMap<String, Object> netPickParams = new HashMap<>();
        netPickParams.put("orientation", Orientation.HORIZONTAL);
        netPickParams.put("index", 1);
        ArrayList<Integer> positions = new ArrayList<>(Arrays.asList(-1, -1, -1, -1));
        netPickParams.put("positions", positions);
        PickResourcesParams pickParams = PickResourcesParams.fromMap(netPickParams);
        turnManager.executePickResources(pickParams);
        Assertions.assertFalse(turnManager.isMainActionDone());
    }
}