package it.polimi.ingsw.server.model.resource;


import it.polimi.ingsw.network.message.from_server.PlayersOrderMessage;

import java.io.Serializable;
import java.util.*;

/**
 * Used to wrap information about resource choices.
 * Contains data about type of resources selected and their positions.
 */
public class ConsumeTarget implements Serializable {

    private static final long serialVersionUID = 1010L;
    private final HashMap<ResourcePosition, ArrayList<ResourceStock>> toConsume = new HashMap<>();

    public ConsumeTarget(){}

    /**
     *
     * @param type Resource type to count
     * @return Quantity of selected resource type present
     */
    public int countResourceType(ResourceType type){
        return this.toConsume.values().stream().mapToInt(
                stocks -> stocks.stream().mapToInt(
                        stock-> (stock.getResourceType().equals(type)) ?
                                stock.getQuantity() : 0
                ).sum()
        ).sum();
    }

    /**
     *
     * @return Set of position selected
     */
    public Set<ResourcePosition> getPositions(){
        return this.toConsume.keySet();
    }

    /**
     *
     * @return List of resource stock selected
     */
    public ArrayList<ResourceStock> getStocks(){
        ArrayList<ResourceStock> toReturn = new ArrayList<>();
        for(ResourcePosition position : this.getPositions()){
            toReturn.addAll(this.toConsume.get(position));
        }
        return toReturn;
    }

    /**
     *
     * @param position Resource position selected
     * @return List of resource stock to consume from selected position
     * @throws Exception
     */
    public ArrayList<ResourceStock> getToConsumeFromPosition(ResourcePosition position) throws Exception {
        if(position.equals(ResourcePosition.DROPPED)) throw new Exception("Invalid position for selecting consumable resources.");
        return this.toConsume.get(position);
    }

    /**
     *
     * @return List of resource stock to consume from strongbox
     */
    public ArrayList<ResourceStock> getToConsumeFromStrongBox(){
        return toConsume.get(ResourcePosition.STRONG_BOX);
    }

    /**
     *
     * @param depot Depot selected
     * @return List of resource stock to consume from selected depot
     */
    public ResourceStock getToConsumeFromDepot(ResourcePosition depot) {
        if(depot.equals(ResourcePosition.DROPPED) || depot.equals(ResourcePosition.STRONG_BOX)) {
            throw new RuntimeException("Error while checking resources to consume.");
        }
        if(toConsume.get(depot).size()!=1) throw new RuntimeException("Malformed consume target.");
        return toConsume.get(depot).get(0);
    }

    /**
     *
     * @return Quantity of resources selected
     */
    public int countResources() {return this.toConsume.values().stream().mapToInt(
            list -> list.stream().mapToInt(ResourceStock::getQuantity).sum()
    ).sum();}

    /**
     * Add resources to consume from position selected
     * @param position Position from wich consume resources
     * @param stock Resources to consume
     * @throws Exception
     */
    public void put(ResourcePosition position, ResourceStock stock) throws Exception {
        if(position.equals(ResourcePosition.DROPPED)) throw new Exception("Error while selecting consumable resources.");
        if(position.equals(ResourcePosition.STRONG_BOX)){
            if(isPositionPresent(position)){
                toConsume.get(position).add(stock);
            } else {
                toConsume.put(position, new ArrayList<>(Collections.singletonList(stock)));
            }
        }
        else if(isPositionPresent(position)){
            if(!toConsume.get(position).get(0).getResourceType().equals(stock.getResourceType())){
                throw new Exception("Cannot select different resource types from the same depot.");
            }
            toConsume.get(position).get(0).incrementResource(stock.getResourceType(), stock.getQuantity());
        } else {
            toConsume.put(position, new ArrayList<>(Collections.singletonList(stock)));
        }
    }

    /**
     * Merge another consume target into this one
     * @param consumed Resource selection to consume
     * @throws Exception
     */
    public void putAll(ConsumeTarget consumed) throws Exception {
        for (ResourcePosition position : consumed.getPositions()) {
            for(ResourceStock stock : consumed.getToConsumeFromPosition(position)){
                this.put(position, stock);
            }
        }
    }

    /**
     *
     * @param position Resource position selected
     * @return Check if position is present in resource to consume definition
     */
    private boolean isPositionPresent(ResourcePosition position){
        return toConsume.get(position) != null;
    }
}
