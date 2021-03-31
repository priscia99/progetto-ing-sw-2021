package it.polimi.ingsw.model;

public class DiscountEffect  extends Effect{

    private final Resource resource;

    public DiscountEffect(Resource resource) {
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
