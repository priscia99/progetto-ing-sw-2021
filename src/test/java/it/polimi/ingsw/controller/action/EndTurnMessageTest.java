package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.network.message.from_client.EndTurnMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EndTurnMessageTest {
    private Game game;
    private ServerController controller;

    @BeforeEach
    public void setUp() throws Exception {
        game = MockProvider.getMockGame();
        controller = new ServerController(game);
    }

    @Test
    @DisplayName("Test player ends its turn correctly")
    public void testPlayerEndsTurn(){
        Player currentPlayer = game.getCurrentPlayer(); // retrieving the current player
        EndTurnMessage message = new EndTurnMessage();

        // Check if player is now still in turn
        Assertions.assertEquals(game.getCurrentPlayer().getNickname(), currentPlayer.getNickname());

        assertDoesNotThrow(() -> {
            message.execute(controller);
        });

        // Check if now player is not in turn anymore
        Assertions.assertNotEquals(game.getCurrentPlayer().getNickname(), currentPlayer.getNickname());
    }

}
