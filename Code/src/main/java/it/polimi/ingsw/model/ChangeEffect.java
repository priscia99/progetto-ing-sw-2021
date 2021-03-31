package it.polimi.ingsw.model;

public class ChangeEffect extends Effect{

    private final Resource resource;

    public ChangeEffect(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void apply() {
        super.apply();
    }

    public Resource getResource() {
        return resource;
    }
}
