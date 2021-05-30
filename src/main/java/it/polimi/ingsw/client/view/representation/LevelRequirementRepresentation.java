package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.server.model.card.color.Color;

public class LevelRequirementRepresentation extends Representation{

    private final Color color;

    public LevelRequirementRepresentation(String assetId, Color color) {
        super(assetId, "level_requirement");
        this.color = color;
    }

    @Override
    public String getCLIRender() {
        return String.format(format, color.toString());
    }
}
