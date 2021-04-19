package it.polimi.ingsw.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.color.ColorPile;
import it.polimi.ingsw.model.card.effect.*;
import it.polimi.ingsw.model.card.requirement.ColorRequirement;
import it.polimi.ingsw.model.card.requirement.LevelRequirement;
import it.polimi.ingsw.model.card.requirement.Requirement;
import it.polimi.ingsw.model.card.requirement.ResourceRequirement;
import it.polimi.ingsw.model.player_board.storage.Depot;
import it.polimi.ingsw.model.resource.ResourcePile;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class LeaderCardsBuilder{
    static private ArrayList<LeaderCard> leaderCardList;
    static final private String leaderCardsPath = "assets/leader_cards.json";

    static private void initBuilder() throws IllegalArgumentException{
        try {
            leaderCardList = new ArrayList<>();
            CustomLogger.getLogger().info(String.format(("Starting leader cards parsing from file %s"), leaderCardsPath));
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(leaderCardsPath));
            JsonParser parser = new JsonParser();
            JsonArray cardsArray = parser.parse(reader).getAsJsonArray();

            for(int cardIterator=0; cardIterator<cardsArray.size(); cardIterator++){
                JsonObject tempObject = cardsArray.get(cardIterator).getAsJsonObject();
                JsonObject reqObject = tempObject.get("requirement").getAsJsonObject();
                JsonObject effObject = tempObject.get("effect").getAsJsonObject();
                String reqType = reqObject.get("requirement_type").getAsString();
                String effType = effObject.get("effect_type").getAsString();
                Requirement requirement;
                Effect effect;

                switch (reqType) {
                    case "LEVEL":
                        requirement = new LevelRequirement(Color.valueOf(reqObject.get("color").getAsString()));
                        break;
                    case "COLOR": {
                        JsonArray contentArray = reqObject.get("content").getAsJsonArray();
                        ArrayList<ColorPile> colorPiles = new ArrayList<>();

                        for (int contentIterator = 0; contentIterator < contentArray.size(); contentIterator++) {
                            JsonObject contentObject = contentArray.get(contentIterator).getAsJsonObject();
                            colorPiles.add(new ColorPile(
                                    Color.valueOf(contentObject.get("color").getAsString()),
                                    contentObject.get("quantity").getAsInt()
                            ));
                        }
                        requirement = new ColorRequirement(colorPiles);
                        break;
                    }
                    case "RESOURCE": {
                        JsonArray contentArray = reqObject.get("content").getAsJsonArray();
                        ArrayList<ResourcePile> resourcePiles = new ArrayList<>();

                        for (int contentIterator = 0; contentIterator < contentArray.size(); contentIterator++) {
                            JsonObject contentObject = contentArray.get(contentIterator).getAsJsonObject();
                            resourcePiles.add(new ResourcePile(
                                    contentObject.get("quantity").getAsInt(),
                                    ResourceType.valueOf(contentObject.get("resource_type").getAsString())
                            ));
                        }
                        requirement = new ResourceRequirement(resourcePiles);
                        break;
                    }
                    default:
                        CustomLogger.getLogger().severe("Parser format error");
                        throw new IllegalArgumentException("Invalid requirement type on card");
                }

                switch (effType) {
                    case "PRODUCTION":
                        JsonArray inputPiles = effObject.get("input_piles").getAsJsonArray();
                        JsonArray outputPiles = effObject.get("output_piles").getAsJsonArray();
                        ArrayList<ResourcePile> input = new ArrayList<>();
                        ArrayList<ResourcePile> output = new ArrayList<>();

                        for (int pileIterator = 0; pileIterator < inputPiles.size(); pileIterator++) {
                            JsonObject pileObject = inputPiles.get(pileIterator).getAsJsonObject();
                            input.add(new ResourcePile(
                                    pileObject.get("resource_quantity").getAsInt(),
                                    ResourceType.valueOf(pileObject.get("resource_type").getAsString())
                            ));
                        }

                        for (int pileIterator = 0; pileIterator < outputPiles.size(); pileIterator++) {
                            JsonObject pileObject = outputPiles.get(pileIterator).getAsJsonObject();
                            output.add(new ResourcePile(
                                    pileObject.get("resource_quantity").getAsInt(),
                                    ResourceType.valueOf(pileObject.get("resource_type").getAsString())
                            ));
                        }
                        effect = new ProductionEffect(input, output);
                        break;
                    case "CHANGE":
                        effect = new ChangeEffect(ResourceType.valueOf(effObject.get("marble_value").getAsString()));
                        break;
                    case "DEPOT":
                        effect = new DepotEffect(
                                ResourceType.valueOf(effObject.get("depot_type").getAsString()),
                                new Depot(2));
                        break;
                    case "DISCOUNT":
                        effect = new DiscountEffect(ResourceType.valueOf(effObject.get("discount_type").getAsString()));
                        break;
                    default:
                        CustomLogger.getLogger().severe("Parser format error");
                        throw new IllegalArgumentException("Invalid effect type on card");
                }

                leaderCardList.add(new LeaderCard(
                        false,
                        tempObject.get("victory_points").getAsInt(),
                        effect,
                        requirement
                ));

            }
        }catch (FileNotFoundException e){
            CustomLogger.getLogger().severe(String.format(("Parser error: %s file not found"), leaderCardsPath));
        }catch (Exception e){
            CustomLogger.getLogger().severe("General parsing error");
            CustomLogger.getLogger().severe(e.getMessage());
        }

    }

    static public ArrayList<LeaderCard> getDeck(){
        if(Objects.isNull(leaderCardList)) initBuilder();
        return new ArrayList<>(leaderCardList);
    }

}
