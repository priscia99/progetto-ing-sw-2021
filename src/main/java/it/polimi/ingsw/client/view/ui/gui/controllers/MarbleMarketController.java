package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientMarbleMarket;
import it.polimi.ingsw.server.model.marble.Marble;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.Objects;

public class MarbleMarketController {

    private static final String BLUE_MARBLE_PATH = "/img/ico/blue_marble.png";          // shield
    private static final String GRAY_MARBLE_PATH = "/img/ico/gray_marble.png";          // stone
    private static final String PURPLE_MARBLE_PATH = "/img/ico/purple_marble.png";      // servant
    private static final String RED_MARBLE_PATH = "/img/ico/red_marble.png";            // faith
    private static final String WHITE_MARBLE_PATH = "/img/ico/white_marble.png";        // white
    private static final String YELLOW_MARBLE_PATH = "/img/ico/yellow_marble.png";      // coin

    private GridPane marbleMarketPane;
    private Pane notForSaleMarble;
    private TabPane tabPane;
    private ClientMarbleMarket marbleMarket;

    public MarbleMarketController(GridPane marbleMarketPane, Pane notForSaleMarble, TabPane tabPane) {
        this.marbleMarketPane = marbleMarketPane;
        this.notForSaleMarble = notForSaleMarble;
        this.tabPane = tabPane;
    }

    /**
     * Refreshes marble market pane based on current market representation
     *
     */
    public void refreshMarbleMarket(){
        String marbleImagePath = null;
        Image marbleImage = null;
        BackgroundImage bgImg = null;

        // refreshing not for sale marble
        marbleImagePath = this.getAssetLink(marbleMarket.getNotForSale());
        marbleImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(marbleImagePath)));
        bgImg = new BackgroundImage(marbleImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        notForSaleMarble.setBackground(new Background(bgImg));

        // refreshing all other marbles
        Marble[][] marketPositions = marbleMarket.getOnSale();

        // iterate through market rows
        for(int i=0; i<marketPositions.length; i++){
            // iterate through marble cols
            for(int j=0; j<marketPositions[i].length; j++){
                Marble tempMarble = marketPositions[i][j];  // getting temp marble
                Pane tempPane = (Pane) marbleMarketPane.lookup("#mm-" + String.valueOf(i+1) + "-" + String.valueOf(j+1));
                marbleImagePath = this.getAssetLink(tempMarble);
                tempPane.setStyle("-fx-background-image: url(" + marbleImagePath + ");");

            }
        }
        tabPane.requestLayout();
    }

    /**
     * Retrieve the proper asset link based on marble type
     * @param marble Marble to return path
     * @return Image asset link
     */
    private String getAssetLink(Marble marble){
        String path = null;
        switch (marble.getResourceType()){
            case SHIELD:    path = BLUE_MARBLE_PATH;
                            break;
            case COIN:      path = YELLOW_MARBLE_PATH;
                            break;
            case STONE:     path = GRAY_MARBLE_PATH;
                            break;
            case SERVANT:   path = PURPLE_MARBLE_PATH;
                            break;
            case FAITH:     path = RED_MARBLE_PATH;
                            break;
            case BLANK:     path = WHITE_MARBLE_PATH;
                            break;
        }
        return path;
    }

    public void setMarbleMarket(ClientMarbleMarket market){
        this.marbleMarket = market;
        this.refreshMarbleMarket();
    }
}
