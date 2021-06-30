package it.polimi.ingsw.model.market;


import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.market.CardsMarket;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Stack;

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

    @Test
    @DisplayName("Test cards market remove card by color")
    public void testRemoveCardByColor(){
        // test removing a purple card

        // counting all available purple cards
        int purpleCardsInitialCount = 0;
        Stack<DevelopmentCard>[][] decks = cardsMarket.getDecks();
        for(int i=0; i<3; i++){
           purpleCardsInitialCount += decks[0][i].size();
        }

        // removing a card from the cards market whose color is purple
        cardsMarket.removeByColor(Color.PURPLE, 1);

        // counting all available purple cards
        int purpleCardsEndingCount = 0;
        for(int i=0; i<3; i++){
            purpleCardsEndingCount += decks[0][i].size();
        }

        Assertions.assertEquals(purpleCardsInitialCount-1, purpleCardsEndingCount);
        System.out.println();
    }

}
