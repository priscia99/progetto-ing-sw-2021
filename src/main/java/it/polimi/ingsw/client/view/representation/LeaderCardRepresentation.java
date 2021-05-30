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

    // TODO move sub representation in individual classes
    public LeaderCardRepresentation(String assetId, ColorRequirement requirement, DepotEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new ColorRequirementRepresentation("", requirement.getColorPiles());
        this.effectRepresentation = effect.toRepresentation();
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, ColorRequirement requirement, ChangeEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new ColorRequirementRepresentation("", requirement.getColorPiles());
        this.effectRepresentation = effect.toRepresentation();
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, ColorRequirement requirement, DiscountEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new ColorRequirementRepresentation("", requirement.getColorPiles());
        this.effectRepresentation = effect.toRepresentation();
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, ColorRequirement requirement, ProductionEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new ColorRequirementRepresentation("", requirement.getColorPiles());
        this.effectRepresentation = new ProductionEffectRepresentation("", new ArrayList<>(effect.getInStocks()), new ArrayList<>(effect.getOutStocks()));
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, LevelRequirement requirement, DepotEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new LevelRequirementRepresentation("", requirement.getColor());
        this.effectRepresentation = new DepotEffectRepresentation("", effect.getDepot());
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, LevelRequirement requirement, ChangeEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new LevelRequirementRepresentation("", requirement.getColor());
        this.effectRepresentation = new ChangeEffectRepresentation("", effect.getResourceType());
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, LevelRequirement requirement, DiscountEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new LevelRequirementRepresentation("", requirement.getColor());
        this.effectRepresentation = new DiscountEffectRepresentation("", effect.getResourceType());
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, LevelRequirement requirement, ProductionEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new LevelRequirementRepresentation("", requirement.getColor());
        this.effectRepresentation = new ProductionEffectRepresentation("", new ArrayList<>(effect.getInStocks()), new ArrayList<>(effect.getOutStocks()));
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, ResourceRequirement requirement, DepotEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new ResourceRequirementRepresentation("", requirement.getResourceStocks());
        this.effectRepresentation = new DepotEffectRepresentation("", effect.getDepot());
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, ResourceRequirement requirement, ChangeEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new ResourceRequirementRepresentation("", requirement.getResourceStocks());
        this.effectRepresentation = new ChangeEffectRepresentation("", effect.getResourceType());
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, ResourceRequirement requirement, DiscountEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new ResourceRequirementRepresentation("", requirement.getResourceStocks());
        this.effectRepresentation = new DiscountEffectRepresentation("", effect.getResourceType());
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    public LeaderCardRepresentation(String assetId, ResourceRequirement requirement, ProductionEffect effect, int victoryPoints, boolean active) {
        super(assetId, "leader_card");
        this.requirementRepresentation = new ResourceRequirementRepresentation("", requirement.getResourceStocks());
        this.effectRepresentation = new ProductionEffectRepresentation("", new ArrayList<>(effect.getInStocks()), new ArrayList<>(effect.getOutStocks()));
        this.active = active;
        this.victoryPoints = victoryPoints;
    }

    @Override
    public String getCLIRender() {
        return String.format(format, victoryPoints, active?"ACTIVE":"INACTIVE", requirementRepresentation.getCLIRender(), effectRepresentation.getCLIRender());
    }
}
