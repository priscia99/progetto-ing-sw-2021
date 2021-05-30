package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.server.model.card.color.ColorPile;
import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.util.ArrayList;

public class ColorRequirementRepresentation extends Representation{

    private final ArrayList<ColorPile> colorPiles;

    public ColorRequirementRepresentation(String assetId, ArrayList<ColorPile> colorPiles) {
        super(assetId, "color_requirement");
        this.colorPiles = colorPiles;
    }

    @Override
    public String getCLIRender() {
        return String.format(format, ColorPile.pilesToString(colorPiles));
    }


}
