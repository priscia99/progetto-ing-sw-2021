package it.polimi.ingsw.client.view.representation;

import it.polimi.ingsw.server.model.card.effect.*;
import it.polimi.ingsw.server.model.card.requirement.ColorRequirement;
import it.polimi.ingsw.server.model.card.requirement.LevelRequirement;
import it.polimi.ingsw.server.model.card.requirement.Requirement;
import it.polimi.ingsw.server.model.card.requirement.ResourceRequirement;

import java.util.ArrayList;

public class LeaderCardRepresentation extends Representation{

    private final Representation requirementRepresentation;
    private final Representation effectRepresentation;
    private final boolean active;
    private final int victoryPoints;

    public LeaderCardRepresentation(String assetId, Requirement requirement, Effect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = requirement.toRepresentation();
        this.effectRepresentation = effect.toRepresentation();
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    @Override
    public String getCLIRender() {
        return String.format(format, victoryPoints, active?"ACTIVE":"INACTIVE", requirementRepresentation.getCLIRender(), effectRepresentation.getCLIRender());
    }
}
