package it.polimi.ingsw.server.model.card.requirement;

import it.polimi.ingsw.server.model.card.effect.DiscountEffect;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ConsumeTarget;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Extension of Requirement to specify the requirement of specific quantities of determined resources.
 */
public class ResourceRequirement extends Requirement implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ResourceStock> resourceStocks;
    private static final String RESOURCE_REQUIREMENT_FORMAT = "[%s]";

    /**
     * Create a ResourceRequirement object with a given list of resource piles.
     * @param resourceStocks the list of wanted resource piles (type and quantities)
     */
    public ResourceRequirement(List<ResourceStock> resourceStocks) {
        this.resourceStocks = resourceStocks;
    }

    /**
     * Create a default ResourceRequirement object.
     */
    public ResourceRequirement(){}

    /**
     *
     * @return return the list of resource piles.
     */
    public List<ResourceStock> getResourceStocks() {
        return resourceStocks;
    }

    /**
     * Test if player fulfill requirement to activate a card.
     * @param player the player to check
     * @return true if requirements are fulfilled; else false
     */
    @Override
    public boolean isFulfilled(Player player) {
         return this.resourceStocks
                 .stream()
                 .allMatch(resourcePile ->
                         player.countByResource(resourcePile.getResourceType()) >= resourcePile.getQuantity());
    }

    // TODO il creatore di questa funzione verifichi la correttezza del commento
    public boolean matchRequirement(ConsumeTarget toConsume){
        return !this.resourceStocks.stream().map(
                stock-> {
                    int quantityConsumable = toConsume.countResourceType(stock.getResourceType());
                    return quantityConsumable == stock.getQuantity();
                }
        ).collect(Collectors.toList()).contains(false);
    }

    @Override
    public String toString(){
        String resourceString = ResourceStock.stocksToString(new ArrayList<>(resourceStocks));
        return String.format(RESOURCE_REQUIREMENT_FORMAT, resourceString);
    }

    /**
     * Merge resource requirements together.
     * @param requirements list of requirements to merge
     * @return the requirement obtained after the merging process
     * @throws Exception
     */
    // TODO il creatore di questa funzione verifichi la correttezza del commento
    static public ResourceRequirement merge(ArrayList<ResourceStock> requirements) throws Exception {
        ArrayList<ResourceStock> stocks = new ArrayList<>();
        for(ResourceStock stock : requirements){
            Optional<ResourceStock> resourceTypeStock = stocks.stream()
                    .filter(s->s.getResourceType().equals(stock.getResourceType())).findFirst();
            if(resourceTypeStock.isPresent()){
                resourceTypeStock.get().incrementResource(stock.getResourceType(), stock.getQuantity());
            } else {
                stocks.add(stock);
            }
        }
        return new ResourceRequirement(stocks);
    }

    /**
     * Apply discounts when trying to buy from the market
     * @param discounts list of discount effects
     */
    public void applyDiscounts(ArrayList<DiscountEffect> discounts) throws Exception {
        for(DiscountEffect discount : discounts){
            for(ResourceStock stock : resourceStocks){
                if(discount.getResourceType().equals(stock.getResourceType())){
                    if(stock.getQuantity() != 0){
                        stock.decrementResource(discount.getResourceType());
                    }
                }
            }
        }
    }
}
