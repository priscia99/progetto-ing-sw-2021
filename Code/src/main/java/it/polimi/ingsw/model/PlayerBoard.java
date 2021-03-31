package it.polimi.ingsw.model;

public class PlayerBoard {
    private FaithPath faithPath;
    private ProductionEffect basicProduction;
    private DevelopmentSlot developmentSlot;
    private Warehouse warehouse;
    private StrongBox strongBox;

    public PlayerBoard(){
        faithPath = new FaithPath();
        basicProduction = new ProductionEffect();
        developmentSlot = new DevelopmentSlot();
        warehouse = new Warehouse();
        strongBox = new StrongBox();
    }

    public void addToDepot(int depot, Resource resource){
        // ...
    }

    public void removeFromDepot(int depot){
        // ...
    }

}
