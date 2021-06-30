package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.network.message.from_client.RemoveResourceMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RemoveResourceMessageTest {
    private Game game;
    private ServerController controller;

    @BeforeEach
    public void setUp() throws Exception {
        game = MockProvider.getMockGame();
        controller = new ServerController(game);
    }

    @Test
    @DisplayName("Test player tries to remove an existent resource from its depot")
    public void testPlayerRemovesResourceFromDepot(){
        String playerName = game.getCurrentPlayer().getNickname();
        Player player = game.getCurrentPlayer();
        // adding a resource to the player's warehouse
        assertDoesNotThrow(() -> {
            player.addResourceToDepot(ResourceType.STONE, 0);
        });

        // check if the resources has been added correctly
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(player.getPlayerBoard().getWarehouse().getDepot(0).getResourceType().equals(ResourceType.STONE));
        });

        // now try to remove the previously added resource from the depot
        RemoveResourceMessage message = new RemoveResourceMessage(ResourcePosition.FIRST_DEPOT);
        assertDoesNotThrow(() -> {
            message.execute(controller);
        });

        // check if the resources has been removed correctly
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(player.getPlayerBoard().getWarehouse().getDepot(0).getResourceType().equals(ResourceType.BLANK));
        });
    }

    @Test
    @DisplayName("Test player tries to remove a not existant resource from its depot")
    public void testPlayerRemovesWrongResourceFromDepot(){
        String playerName = game.getCurrentPlayer().getNickname();
        Player player = game.getCurrentPlayer();

        // check if the depot is empty
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(player.getPlayerBoard().getWarehouse().getDepot(0).getResourceType().equals(ResourceType.BLANK));
        });

        // now try to remove the previously added resource from the depot
        RemoveResourceMessage message = new RemoveResourceMessage(ResourcePosition.FIRST_DEPOT);

        // check if the resource cannot be removed
        Exception exception = assertThrows(Exception.class, () -> {
            message.execute(controller);
        });
        String expectedMessage = "Cannot remove from empty depot!";
        assertTrue(exception.getMessage().contains(expectedMessage));

        // check if the depot is still empty
        assertDoesNotThrow(() -> {
            Assertions.assertTrue(player.getPlayerBoard().getWarehouse().getDepot(0).getResourceType().equals(ResourceType.BLANK));
        });
    }

    @Test
    @DisplayName("Test if removing resource adds faith points to other players")
    public void testAddingPointsToOtherPlayers(){
        this.testPlayerRemovesResourceFromDepot();
        String currentPlayer = game.getCurrentPlayer().getNickname();
        assertDoesNotThrow(() ->{
            game.getPlayers().stream().filter(player -> player.getNickname()!=currentPlayer).forEach(
                    player -> {
                        try {
                            assertEquals(1, game.getPlayerByUsername(player.getNickname()).getPlayerBoard().getFaithPath().getFaithPoints());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
            );
        });
    }
}
