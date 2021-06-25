package it.polimi.ingsw.server.model.singleplayer;

import it.polimi.ingsw.network.message.from_server.BlackCrossMessage;
import it.polimi.ingsw.network.message.from_server.FaithPathMessage;
import it.polimi.ingsw.network.message.from_server.LorenzoWinsMessage;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.singleplayer.action_token.ActionToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class SinglePlayerGame extends Game {
    ArrayList<ActionToken> actions;
    int currentActionIndex;
    boolean isFirstTurn;
    int blackCrossPosition = 0;

    @Override
    public void setup(ArrayList<Player> players){
        super.setup(players);
        this.setupActionTokens();
        this.isFirstTurn = true;
    }

    private void setupActionTokens(){
        this.actions = ActionToken.getStartingTokens();
        this.shuffleTokens();
    }

    @Override
    public void nextTurn() {
        if(isFirstTurn){
            isFirstTurn = false;
        } else {
            ActionToken action = actions.get(currentActionIndex);
            currentActionIndex++;
            currentActionIndex = currentActionIndex % this.actions.size();
            action.applyOn(this);
            if(lorenzoEndedFaithPath() || developmentCardUnavailable()){
                notify(new LorenzoWinsMessage());
            }
        }
        super.nextTurn();
    }


    private boolean lorenzoEndedFaithPath(){
        return this.blackCrossPosition >= getCurrentPlayer().getPlayerBoard().getFaithPath().getCells().length;
    }

    private boolean developmentCardUnavailable(){
        return this.getCardMarket().hasEmptyColor();
    }

    public void advanceBlackCross(int quantity){
        for(int i = 0; i<quantity; i++){
            this.blackCrossPosition++;
            this.getCurrentPlayer().checkPopeFavour(blackCrossPosition);
        }
        notify(new BlackCrossMessage(this.blackCrossPosition, getCurrentPlayer().getPlayerBoard().getFaithPath().getPopeFavours()));
    }

    public void shuffleTokens(){
        Collections.shuffle(this.actions);
        currentActionIndex = 0;
    }
}
