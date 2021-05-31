package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.card.effect.Effect;
import it.polimi.ingsw.server.model.card.requirement.Requirement;

public class DevelopmentCardRepresentation extends Representation{

    private final Color color;
    private final int level;
    private final String id;
    private final Representation requirement;
    private final Representation effect;
    private final int victoryPoints;

    public DevelopmentCardRepresentation(String assetId, String assetType, Color color, int level, String id, Requirement requirement, Effect effect, int victoryPoints) {
        super(assetId, assetType);
        this.color = color;
        this.level = level;
        this.id = id;
        this.requirement = requirement.toRepresentation();
        this.effect = effect.toRepresentation();
        this.victoryPoints = victoryPoints;
    }

    @Override
    public String getCLIRender() {
        return String.format(format, color.toString(), level, victoryPoints, requirement.getCLIRender(), effect.getCLIRender());
    }
}
