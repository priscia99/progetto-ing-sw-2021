package it.polimi.ingsw.model.game;

import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGame;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SinglePlayerGameTest {
    private SinglePlayerGame game;
    private ServerController controller;

    @BeforeEach
    public void setUp() throws Exception {
        game = MockProvider.getSinglePlayerMockGame();
        controller = new ServerController(game);
    }

    @Test
    @DisplayName("Test Lorenzo's initial position is at beginning of faith path")
    public void testLorenzoInitialPosition(){
        Assertions.assertEquals(0, game.getBlackCrossPosition());
    }

    @Test
    @DisplayName("Test if next turn is correct")
    public void testNextTurn(){
        String currentPlayer = game.getCurrentPlayer().getNickname();
        assertDoesNotThrow(() -> {
            game.nextTurn();
        });
        Assertions.assertEquals(game.getCurrentPlayer().getNickname(), currentPlayer);
    }

}
