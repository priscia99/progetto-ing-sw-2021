package it.polimi.ingsw.model;

import java.util.Stack;

public class Game {
    private String id;
    private Stack<LeaderCard> leaderCardsDeck;
    private CardMarket cardMarket;

    public void nextTurn(){}
    public void start(){}
    private Player extractFirstPlayer(){}
    void giveResources(){}

    public void setup(){
        setupCardsMarket();
        setupMarbleMarket();
        setupLeaderCards();
        setupPlayerBoards();
    }

    private void setupMarbleMarket(){}
    private void setupCardsMarket(){}
    private void setupLeaderCards(){}
    private void setupPlayerBoards(){}

}