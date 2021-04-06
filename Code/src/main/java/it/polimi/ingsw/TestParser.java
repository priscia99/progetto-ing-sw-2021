package it.polimi.ingsw;

import it.polimi.ingsw.data.DevelopmentCardsBuilder;
import it.polimi.ingsw.data.LeaderCardsBuilder;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TestParser
{
    public static void main( String[] args )
    {
       ArrayList<LeaderCard> leaderCardDeck = LeaderCardsBuilder.getDeck();
       for(int i=0; i<leaderCardDeck.size(); i++){
           System.out.println("--- CARD " + (i+1) + " ---\n" + leaderCardDeck.get(i));
       }

        ArrayList<DevelopmentCard> devCardDeck = DevelopmentCardsBuilder.getDeck();
        for(int i=0; i<devCardDeck.size(); i++){
            System.out.println("--- CARD " + (i+1) + " ---\n" + devCardDeck.get(i));
        }
    }
}
