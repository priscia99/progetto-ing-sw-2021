package it.polimi.ingsw.client.view.ui.gui.utils;

import it.polimi.ingsw.client.model.ClientAsset;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.resource.ResourceType;

public class AssetsHelper {

    private static final String COIN = "/img/ico/coin.png";
    private static final String SERVANT = "/img/ico/servant.png";
    private static final String SHIELD = "/img/ico/shield.png";
    private static final String STONE = "/img/ico/stone.png";
    private static final String GENERIC = "/img/ico/generic.png";
    private static final String BLANK = "/img/ico/blank.png";
    private static final String FAITH = "/img/ico/faith.png";
    private static final String BLACK_MARBLE = "/img/ico/black_marble.png";
    private static final String LEADER_BACK = "/img/cards/back/lead_back.png";
    private static final String LEADER_FRONT = "/img/cards/front/leader-card-";
    private static final String DEVELOPMENT_FRONT = "/img/cards/front/development-card-";
    private static final String DEVELOPMENT_BACK = "/img/cards/back/dev_back_";
    private static final String BACKGROUND_MARKET = "img/background-markets.png";
    private static final String CROSS = "/img/croce.png";
    private static final String LORENZO_ICON = "/img/lorenzo.png";
    private static final String CROSSES = "/img/crosses.png";
    private static final String BLUE_MARBLE = "/img/ico/blue_marble.png";          // shield
    private static final String GRAY_MARBLE = "/img/ico/gray_marble.png";          // stone
    private static final String PURPLE_MARBLE = "/img/ico/purple_marble.png";      // servant
    private static final String RED_MARBLE = "/img/ico/red_marble.png";            // faith
    private static final String WHITE_MARBLE = "/img/ico/white_marble.png";        // white
    private static final String YELLOW_MARBLE = "/img/ico/yellow_marble.png";      // coin

    /**
     * Retrieves the proper icon asset path based on the resource path
     * @param resource resource type
     * @return icon asset path
     */
    public static String getResourceIconPath(ResourceType resource){
        return switch (resource){
            case COIN -> COIN;
            case SERVANT -> SERVANT;
            case SHIELD -> SHIELD;
            case STONE -> STONE;
            case GENERIC -> GENERIC;
            case BLANK -> BLANK;
            case FAITH -> FAITH;
        };
    }

    public static String getBlackMarblePath(){
        return BLACK_MARBLE;
    }

    public static String getBackLeaderPath(){ return LEADER_BACK;}

    public static String getLeaderFrontPath(ClientAsset asset){
        return LEADER_FRONT + asset.getAssetLink() + ".png";
    }

    public static String getLeaderFrontPath(String id){
        return LEADER_FRONT + id + ".png";
    }

    public static String getDevelopmentFrontPath(ClientAsset asset){
        return  DEVELOPMENT_FRONT+ asset.getAssetLink() + ".png";
    }

    public static String getBackDevelopmentPath(String id){
        return  DEVELOPMENT_BACK + id + ".png";
    }

    public static String getBackMarketPath(){
        return BACKGROUND_MARKET;
    }

    public static String getCrossPath(){return CROSS;}

    public static String getLorenzoPath(){return LORENZO_ICON;}

    public static String getCrossesPath(){ return CROSSES;}

    public static String getPopeFavourPath(int index, boolean active){
        String status = (active) ? "active" : "inactive";
        return "img/favor-"+ index + "-"+ status + ".png";
    }

    public static String getMarblePath(Marble marble){
         return switch (marble.getResourceType()){
            case SHIELD -> BLUE_MARBLE;
            case COIN -> YELLOW_MARBLE;
            case STONE -> GRAY_MARBLE;
            case SERVANT -> PURPLE_MARBLE;
            case FAITH -> RED_MARBLE;
            case GENERIC -> BLACK_MARBLE;
            case BLANK -> WHITE_MARBLE;
        };
    }
}
