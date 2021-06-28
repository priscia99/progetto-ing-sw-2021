package it.polimi.ingsw.client.view.ui.gui.utils;

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
}
