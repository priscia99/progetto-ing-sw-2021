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
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.BLUE, null), 0);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.YELLOW, null), 1);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 2, null, Color.YELLOW, null), 1);
        Assertions.assertTrue(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 3, null, Color.YELLOW, null), 1);
    }

    @Test
    @DisplayName("Test single color requirement")
    public void testColorRequirement(){
        ArrayList<ColorPile> toFulfil = new ArrayList<>();
        toFulfil.add(new ColorPile(Color.BLUE, 2));
        ColorRequirement requirement = new ColorRequirement(toFulfil);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.BLUE, null), 0);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 2, null, Color.BLUE, null), 0);
        Assertions.assertTrue(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 3, null, Color.BLUE, null), 0);
        Assertions.assertTrue(requirement.isFulfilled(player));
    }

    @Test
    @DisplayName("Test multiple colors requirement")
    public void testMultipleColorRequirement(){
        ArrayList<ColorPile> toFulfil = new ArrayList<>();
        toFulfil.add(new ColorPile(Color.BLUE, 2));
        toFulfil.add(new ColorPile(Color.YELLOW, 1));
        ColorRequirement requirement = new ColorRequirement(toFulfil);
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.BLUE, null), 0);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 2, null, Color.BLUE, null), 0);
        Assertions.assertFalse(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.YELLOW, null), 2);
        Assertions.assertTrue(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 1, null, Color.GREEN, null), 1);
        Assertions.assertTrue(requirement.isFulfilled(player));
        player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(1, 2, null, Color.GREEN, null), 1);
        Assertions.assertTrue(requirement.isFulfilled(player));
    }
}
