package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientCardsMarket;
import it.polimi.ingsw.client.model.ClientDevelopmentCard;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class DevelopmentCardMarketController {

    private static final String DEV_CARDS_FRONT_PATH = "/img/cards/front/development-card-";
    private static final String DEV_CARDS_BACK_PATH = "/img/cards/back/dev_back_";

    private final GridPane developmentCardsMarketPane;
    private ObservableList<Node> marketCards;
    private ClientCardsMarket cardsMarket;

    public DevelopmentCardMarketController(GridPane developmentCardsMarketPane) {
        this.developmentCardsMarketPane = developmentCardsMarketPane;
    }

    public void refreshCardsMarket(){
        String cardImagePath = null;
        Image cardImage = null;
        BackgroundImage bgImg = null;

        int index = 0;
        for(int i = 0; i<4; i++){
            for(int j = 0; j < 3 ; j ++){
                int deckSize = cardsMarket.getDecks().get(i).get(j).size();
                System.out.println("#card-" + String.valueOf(j+1) + "-" + String.valueOf(i+1));
                Pane tempCardPane = (Pane) developmentCardsMarketPane.lookup("#card-" + String.valueOf(j+1) + "-" + String.valueOf(i+1));
                if(deckSize>0){
                    // there is a development card available for this cell -> display image
                    ClientDevelopmentCard tempCard = cardsMarket.getDecks().get(i).get(j).get(deckSize-1);
                    cardImagePath = DEV_CARDS_FRONT_PATH + tempCard.getAssetLink() + ".png";
                } else {
                    // the current cell is empty -> display back card based on proper level and color of the cell
                    int cardPathNumber = 1 + j*4 + i;
                    cardImagePath = DEV_CARDS_BACK_PATH + cardPathNumber + ".png";
                    System.out.println(cardImagePath);
                }
                tempCardPane.setStyle("-fx-background-image: url(" + cardImagePath + ");");
                index++;
            }
        }
    }

    public void setCardsMarket(ClientCardsMarket market){
        this.cardsMarket = market;
        this.refreshCardsMarket();
    }

    /*
    private static String[] getCardMarketContents(ClientCardsMarket market){
        String[] content = new String[12];
        int index = 0;
        for(int i = 0; i<4; i++){
            for(int j = 0; j < 3 ; j ++){
                int deckSize = market.getDecks().get(i).get(j).size();
                if(deckSize>0){
                    content[index] = RepresentationBuilder.render(market.getDecks().get(i).get(j).get(deckSize-1));
                } else {
                    content[index] = "<< EMPTY >>";
                }
                index++;
            }
        }
        return content;
    }
     */
}
