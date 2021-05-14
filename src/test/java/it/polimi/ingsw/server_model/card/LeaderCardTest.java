package it.polimi.ingsw.server_model.card;
import it.polimi.ingsw.server_model.card.effect.DiscountEffect;
import it.polimi.ingsw.server_model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class LeaderCardTest {
    private LeaderCard leaderCard;

    @BeforeEach
    public void setUp(){
        this.leaderCard = new LeaderCard(5, new DiscountEffect(ResourceType.COIN), null);
    }

    @Test
    @DisplayName("Ensure activation works")
    public void testActive(){
        this.leaderCard.play();
        Assertions.assertTrue(leaderCard.isActive(), "Card should be active");
    }

}
