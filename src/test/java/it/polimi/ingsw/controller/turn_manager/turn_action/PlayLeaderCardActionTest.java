package it.polimi.ingsw.controller.turn_manager.turn_action;

import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.controller.turn_manager.action_params.PlayLeaderCardParams;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        String cardId = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).getId();
        Assertions.assertFalse(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).isActive());
        PlayLeaderCardParams params = new PlayLeaderCardParams(cardId);
        action.execute(game, params);
        Assertions.assertTrue(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).isActive());
    }


}
