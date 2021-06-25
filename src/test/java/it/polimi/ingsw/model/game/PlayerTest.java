package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp(){
        player = new Player("Tested");
    }

    @Test
    @DisplayName("Test picked leader cards")
    public void testPickedLeaderCards() throws GameException {
        player.getPlayerBoard().addToLeaderCardsDeck(MockProvider.getArrayListLeaderCardsMock());
        ArrayList<String> pickedCards = new ArrayList<>();
        LeaderCard cardToCheck = MockProvider.getLeaderCardMock();
        player.getPlayerBoard().getLeaderCardsDeck().addLeader(cardToCheck);
        pickedCards.add(cardToCheck.getId());
        player.pickedLeaderCards(pickedCards);
        Assertions.assertEquals(1, player.getPlayerBoard().getLeaderCardsDeck().getLeaderCards().size());
        Assertions.assertEquals(cardToCheck, player.getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0));
    }

    @Test
    @DisplayName("Test player has LeaderCards")
    public void testPlayerHasLeaderCards(){
        Assertions.assertFalse(player.hasLeaderCards());
        LeaderCard cardToAdd = MockProvider.getLeaderCardMock();
        player.getPlayerBoard().getLeaderCardsDeck().addLeader(cardToAdd);
        Assertions.assertTrue(player.hasLeaderCards());
    }

    @Test
    @DisplayName("Ensure that the counter of player's resources work correctly")
    public void testCountByResources() throws GameException {
        // setup player
        player.getPlayerBoard().addToDepot(1, ResourceType.COIN);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 1, null, Color.PURPLE, null, "1"),
                0);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 2, null, Color.BLUE, null, "2"),
                0
        );
        // test
        Assertions.assertEquals(1, player.countByResource(ResourceType.COIN), "Count of COINs should be 1.");
        Assertions.assertEquals(0, player.countByResource(ResourceType.SERVANT), "Count of SERVANTs should be 0.");
    }

    @Test
    @DisplayName("Ensure that the counter of player's cards' colors work correctly")
    public void testCountByColor() throws GameException {
        // setup player
        player.getPlayerBoard().addToDepot(1, ResourceType.COIN);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 1, null, Color.PURPLE, null, "1"),
                0);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 2, null, Color.PURPLE, null, "1"),
                0
        );
        // test
        Assertions.assertEquals(2, player.countByColor(Color.PURPLE), "Count of PURPLE cards should be 2.");
        Assertions.assertEquals(0, player.countByColor(Color.GREEN), "Count of GREEN cards should be 0.");
    }

    @Test
    @DisplayName("Ensure that the counter of player's resources' levels work correctly")
    public void testCountByLevel() throws GameException {
        // setup player
        player.getPlayerBoard().addToDepot(1, ResourceType.COIN);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 1, null, Color.PURPLE, null, "1"),
                0);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 1, null, Color.BLUE, null, "1"),
                1
        );
        // test
        Assertions.assertEquals(2, player.countByLevel(1), "Count of level ONE cards should be 2.");
        Assertions.assertEquals(0, player.countByLevel(3), "Count of level THREE cards should be 0.");
    }

    @Test
    @DisplayName("Ensure getter of player's cards' by color work correctly")
    public void testColorByLevel() throws GameException {
        // setup player
        player.getPlayerBoard().addToDepot(1, ResourceType.COIN);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 1, null, Color.PURPLE, null, "1"),
                0);

        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 1, null, Color.GREEN, null, "1"),
                1
        );

        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 2, null, Color.BLUE, null, "1"),
                1
        );

        // test level 1
        ArrayList<Color> lv1 = player.colorByLevel(1);
        Assertions.assertEquals(Color.PURPLE, lv1.get(0), "The first level ONE card should be PURPLE.");
        Assertions.assertEquals(Color.GREEN, lv1.get(1), "The second level ONE card should be GREEN.");
        Assertions.assertEquals(2, lv1.size(), "The list of colors of level ONE cards should contain only 2 values.");
        // test level 2
        ArrayList<Color> lv2 = player.colorByLevel(2);
        Assertions.assertEquals(Color.BLUE, lv2.get(0), "The level TWO card should be BLUE.");
        // test level 3
        ArrayList<Color> lv3 = player.colorByLevel(3);
        Assertions.assertEquals(0, lv3.size(), "The list of level THREE cards should be empty.");
    }

    @Test
    @DisplayName("Test initial resource logic")
    public void testInitialResource(){
        player.setInitialResourceToChoose(2);
        Assertions.assertTrue(player.hasToChooseInitialResource());
        player.hasChosenInitialResource();
        Assertions.assertTrue(player.hasToChooseInitialResource());
        player.hasChosenInitialResource();
        Assertions.assertFalse(player.hasToChooseInitialResource());
    }

    @Test
    @DisplayName("Test meets win conditions for faith path")
    public void testMeetConditionFaithPath(){
        for(int i=0; i<24; i++) {
            player.addFaithPoint();
        }
        Assertions.assertFalse(player.finishedFaithPath());
        player.addFaithPoint();
        Assertions.assertTrue(player.finishedFaithPath());
    }

    @Test
    @DisplayName("Test meets win conditions for development cards")
    public void testMeetConditionDevelopmentCards() throws GameException {
        ArrayList<DevelopmentCard> cards = MockProvider.getArrayDevelopmentCardsMock();
        for(int i = 0; i<6; i++) {
            player.getPlayerBoard().addDevelopmentCard(cards.get(i), i%3 );
            Assertions.assertFalse(player.getTotalDevelopmentCards()>6);
        }
        player.getPlayerBoard().addDevelopmentCard(cards.get(6), 0 );
        Assertions.assertTrue(player.getTotalDevelopmentCards()>6);
    }

    @Test
    @DisplayName("Test add to strongbox")
    public void testAddToStrongBox() throws GameException {
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.COIN, 1));
        Assertions.assertEquals(1, player.getPlayerBoard().getStrongbox().countByResourceType(ResourceType.COIN));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SERVANT, 1));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SHIELD, 3));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.COIN, 2));
        Assertions.assertEquals(3, player.getPlayerBoard().getStrongbox().countByResourceType(ResourceType.COIN));
        Assertions.assertEquals(1, player.getPlayerBoard().getStrongbox().countByResourceType(ResourceType.SERVANT));
        Assertions.assertEquals(3, player.getPlayerBoard().getStrongbox().countByResourceType(ResourceType.SHIELD));
        Assertions.assertEquals(0, player.getPlayerBoard().getStrongbox().countByResourceType(ResourceType.STONE));
    }

    @Test
    @DisplayName("Test add to warehouse")
    public void testAddToWarehouse() throws GameException {
        player.addResourceToDepot(ResourceType.COIN, 1);
        Assertions.assertEquals(1, player.getPlayerBoard().getWarehouse().countByResourceType(ResourceType.COIN));
        player.addResourceToDepot(ResourceType.COIN, 1);
        player.addResourceToDepot(ResourceType.SHIELD, 2);
        player.addResourceToDepot(ResourceType.SHIELD, 2);
        Assertions.assertEquals(2, player.getPlayerBoard().getWarehouse().countByResourceType(ResourceType.SHIELD));
        player.addResourceToDepot(ResourceType.SHIELD, 2);
        Assertions.assertEquals(0, player.getPlayerBoard().getWarehouse().countByResourceType(ResourceType.SERVANT));
        player.addResourceToDepot(ResourceType.SERVANT, 0);
        Assertions.assertEquals(2, player.getPlayerBoard().getWarehouse().countByResourceType(ResourceType.COIN));
        Assertions.assertEquals(1, player.getPlayerBoard().getWarehouse().countByResourceType(ResourceType.SERVANT));
        Assertions.assertEquals(3, player.getPlayerBoard().getWarehouse().countByResourceType(ResourceType.SHIELD));
        Assertions.assertEquals(0, player.getPlayerBoard().getWarehouse().countByResourceType(ResourceType.STONE));
    }
}
