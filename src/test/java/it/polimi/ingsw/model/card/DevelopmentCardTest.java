package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.color.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class DevelopmentCardTest {
    private DevelopmentCard developmentCard;

    @BeforeEach
    public void setUp(){
        this.developmentCard = new DevelopmentCard( 5, 1, null, Color.PURPLE, null);
    }

    @Test
    @DisplayName("Ensure activation works")
    public void testActive(){
        this.developmentCard.play();
        Assertions.assertTrue(developmentCard.isActive(), "Card should be active");
    }

    @Test
    @DisplayName("Ensure level is correct")
    public void testLevel(){
        Assertions.assertEquals(1,developmentCard.getLevel(),"Level should be 1");
    }

    @Test
    @DisplayName("Ensure color is correct")
    public void testColor(){
        Assertions.assertEquals(Color.PURPLE,developmentCard.getColor(),"Color should be PURPLE");
    }
}