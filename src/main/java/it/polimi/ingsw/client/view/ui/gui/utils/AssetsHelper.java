package it.polimi.ingsw.client.view.ui.gui.utils;

import it.polimi.ingsw.client.model.ClientAsset;
import it.polimi.ingsw.server.model.resource.ResourceType;

public class AssetsHelper {

    private static final String COIN_ICON = "/img/ico/coin.png";
    private static final String SERVANT_ICON = "/img/ico/servant.png";
    private static final String SHIELD_ICON = "/img/ico/shield.png";
    private static final String STONE_ICON = "/img/ico/stone.png";
    private static final String GENERIC_ICON = "/img/ico/generic.png";
    private static final String BLANK_ICON = "/img/ico/blank.png";
    private static final String FAITH_ICON = "/img/ico/faith.png";
    private static final String BLACK_MARBLE = "/img/ico/black_marble.png";
    private static final String LEADER_BACK = "/img/cards/back/lead_back.png";
    private static final String LEADER_FRONT = "/img/cards/front/leader-card-";
    private static final String DEVELOPMENT_FRONT = "/img/cards/front/development-card-";
    private static final String DEVELOPMENT_BACK = "/img/cards/back/dev_back_";


    public static String getResourceIconPath(ResourceType resource){
        return switch (resource){
            case COIN -> COIN_ICON;
            case SERVANT -> SERVANT_ICON;
            case SHIELD -> SHIELD_ICON;
            case STONE -> STONE_ICON;
            case GENERIC -> GENERIC_ICON;
            case BLANK -> BLANK_ICON;
            case FAITH ->FAITH_ICON;
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
}
