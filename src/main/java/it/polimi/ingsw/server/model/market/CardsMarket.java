package it.polimi.ingsw.server.model.market;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.data.DevCardMarketBuilder;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_server.CardsMarketMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.game.Player;

import java.util.Arrays;
import java.util.Stack;

public class CardsMarket extends Observable<Message<ClientController>> {

    private final Stack<DevelopmentCard>[][] decks;

    static public CardsMarket getStartingMarket() throws Exception {
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
                        throw new Exception("column must be in [0;3]");
                }
                decks[column][lv-1] = DevCardMarketBuilder.getStackByLevelColor(lv, color);
          }
         }
        return new CardsMarket(decks);
    }

    public CardsMarket(Stack<DevelopmentCard>[][] decks) {
        this.decks = decks;
    }

    public CardsMarket getCopy(){
        CardsMarket copy = new CardsMarket(this.decks);
        return copy;
    }

    public Stack<DevelopmentCard>[][] getDecks() {
        return decks;
    }

    public DevelopmentCard getCard(int row, int column){
        return this.decks[row][column].peek();
    }

    public DevelopmentCard getCardById(String id) throws Exception {
        for(int i = 0; i<4; i++){
            for(int j = 0; j<3; j++){
                if(this.decks[i][j].size()>0){
                    if(this.decks[i][j].peek().getId().equals(id)) return this.decks[i][j].peek();
                }
            }
        }
        throw new Exception("The requested card is not available in the market");
    }

    public DevelopmentCard popCardById(String id){
        for(int i = 0; i<4; i++){
            for(int j = 0; j<3; j++){
                if(this.decks[i][j].peek().getId().equals(id)) return this.decks[i][j].pop();
            }
        }
        return null;
    }

    public DevelopmentCard sell(String id, Player player) throws Exception{
        DevelopmentCard card = getCardById(id);
        if (card.getRequirement().isFulfilled(player)) {
            card = popCardById(id);
            notify(new CardsMarketMessage(this));
            return card;
        }
        else {
            throw new Exception("player doesn't fulfill requirements");
        }
    }


    public void removeByColor(Color toRemove, int quantity){
        for(int i = 0; i<4; i++){
            for(int j = 0; j<3; j++){
                if(this.decks[i][j].size()>0){
                    if(this.decks[i][j].peek().getColor().equals(toRemove)){
                        if(this.decks[i][j].size()>=quantity){
                            for(int k = 0; k<quantity; k++) this.decks[i][j].pop();
                            notify(new CardsMarketMessage(this));
                            return;
                        } else {
                            quantity -= this.decks[i][j].size();
                            for(int k = 0; k<this.decks[i][j].size(); k++) this.decks[i][j].pop();
                        }
                    }
                }
            }
        }
        notify(new CardsMarketMessage(this));
    }

    public boolean hasEmptyColor(){
        Stack<DevelopmentCard>[][] stacks = this.getDecks();
        for(Stack<DevelopmentCard>[] color : stacks){
            if(Arrays.stream(color).mapToInt(Stack::size).sum() == 0) return true;
        }
        return false;
    }

}
