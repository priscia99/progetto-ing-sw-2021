package it.polimi.ingsw.server_model.market;

import it.polimi.ingsw.data.DevCardMarketBuilder;
import it.polimi.ingsw.exceptions.NotFulfilledException;
import it.polimi.ingsw.server_model.card.DevelopmentCard;
import it.polimi.ingsw.server_model.card.color.Color;
import it.polimi.ingsw.server_model.game.Player;

import java.util.Stack;

public class CardMarket {

    private final Stack<DevelopmentCard>[][] decks;

    static public CardMarket getStartingMarket(){
        // market scheme:
        // lv3 row [purple, yellow, green, blue]
        // lv2 row [purple, yellow, green, blue]
        // lv1 row [purple, yellow, green, blue]

        Stack[][] decks = new Stack[4][3];
        for (int column = 0; column<4; column++) {
            for (int lv = 1; lv<=3; lv++) {
                Color color;
                switch (column) {
                    case 0:
                        color = Color.PURPLE;
                        break;
                    case 1:
                        color = Color.YELLOW;
                        break;
                    case 2:
                        color = Color.GREEN;
                        break;
                    case 3:
                        color = Color.BLUE;
                        break;
                    default:
                        throw new IllegalArgumentException("column must be in [0;3]");
                }
                decks[column][lv-1] = DevCardMarketBuilder.getStackByLevelColor(lv, color);
          }
         }
        return new CardMarket(decks);
    }

    public CardMarket(Stack<DevelopmentCard>[][] decks) {
        this.decks = decks;
    }

    public Stack<DevelopmentCard>[][] getDecks() {
        return decks;
    }

    public DevelopmentCard sell(int row, int column, Player player) {
        if (this.decks[row][column].peek().getRequirement().isFulfilled(player)) {
            return this.decks[row][column].pop();
        }
        else {
            throw new NotFulfilledException("player doesn't fulfill requirements");
        }
    }
}
