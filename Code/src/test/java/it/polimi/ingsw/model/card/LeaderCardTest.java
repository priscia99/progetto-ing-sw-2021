package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.LeaderCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class LeaderCardTest {
    private LeaderCard leaderCard;

    @BeforeEach
    public void setUp(){
        this.leaderCard = new LeaderCard(false, 5, null, null);
    }

    @Test
    @DisplayName("Ensure activation works")
    public void testActive(){
        this.leaderCard.play();
        Assertions.assertTrue(leaderCard.isActive(), "Card should be active");
    }

}
