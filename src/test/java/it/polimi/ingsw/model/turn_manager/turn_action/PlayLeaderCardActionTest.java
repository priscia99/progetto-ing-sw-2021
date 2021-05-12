package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.turn_manager.action_params.DropLeaderCardParams;
import it.polimi.ingsw.model.turn_manager.action_params.PlayLeaderCardParams;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class PlayLeaderCardActionTest {
    private PlayLeaderCardAction action;
    private Game game;

    @BeforeEach
    public void setUp(){
        action = new PlayLeaderCardAction();
        game = MockProvider.getMockGame();
    }

    @Test
    @DisplayName("Test leader card activation")
    public void testLeaderCardActivation(){
        HashMap<String, Object> map = new HashMap<>();
        String cardId = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).getId();
        map.put("cardId", cardId);
        Assertions.assertFalse(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).isActive());
        PlayLeaderCardParams params = PlayLeaderCardParams.fromMap(map);
        action.execute(game, params);
        Assertions.assertTrue(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).isActive());
    }


}
