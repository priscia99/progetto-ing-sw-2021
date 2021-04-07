package it.polimi.ingsw;

import it.polimi.ingsw.data.DevCardMarketBuilder;
import it.polimi.ingsw.data.DevelopmentCardsBuilder;
import it.polimi.ingsw.data.LeaderCardsBuilder;
import it.polimi.ingsw.data.MarbleMarketBuilder;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.marble.Marble;
import it.polimi.ingsw.model.market.MarbleMarket;
import it.polimi.ingsw.utils.CustomLogger;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;

public class TestParser
{
    public static void main( String[] args )
    {
        // --- HOW TO GENERATE THE LEADER CARDS DECK
        ArrayList<LeaderCard> leaderCardDeck = LeaderCardsBuilder.getDeck();

        for(int i=0; i<leaderCardDeck.size(); i++){
           System.out.println("--- CARD " + (i+1) + " ---\n" + leaderCardDeck.get(i));
        }

        // --- HOW TO GENERATE THE DEVELOPMENT CARDS DECK
        ArrayList<DevelopmentCard> devCardDeck = DevelopmentCardsBuilder.getDeck();

        for(int i=0; i<devCardDeck.size(); i++){
            System.out.println("--- CARD " + (i+1) + " ---\n" + devCardDeck.get(i));
        }

        // --- HOW TO GENERATE THE MARBLE MARKET USING MARBLEMARKETBUILDER
        MarbleMarketBuilder marbleMarketBuilder = new MarbleMarketBuilder();    // First of all, create a new MarbleMarketBuilder() object
        Marble[][] market = marbleMarketBuilder.getMarket();                    // Then, invoke getMarket() method

        for (Marble[] marbles : market) {
            for (Marble marble : marbles) {
                System.out.print(marble.getResourceType().name() + "\t");
            }
            System.out.print("\n");
        }

        // --- HOW TO GET THE NOT FOR SALE MARBLE USING MARBLEMARKETBUILDER
        Marble notForSale = marbleMarketBuilder.getNotForSale();    // IMPORTANT! Use the previously instanced object!

        System.out.println("Not for sale marble: " + notForSale.getResourceType().name());
        /*
            Please note MarbleMarketBuilder is slightly different from the other builders.
            It is not a Singleton for the simple reason we may need to generate different markets with different
            marbles position if we implement multiple games functionality in the same server.
            Using a singleton class, we would receive the same market with same positions.
         */

        // The following code is just an example created to show that it's possibile to generate a totally different
        // marble market using a new Builder

        MarbleMarketBuilder anotherBuilder = new MarbleMarketBuilder();
        Marble[][] anotherMarket = anotherBuilder.getMarket();
        Marble anotherNotForSale = anotherBuilder.getNotForSale();

        System.out.println("\n");
        for (Marble[] marbles : anotherMarket) {
            for (Marble marble : marbles) {
                System.out.print(marble.getResourceType().name() + "\t");
            }
            System.out.print("\n");
        }
        System.out.println("Not for sale marble: " + anotherNotForSale.getResourceType().name());

        // HOW TO GET A STACK OF DEV CARDS GIVEN LEVEL AND COLOR
        Stack<DevelopmentCard> deck = DevCardMarketBuilder.getStackByLevelColor(1, Color.GREEN);    // Call the builder
        while(deck.size() > 0)
            System.out.println(deck.pop()); // Use pop() to take the first top card
    }
}
