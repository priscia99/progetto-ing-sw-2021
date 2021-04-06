package it.polimi.ingsw.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.*;
import it.polimi.ingsw.model.card.requirement.ResourceRequirement;
import it.polimi.ingsw.model.resource.ResourcePile;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

public class DevelopmentCardsBuilder {
    static private ArrayList<DevelopmentCard> developmentCardList;
    static final private String devCardsPath = "assets/dev_cards.json";

    static private void initBuilder() throws IllegalArgumentException{
        try {
            developmentCardList = new ArrayList<>();
            CustomLogger.getLogger().info(String.format(("Starting development cards parsing from file %s"), devCardsPath));
            JsonReader reader = new JsonReader(new FileReader(devCardsPath));
            JsonParser parser = new JsonParser();
            JsonArray cardsArray = parser.parse(reader).getAsJsonArray();

            for(int cardIterator=0; cardIterator<cardsArray.size(); cardIterator++){
                JsonObject cardObject = cardsArray.get(cardIterator).getAsJsonObject();
                JsonArray reqArray = cardObject.get("requirements").getAsJsonArray();
                JsonArray inputArray = cardObject.get("input_pile").getAsJsonArray();
                JsonArray outputArray = cardObject.get("output_pile").getAsJsonArray();

                // Creating requirements
                ArrayList<ResourcePile> requiredPiles = new ArrayList<>();
                for(int pileIterator=0; pileIterator<reqArray.size(); pileIterator++){
                    JsonObject pileObject = reqArray.get(pileIterator).getAsJsonObject();
                    requiredPiles.add(new ResourcePile(
                            pileObject.get("quantity").getAsInt(),
                            ResourceType.valueOf(pileObject.get("resource_type").getAsString())
                    ));
                }

                //Creating input pile
                ArrayList<ResourcePile> inputPile = new ArrayList<>();
                for(int pileIterator=0; pileIterator<inputArray.size(); pileIterator++){
                    JsonObject pileObject = inputArray.get(pileIterator).getAsJsonObject();
                    inputPile.add(new ResourcePile(
                            pileObject.get("quantity").getAsInt(),
                            ResourceType.valueOf(pileObject.get("resource_type").getAsString())
                    ));
                }

                //Creating output pile
                ArrayList<ResourcePile> outputPile = new ArrayList<>();
                for(int pileIterator=0; pileIterator<outputArray.size(); pileIterator++){
                    JsonObject pileObject = outputArray.get(pileIterator).getAsJsonObject();
                    outputPile.add(new ResourcePile(
                            pileObject.get("quantity").getAsInt(),
                            ResourceType.valueOf(pileObject.get("resource_type").getAsString())
                    ));
                }

                developmentCardList.add(new DevelopmentCard(
                        false,
                        cardObject.get("victory_points").getAsInt(),
                        cardObject.get("level").getAsInt(),
                        new ProductionEffect(inputPile, outputPile),
                        Color.valueOf(cardObject.get("color").getAsString()),
                        new ResourceRequirement(requiredPiles)
                ));

            }
        }catch (FileNotFoundException e){
            CustomLogger.getLogger().severe(String.format(("Parser error: %s file not found"), devCardsPath));
        }catch (Exception e){
            CustomLogger.getLogger().severe("General parsing error");
            CustomLogger.getLogger().severe(e.getMessage());
        }

    }

    static public ArrayList<DevelopmentCard> getDeck(){
        if(Objects.isNull(developmentCardList)) initBuilder();

        return new ArrayList<>(developmentCardList);
    }
}
