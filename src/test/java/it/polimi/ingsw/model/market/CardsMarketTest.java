package it.polimi.ingsw.model.market;


import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.market.CardsMarket;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CardsMarketTest {
    private CardsMarket cardsMarket;

    @BeforeEach
    public void setUp() throws Exception {
        cardsMarket = CardsMarket.getStartingMarket();
    }

    @Test
    @DisplayName("Test sell")
    public void testSell() throws Exception {
        DevelopmentCard toSell = cardsMarket.getDecks()[0][0].peek();
        Player player = new Player("Pippo");
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SERVANT, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.COIN, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SHIELD, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.STONE, 10));
        try {
            Assertions.assertEquals(toSell, cardsMarket.sell(toSell.getId(), player));
        }catch (Exception e){
            e.printStackTrace();
        }
        DevelopmentCard showedCard = cardsMarket.getDecks()[0][0].peek();
        Assertions.assertTrue(showedCard != toSell);
    }

}
