package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.card.color.ColorPile;
import it.polimi.ingsw.server.model.card.requirement.ColorRequirement;
import it.polimi.ingsw.server.model.card.requirement.LevelRequirement;
import it.polimi.ingsw.server.model.card.requirement.ResourceRequirement;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RequirementTest {

    private Player player;

    @BeforeEach
    public void setup () {
        this.player = new Player("Giocatore 1");
    }

    @Test
    @DisplayName("Test single resource requirement fulfil")
    public void testSingleResourceRequirement(){
        ArrayList<ResourceStock> stockToFulfil = new ArrayList<>();
        stockToFulfil.add(new ResourceStock(ResourceType.COIN, 1));
        ResourceRequirement requirement = new ResourceRequirement(stockToFulfil);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.COIN, 1));
        Assertions.assertTrue(requirement.isFulfilled(player));
    }

    @Test
    @DisplayName("Test multiple resource requirement fulfil")
    public void testMultipleResourceRequirement(){
        ArrayList<ResourceStock> stockToFulfil = new ArrayList<>();
        stockToFulfil.add(new ResourceStock(ResourceType.COIN, 1));
        stockToFulfil.add(new ResourceStock(ResourceType.SERVANT, 1));
        ResourceRequirement requirement = new ResourceRequirement(stockToFulfil);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.COIN, 1));
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SHIELD, 1));
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SERVANT, 1));
        Assertions.assertTrue(requirement.isFulfilled(player));
    }

    @Test
    @DisplayName("Test level requirement")
    public void testLevelRequirement(){
        LevelRequirement requirement = new LevelRequirement(Color.YELLOW);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.BLUE, null, "1"), 0);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.YELLOW, null, "5"), 1);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 2, null, Color.YELLOW, null, "29"), 1);
        Assertions.assertTrue(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 3, null, Color.YELLOW, null, "30"), 1);
    }

    @Test
    @DisplayName("Test single color requirement")
    public void testColorRequirement(){
        ArrayList<ColorPile> toFulfil = new ArrayList<>();
        toFulfil.add(new ColorPile(Color.BLUE, 2));
        ColorRequirement requirement = new ColorRequirement(toFulfil);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.BLUE, null, "77"), 0);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 2, null, Color.BLUE, null, "333"), 0);
        Assertions.assertTrue(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 3, null, Color.BLUE, null, "666"), 0);
        Assertions.assertTrue(requirement.isFulfilled(player));
    }

    @Test
    @DisplayName("Test multiple colors requirement")
    public void testMultipleColorRequirement(){
        ArrayList<ColorPile> toFulfil = new ArrayList<>();
        toFulfil.add(new ColorPile(Color.BLUE, 2));
        toFulfil.add(new ColorPile(Color.YELLOW, 1));
        ColorRequirement requirement = new ColorRequirement(toFulfil);
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.BLUE, null, "23"),0);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 2, null, Color.BLUE, null, "34"), 0);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.YELLOW, null, "73"), 2);
        Assertions.assertTrue(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.GREEN, null, "98"), 1);
        Assertions.assertTrue(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 2, null, Color.GREEN, null, "57"), 1);
        Assertions.assertTrue(requirement.isFulfilled(player));
    }
}
