package it.polimi.ingsw.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.card.requirement.ResourceRequirement;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class DevelopmentCardsBuilder {
    static private ArrayList<DevelopmentCard> developmentCardList;
    static final private String devCardsPath = "dev_cards.json";

    static private void initBuilder() throws IllegalArgumentException{
        try {
            developmentCardList = new ArrayList<>();
            CustomLogger.getLogger().info(String.format(("Starting development cards parsing from file %s"), devCardsPath));
            InputStream stream = LeaderCardsBuilder.class.getClassLoader().getResourceAsStream(devCardsPath);
            InputStreamReader streamReader = new InputStreamReader(stream);
            JsonReader reader = new JsonReader(streamReader);
            JsonParser parser = new JsonParser();
            JsonArray cardsArray = parser.parse(reader).getAsJsonArray();

            for(int cardIterator=0; cardIterator<cardsArray.size(); cardIterator++){
                JsonObject cardObject = cardsArray.get(cardIterator).getAsJsonObject();
                JsonArray reqArray = cardObject.get("requirements").getAsJsonArray();
                JsonArray inputArray = cardObject.get("input_pile").getAsJsonArray();
                JsonArray outputArray = cardObject.get("output_pile").getAsJsonArray();

                // Creating requirements
                ArrayList<ResourceStock> requiredPiles = new ArrayList<>();
                for(int pileIterator=0; pileIterator<reqArray.size(); pileIterator++){
                    JsonObject pileObject = reqArray.get(pileIterator).getAsJsonObject();
                    requiredPiles.add(new ResourceStock(
                            ResourceType.valueOf(pileObject.get("resource_type").getAsString()),
                            pileObject.get("quantity").getAsInt()
                            ));
                }

                //Creating input pile
                ArrayList<ResourceStock> inputPile = new ArrayList<>();
                for(int pileIterator=0; pileIterator<inputArray.size(); pileIterator++){
                    JsonObject pileObject = inputArray.get(pileIterator).getAsJsonObject();
                    inputPile.add(new ResourceStock(
                            ResourceType.valueOf(pileObject.get("resource_type").getAsString()),
                            pileObject.get("quantity").getAsInt()
                            ));
                }

                //Creating output pile
                ArrayList<ResourceStock> outputPile = new ArrayList<>();
                for(int pileIterator=0; pileIterator<outputArray.size(); pileIterator++){
                    JsonObject pileObject = outputArray.get(pileIterator).getAsJsonObject();
                    outputPile.add(new ResourceStock(
                            ResourceType.valueOf(pileObject.get("resource_type").getAsString()),
                            pileObject.get("quantity").getAsInt()
                            ));
                }

                developmentCardList.add(new DevelopmentCard(
                        cardObject.get("victory_points").getAsInt(),
                        cardObject.get("level").getAsInt(),
                        new ProductionEffect(inputPile, outputPile),
                        Color.valueOf(cardObject.get("color").getAsString()),
                        new ResourceRequirement(requiredPiles),
                        "D" + Integer.toString(cardIterator)
                ));

            }
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
