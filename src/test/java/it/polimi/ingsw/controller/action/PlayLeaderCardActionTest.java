package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.network.message.from_client.PlayLeaderCardMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayLeaderCardActionTest {
    private Game game;
    private ServerController controller;

    @BeforeEach
    public void setUp(){
        game = MockProvider.getMockGame();
        controller = new ServerController(game);
    }

    @Test
    @DisplayName("Test leader card activation")
    public void testLeaderCardActivation(){
        String cardId = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).getId();
        Assertions.assertFalse(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).isActive());
        PlayLeaderCardMessage action = new PlayLeaderCardMessage(cardId);
        action.execute(controller);
        Assertions.assertTrue(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).isActive());
    }


}
