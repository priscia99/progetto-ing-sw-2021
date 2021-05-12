package it.polimi.ingsw.model.market;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.resource.ResourceStock;
import it.polimi.ingsw.model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.basic.BasicToolBarSeparatorUI;

public class CardMarketTest {
    private CardMarket cardMarket;

    @BeforeEach
    public void setUp(){
        cardMarket = CardMarket.getStartingMarket();
    }

    @Test
    @DisplayName("Test sell")
    public void testSell(){
        DevelopmentCard toSell = cardMarket.getDecks()[0][0].peek();
        Player player = new Player("Pippo");
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SERVANT, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.COIN, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.SHIELD, 10));
        player.addResourcesToStrongBox(new ResourceStock(ResourceType.STONE, 10));
        Assertions.assertEquals(toSell, cardMarket.sell(0,0, player));
        DevelopmentCard showedCard = cardMarket.getDecks()[0][0].peek();
        Assertions.assertTrue(showedCard != toSell);
    }
}