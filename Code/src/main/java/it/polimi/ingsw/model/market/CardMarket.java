package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.EffectType;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.resource.ResourcePile;

import java.util.Stack;

public class CardMarket {

    private final Stack<DevelopmentCard>[][] decks;

    static public CardMarket getStartingMarket(){
        // market scheme:
        // lv3 row [purple, yellow, green, blue]
        // lv2 row [purple, yellow, green, blue]
        // lv1 row [purple, yellow, green, blue]

        // CardMarketBuilder builder = new CardMarketBuilder();
        // for (int column = 0; column<4; column++) {
        //  for (int lv = 0; lv<3; lv++) {
        //      switch (column) {
        //          case 0:
        //              color = Color.PURPLE;
        //              break;
        //          case 1:
        //              color = Color.YELLOW;
        //              break;
        //          case 2:
        //              color = Color.GREEN;
        //              break;
        //          case 3:
        //              color = Color.BLUE;
        //              break;
        //          default:
        //              TODO: lanciare eccezione
        //              break;
        //      }
        //      builder.getStack(color, lv);
        //  }
        // }
        return null;    // TODO: rimuovere
    }

    public CardMarket(Stack<DevelopmentCard>[][] decks) {
        this.decks = decks;
    }

    public Stack<DevelopmentCard>[][] getDecks() {
        return decks;
    }

    public void buyCard(DevelopmentCard toRemove) {
        // TODO: fill the function
    }
}
