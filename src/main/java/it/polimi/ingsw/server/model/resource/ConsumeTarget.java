package it.polimi.ingsw.server.model.resource;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.network.message.from_server.PlayersOrderMessage;

import java.io.Serializable;
import java.util.*;

public class ConsumeTarget implements Serializable {

    private final HashMap<ResourcePosition, ArrayList<ResourceStock>> toConsume = new HashMap<>();

    public ConsumeTarget(){}

    public int countResourceType(ResourceType type){
        return this.toConsume.values().stream().mapToInt(
                stocks -> stocks.stream().mapToInt(
                        stock-> (stock.getResourceType().equals(type)) ?
                                stock.getQuantity() : 0
                ).sum()
        ).sum();
    }

    public Set<ResourcePosition> getPositions(){
        return this.toConsume.keySet();
    }

    public ArrayList<ResourceStock> getStocks(){
        ArrayList<ResourceStock> toReturn = new ArrayList<>();
        for(ResourcePosition position : this.getPositions()){
            toReturn.addAll(this.toConsume.get(position));
        }
        return toReturn;
    }

    public ArrayList<ResourceStock> getToConsumeFromPosition(ResourcePosition position) throws GameException {
        if(position.equals(ResourcePosition.DROPPED)) throw new GameException("Invalid position for selecting consumable resources.");
        return this.toConsume.get(position);
    }

    public ArrayList<ResourceStock> getToConsumeFromStrongBox(){
        return toConsume.get(ResourcePosition.STRONG_BOX);
    }

    public ResourceStock getToConsumeFromDepot(ResourcePosition depot) {
        if(depot.equals(ResourcePosition.DROPPED) || depot.equals(ResourcePosition.STRONG_BOX)) {
            throw new RuntimeException("Error while checking resources to consume.");
        }
        if(toConsume.get(depot).size()!=1) throw new RuntimeException("Malformed consume target.");
        return toConsume.get(depot).get(0);
    }

    public int countStocks(){
        return this.toConsume.values().stream().mapToInt(ArrayList::size).sum();
    }

    public int countResources() {return this.toConsume.values().stream().mapToInt(
            list -> list.stream().mapToInt(ResourceStock::getQuantity).sum()
    ).sum();}

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

    public void putAll(ConsumeTarget consumed) throws Exception {
        for (ResourcePosition position : consumed.getPositions()) {
            for(ResourceStock stock : consumed.getToConsumeFromPosition(position)){
                this.put(position, stock);
            }
        }
    }

    private boolean isPositionPresent(ResourcePosition position){
        return toConsume.get(position) != null;
    }
}
