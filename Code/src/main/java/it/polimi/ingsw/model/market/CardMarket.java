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

        // FIXME: wait for builder
        // Stack<DevelopmentCard>[][] decks = new Stack<DevelopmentCard>[4][3]
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
        //              throws new IllegalArgumentException("column must be in [0;3]")
        //              break;
        //      }
        //      decks[column][lv] = builder.getStack(color, lv);
        //  }
        // }
        // return new CardMarket(decks);
        return null;
    }

    public CardMarket(Stack<DevelopmentCard>[][] decks) {
        this.decks = decks;
    }

    public Stack<DevelopmentCard>[][] getDecks() {
        return decks;
    }

    public DevelopmentCard sell(int row, int column) {
        return this.decks[row][column].pop();
    }
}
