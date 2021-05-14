package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayLeaderCardActionTest {
    private Game game;

    @BeforeEach
    public void setUp(){
        game = MockProvider.getMockGame();
    }

    @Test
    @DisplayName("Test leader card activation")
    public void testLeaderCardActivation(){
        String cardId = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).getId();
        Assertions.assertFalse(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).isActive());
        PlayLeaderCardAction action = new PlayLeaderCardAction(cardId);
        action.execute(game);
        Assertions.assertTrue(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).isActive());
    }


}
