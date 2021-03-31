package it.polimi.ingsw.model;

public class DepotEffect extends Effect{

    private final Resource resource;

    public DepotEffect(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    @Override
    public void apply() {
        super.apply();
    }
}
