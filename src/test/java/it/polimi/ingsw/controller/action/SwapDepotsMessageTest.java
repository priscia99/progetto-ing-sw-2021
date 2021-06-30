package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.network.message.from_client.SwapDepotsMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwapDepotsMessageTest {
    private Game game;
    private ServerController controller;

    @BeforeEach
    public void setUp() throws Exception {
        game = MockProvider.getMockGame();
        controller = new ServerController(game);
    }

    @Test
    @DisplayName("Test player tries to swap its depots correctly")
    public void testPlayerSwapsDepots(){
        Player currentPlayer = game.getCurrentPlayer(); // retrieving the current player

        // initializing the swap depots message
        SwapDepotsMessage message = new SwapDepotsMessage(0, 1);

        // adding a first resource to the player's warehouse
        assertDoesNotThrow(() -> {
            currentPlayer.addResourceToDepot(ResourceType.STONE, 0);
        });

        // adding a second resource to the player's warehouse
        assertDoesNotThrow(() -> {
            currentPlayer.addResourceToDepot(ResourceType.SHIELD, 1);
        });

        // check if player now has these resources inserted in warehouse
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(currentPlayer.getPlayerBoard().getWarehouse().getDepot(0).getResourceType().equals(ResourceType.STONE));
        });
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(currentPlayer.getPlayerBoard().getWarehouse().getDepot(1).getResourceType().equals(ResourceType.SHIELD));
        });

        // executing the swap depots message
        assertDoesNotThrow(() -> {
            message.execute(controller);
        });

        // checking if now depots are swapped
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(currentPlayer.getPlayerBoard().getWarehouse().getDepot(0).getResourceType().equals(ResourceType.SHIELD));
        });
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(currentPlayer.getPlayerBoard().getWarehouse().getDepot(1).getResourceType().equals(ResourceType.STONE));
        });

    }

    @Test
    @DisplayName("Test player tries to swap its depots incorrectly")
    public void testPlayerSwapsDepotsWrong(){
        Player currentPlayer = game.getCurrentPlayer(); // retrieving the current player

        // initializing the swap depots message
        SwapDepotsMessage message = new SwapDepotsMessage(0, 1);

        // adding a first resource to the player's warehouse
        assertDoesNotThrow(() -> {
            currentPlayer.addResourceToDepot(ResourceType.STONE, 0);
        });

        // adding a two resources resource to the player's warehouse in second depot
        assertDoesNotThrow(() -> {
            currentPlayer.addResourceToDepot(ResourceType.SHIELD, 1);
            currentPlayer.addResourceToDepot(ResourceType.SHIELD, 1);
        });

        // check if player now has these resources inserted in warehouse
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(currentPlayer.getPlayerBoard().getWarehouse().getDepot(0).getResourceType().equals(ResourceType.STONE));
        });
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(currentPlayer.getPlayerBoard().getWarehouse().getDepot(1).getResourceType().equals(ResourceType.SHIELD));
            Assertions.assertEquals(2, currentPlayer.getPlayerBoard().getWarehouse().getDepot(1).getQuantity());
        });

        // executing the message and check if depots cannot be swapped
        Exception exception = assertThrows(Exception.class, () -> {
            message.execute(controller);
        });

        // checking if depots remains the same after the exception was received
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(currentPlayer.getPlayerBoard().getWarehouse().getDepot(0).getResourceType().equals(ResourceType.STONE));
        });
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(currentPlayer.getPlayerBoard().getWarehouse().getDepot(1).getResourceType().equals(ResourceType.SHIELD));
            Assertions.assertEquals(2, currentPlayer.getPlayerBoard().getWarehouse().getDepot(1).getQuantity());
        });
    }
}
