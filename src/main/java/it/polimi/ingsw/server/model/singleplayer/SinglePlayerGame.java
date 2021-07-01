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

/**
 * Extension of multiplayer game, applied when player is only one.
 */
public class SinglePlayerGame extends Game {
    private ArrayList<ActionToken> actions;
    private int currentActionIndex;
    private boolean isFirstTurn;
    private int blackCrossPosition = 0;

    /**
     * Setup of game structures and action tokens
     * @param players Players that are playing in this game
     * @throws Exception
     */
    @Override
    public void setup(ArrayList<Player> players) throws Exception {
        super.setup(players);
        this.setupActionTokens();
        this.isFirstTurn = true;
    }

    public int getBlackCrossPosition(){
        return blackCrossPosition;
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

    /**
     *
     * @return Copy of this game
     * @throws Exception
     */
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

    /**
     * Apply next turn rule and activate action token
     * @throws Exception
     */
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


    /**
     *
     * @return Check if Lorenzo has ended faith path
     */
    private boolean lorenzoEndedFaithPath(){
        return this.blackCrossPosition >= getCurrentPlayer().getPlayerBoard().getFaithPath().getCells().length;
    }

    /**
     *
     * @return Check if a card color is missing from card market
     */
    private boolean developmentCardUnavailable(){
        return this.getCardMarket().hasEmptyColor();
    }

    /**
     * Increase position of black cross
     * @param quantity Quantity of faith points to add to Lorenzo
     */
    public void advanceBlackCross(int quantity){
        for(int i = 0; i<quantity; i++){
            this.blackCrossPosition++;
            this.getCurrentPlayer().checkPopeFavour(blackCrossPosition);
        }
        notify(new BlackCrossMessage(this.blackCrossPosition, getCurrentPlayer().getPlayerBoard().getFaithPath().getAcquiredPopeFavours()));
    }

    /**
     * Shuffles action tokens
     */
    public void shuffleTokens(){
        Collections.shuffle(this.actions);
        currentActionIndex = 0;
    }
}
