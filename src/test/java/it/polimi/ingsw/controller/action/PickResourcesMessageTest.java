package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.exceptions.IllegalResourceException;
import it.polimi.ingsw.network.message.from_client.PickResourcesMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.marble.Orientation;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.testUtils.MockProvider;
import it.polimi.ingsw.testUtils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PickResourcesMessageTest {
    private Game game;
    private ServerController controller;

    @BeforeEach
    public void setUp(){
        game = MockProvider.getMockGame();
        controller = new ServerController(game);
    }

    @Test
    @DisplayName("Test player picked resources from marble market")
    public void testPlayerPickedResources(){
        // Generate mock marble market for testing purposes
        MockProvider.generateMockMarbleMarket(game);
        // Create a proper selection from marble market
        MarbleSelection marbleSelection = new MarbleSelection(Orientation.HORIZONTAL, 0);
        // Create positions array list
        ArrayList<ResourcePosition> positions = new ArrayList<>();
        positions.add(ResourcePosition.FIRST_DEPOT);
        positions.add(ResourcePosition.SECOND_DEPOT);
        positions.add(ResourcePosition.SECOND_DEPOT);
        // Creating a proper message
        PickResourcesMessage message = new PickResourcesMessage(marbleSelection, positions, new ArrayList<>());
        assertDoesNotThrow(() -> {
            message.execute(controller);
        });
        // Checking if resources were stored correctly in player's warehouse
        assertEquals(game.getCurrentPlayer().getPlayerBoard().getWarehouse().getDepot(0).getResourceType(), ResourceType.STONE);
        assertEquals(game.getCurrentPlayer().getPlayerBoard().getWarehouse().getDepot(1).getResourceType(), ResourceType.SERVANT);
        assertEquals(1, game.getCurrentPlayer().getPlayerBoard().getWarehouse().getDepot(0).getQuantity());
        assertEquals(2, game.getCurrentPlayer().getPlayerBoard().getWarehouse().getDepot(1).getQuantity());
    }

    @Test
    @DisplayName("Test player tries to insert a resource in strongbox")
    public void testPlayerInsertResourceInStrongbox(){
        // Generate mock marble market for testing purposes
        MockProvider.generateMockMarbleMarket(game);
        // Create a proper selection from marble market
        MarbleSelection marbleSelection = new MarbleSelection(Orientation.HORIZONTAL, 0);
        // Create positions array list
        ArrayList<ResourcePosition> positions = new ArrayList<>();
        positions.add(ResourcePosition.FIRST_DEPOT);
        positions.add(ResourcePosition.STRONG_BOX);
        positions.add(ResourcePosition.SECOND_DEPOT);
        // Creating a proper message
        PickResourcesMessage message = new PickResourcesMessage(marbleSelection, positions, new ArrayList<>());
        Exception exception = assertThrows(Exception.class, () -> {
            message.execute(controller);
        });
        String expectedMessage = "Cannot insert resources in strongbox!";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("Test insert different resource types in the same depot")
    public void testInsertDifferentTypesSameDepot(){
        // Generate mock marble market for testing purposes
        MockProvider.generateMockMarbleMarket(game);
        // Create a proper selection from marble market
        MarbleSelection marbleSelection = new MarbleSelection(Orientation.HORIZONTAL, 0);
        // Create positions array list
        ArrayList<ResourcePosition> positions = new ArrayList<>();
        positions.add(ResourcePosition.SECOND_DEPOT);
        positions.add(ResourcePosition.SECOND_DEPOT);
        positions.add(ResourcePosition.SECOND_DEPOT);
        // Creating a proper message
        PickResourcesMessage message = new PickResourcesMessage(marbleSelection, positions, new ArrayList<>());
        Exception exception = assertThrows(IllegalResourceException.class, () -> {
                message.execute(controller);
         });
        System.out.println("Got: " + exception.getMessage());
        String expectedMessage = "You cannot have different resource types in the same depot";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
