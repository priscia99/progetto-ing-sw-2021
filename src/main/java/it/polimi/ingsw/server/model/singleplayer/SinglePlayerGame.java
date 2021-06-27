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
    private ArrayList<ActionToken> actions;
    private int currentActionIndex;
    private boolean isFirstTurn;
    private int blackCrossPosition = 0;

    @Override
    public void setup(ArrayList<Player> players) throws Exception {
        super.setup(players);
        this.setupActionTokens();
        this.isFirstTurn = true;
    }


    public void setBlackCrossPosition(int position){
        this.blackCrossPosition = position;
    }

    public void setFirstTurn(boolean flag){
        this.isFirstTurn = flag;
    }

    public void setCurrentActionIndex(int index){
        this.currentPlayerIndex = index;
    }

    public void setActionTokens(ArrayList<ActionToken> actions){
        this.actions = actions;
    }

    private void setupActionTokens(){
        this.actions = ActionToken.getStartingTokens();
        this.shuffleTokens();
    }

    @Override
    public SinglePlayerGame getBackup() throws Exception {
        SinglePlayerGame backup = new SinglePlayerGame();
        ArrayList<Player> playersBackup = new ArrayList<>();
        for(Player player : getPlayers()){
            playersBackup.add(player.getCopy());
        }
        backup.setBlackCrossPosition(this.blackCrossPosition);
        backup.setPlayers(playersBackup);
        backup.setActionTokens(this.actions);
        backup.setFirstTurn(this.isFirstTurn);
        backup.setCurrentActionIndex(this.currentActionIndex);
        backup.setCurrentPlayerIndex(this.currentPlayerIndex);
        backup.setLeaderCards(leaderCardsDeck.getCopy());
        backup.setCardsMarket(cardsMarket.getCopy());
        backup.setMarbleMarket(marbleMarket.getCopy());
        backup.setIsLastRound(isLastRound);
        return backup;
    }

    @Override
    public void nextTurn() throws Exception {
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
        notify(new BlackCrossMessage(this.blackCrossPosition, getCurrentPlayer().getPlayerBoard().getFaithPath().getAcquiredPopeFavours()));
    }

    public void shuffleTokens(){
        Collections.shuffle(this.actions);
        currentActionIndex = 0;
    }
}
