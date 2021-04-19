package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.DepotEffect;
import it.polimi.ingsw.model.card.requirement.Requirement;
import it.polimi.ingsw.model.player_board.storage.Depot;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp(){
        player = new Player("Tested");
    }

    @Test
    @DisplayName("Test picked leader cards")
    public void testPickedLeaderCards(){
        List<LeaderCard> pickedCards = new ArrayList<>();
        LeaderCard cardToCheck = MockProvider.getLeaderCardMock();
        pickedCards.add(cardToCheck);
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

    @Disabled("Disabled until the fix of player initialization.")
    @Test
    @DisplayName("Ensure that the counter of player's resources work correctly")
    public void testCountByResources() {
        // setup player
        this.player = new Player("Giocatore 1");
        player.getPlayerBoard().addToDepot(1, ResourceType.COIN);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( false,1, 1, null, Color.PURPLE, null),
                0);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard(false, 1, 2, null, Color.BLUE, null),
                0
        );
        // test
        Assertions.assertEquals(1, player.countByResource(ResourceType.COIN), "Count of COINs should be 1.");
        Assertions.assertEquals(0, player.countByResource(ResourceType.SERVANT), "Count of SERVANTs should be 0.");
    }

    @Disabled("Disabled until the fix of player initialization.")
    @Test
    @DisplayName("Ensure that the counter of player's cards' colors work correctly")
    public void testCountByColor() {
        // setup player
        this.player = new Player("Giocatore 1");
        player.getPlayerBoard().addToDepot(1, ResourceType.COIN);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( false,1, 1, null, Color.PURPLE, null),
                0);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard(false, 1, 2, null, Color.PURPLE, null),
                0
        );
        // test
        Assertions.assertEquals(2, player.countByColor(Color.PURPLE), "Count of PURPLE cards should be 2.");
        Assertions.assertEquals(0, player.countByColor(Color.GREEN), "Count of GREEN cards should be 0.");
    }

    @Disabled("Disabled until the fix of player initialization.")
    @Test
    @DisplayName("Ensure that the counter of player's resources' levels work correctly")
    public void testCountByLevel() {
        // setup player
        this.player = new Player("Giocatore 1");
        player.getPlayerBoard().addToDepot(1, ResourceType.COIN);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( false,1, 1, null, Color.PURPLE, null),
                0);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard(false, 1, 1, null, Color.BLUE, null),
                1
        );
        // test
        Assertions.assertEquals(2, player.countByLevel(1), "Count of level ONE cards should be 2.");
        Assertions.assertEquals(0, player.countByLevel(3), "Count of level THREE cards should be 0.");
    }

    @Disabled("Disabled until the fix of player initialization.")
    @Test
    @DisplayName("Ensure getter of player's cards' by color work correctly")
    public void testColorByLevel() {
        // setup player
        this.player = new Player("Giocatore 1");
        player.getPlayerBoard().addToDepot(1, ResourceType.COIN);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( false,1, 1, null, Color.PURPLE, null),
                0);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard(false, 1, 2, null, Color.BLUE, null),
                0
        );
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard(false, 1, 1, null, Color.GREEN, null),
                1
        );
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard(false, 1, 1, null, Color.GREEN, null),
                2
        );

        // test level 1
        ArrayList<Color> lv1 = player.colorByLevel(1);
        Assertions.assertEquals(Color.PURPLE, lv1.get(0), "The first level ONE card should be PURPLE.");
        Assertions.assertEquals(Color.GREEN, lv1.get(0), "The second level ONE card should be GREEN.");
        Assertions.assertEquals(2, lv1.size(), "The list of colors of level ONE cards should contain only 2 values.");
        // test level 2
        ArrayList<Color> lv2 = player.colorByLevel(2);
        Assertions.assertEquals(Color.BLUE, lv2.get(0), "The level TWO card should be BLUE.");
        // test level 3
        ArrayList<Color> lv3 = player.colorByLevel(3);
        Assertions.assertEquals(0, lv3.size(), "The list of level THREE cards should be empty.");
    }
}
