package it.polimi.ingsw.client.view.ui.gui.utils;

import it.polimi.ingsw.client.model.ClientAsset;
import it.polimi.ingsw.server.model.resource.ResourceType;

public class AssetsHelper {

    public static String getResourceIconPath(ResourceType resource){
        return switch (resource){
            case COIN -> "/img/ico/coin.png";
            case SERVANT -> "/img/ico/servant.png";
            case SHIELD -> "/img/ico/shield.png";
            case STONE -> "/img/ico/stone.png";
            case GENERIC -> "/img/generic.png";
            case BLANK -> "img/ico/zero.png";
            case FAITH -> "img/ico/red_cross.png";
        };
    }

    public static String getBlackMarblePath(){
        return "/img/ico/black_marble.png";
    }

    public static String getBackLeaderPath(){ return "/img/cards/back/lead_back.png";}

    public static String getLeaderFrontPath(ClientAsset asset){
        return "/img/cards/front/leader-card-" + asset.getAssetLink() + ".png";
    }

    public static String getDevelopmentFrontPath(ClientAsset asset){
        return "/img/cards/front/development-card-" + asset.getAssetLink() + ".png";
    }
}
