package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.color.ColorPile;
import it.polimi.ingsw.model.card.requirement.ColorRequirement;
import it.polimi.ingsw.model.card.requirement.LevelRequirement;
import it.polimi.ingsw.model.card.requirement.ResourceRequirement;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.resource.ResourceStock;
import it.polimi.ingsw.model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class requirementTest {

    private ResourceRequirement resourceRequirement;
    private LevelRequirement levelRequirement;
    private ColorRequirement colorRequirement;
    private Player player;

    @BeforeEach
    public void setup () {
        // setup player
        this.player = new Player("Giocatore 1");
        // add resources
        player.getPlayerBoard().addToDepot(1, ResourceType.COIN);
        // add development cards
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 1, null, Color.PURPLE, null),
                0);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 2, null, Color.PURPLE, null),
                0);
        player.getPlayerBoard().addDevelopmentCard(
                new DevelopmentCard( 1, 1, null, Color.BLUE, null),
                1
        );

        // setup resource requirement
        ArrayList<ResourceStock> resources = new ArrayList<ResourceStock>();
        resources.add(new ResourceStock(ResourceType.COIN, 1));
        this.resourceRequirement = new ResourceRequirement(resources);

        // setup level requirement
        this.levelRequirement = new LevelRequirement(Color.PURPLE);

        // setup color requirement
        ArrayList<ColorPile> colors = new ArrayList<ColorPile>();
        colors.add(new ColorPile(Color.PURPLE, 1));
        colors.add(new ColorPile(Color.BLUE, 1));
        this.colorRequirement = new ColorRequirement(colors);
    }

    @Test
    public void testIsFulfilled () {
        Assertions.assertTrue(
                this.colorRequirement.isFulfilled(this.player),
                "colorRequirement should be fulfilled"
        );
        Assertions.assertTrue(
                this.levelRequirement.isFulfilled(this.player),
                "levelRequirement should be fulfilled"
        );
        Assertions.assertTrue(
                this.resourceRequirement.isFulfilled(this.player),
                "resourceRequirement should be fulfilled"
        );
    }
}
