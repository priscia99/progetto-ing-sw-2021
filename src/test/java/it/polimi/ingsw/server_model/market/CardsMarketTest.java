package it.polimi.ingsw.server_model.market;
import it.polimi.ingsw.server_model.card.DevelopmentCard;
import it.polimi.ingsw.server_model.game.Player;
import it.polimi.ingsw.server_model.resource.ResourceStock;
import it.polimi.ingsw.server_model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CardsMarketTest {
    private CardsMarket cardsMarket;

    @BeforeEach
    public void setUp(){
        cardsMarket = CardsMarket.getStartingMarket();
    }

    @Test
    @DisplayName("Test sell")
    public void testSell(){
        DevelopmentCard toSell = cardsMarket.getDecks()[0][0].peek();
        Player player = new Player("Pippo");
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SERVANT, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.COIN, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SHIELD, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.STONE, 10));
        Assertions.assertEquals(toSell, cardsMarket.sell(0,0, player));
        DevelopmentCard showedCard = cardsMarket.getDecks()[0][0].peek();
        Assertions.assertTrue(showedCard != toSell);
    }
}
