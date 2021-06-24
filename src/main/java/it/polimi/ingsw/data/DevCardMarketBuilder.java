package it.polimi.ingsw.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.color.*;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.server.model.card.effect.*;
import it.polimi.ingsw.server.model.card.requirement.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Stack;

public class DevCardMarketBuilder {
    private static final int NUM_COLORS = 4;
    private static final int NUM_LEVELS = 3;
    private static Stack<DevelopmentCard>[][] marketDecks;
    private static final String file_path = "assets/dev_cards.json";

    private static void initBuilder(){
        try {
            marketDecks = new Stack[NUM_LEVELS][NUM_COLORS];
            JsonReader reader = new JsonReader(new FileReader(file_path));
            JsonParser parser = new JsonParser();
            JsonArray cardsArray = parser.parse(reader).getAsJsonArray();

            for(int cardIterator=0; cardIterator< cardsArray.size(); cardIterator++) {
                JsonObject card = cardsArray.get(cardIterator).getAsJsonObject();
                Color cardColor = Color.valueOf(card.get("color").getAsString());
                int cardPoints = card.get("victory_points").getAsInt();
                int cardLevel = card.get("level").getAsInt();
                JsonArray reqArray = card.get("requirements").getAsJsonArray();
                JsonArray inputArray = card.get("input_pile").getAsJsonArray();
                JsonArray outputArray = card.get("output_pile").getAsJsonArray();
                // Creating requirements
                ArrayList<ResourceStock> requiredPiles = new ArrayList<>();
                for (int pileIterator = 0; pileIterator < reqArray.size(); pileIterator++) {
                    JsonObject pileObject = reqArray.get(pileIterator).getAsJsonObject();
                    requiredPiles.add(new ResourceStock(
                            ResourceType.valueOf(pileObject.get("resource_type").getAsString()),
                            pileObject.get("quantity").getAsInt()
                            ));
                }
                //Creating input pile
                ArrayList<ResourceStock> inputPile = new ArrayList<>();
                for (int pileIterator = 0; pileIterator < inputArray.size(); pileIterator++) {
                    JsonObject pileObject = inputArray.get(pileIterator).getAsJsonObject();
                    inputPile.add(new ResourceStock(
                            ResourceType.valueOf(pileObject.get("resource_type").getAsString()),
                            pileObject.get("quantity").getAsInt()
                            ));
                }
                //Creating output pile
                ArrayList<ResourceStock> outputPile = new ArrayList<>();
                for (int pileIterator = 0; pileIterator < outputArray.size(); pileIterator++) {
                    JsonObject pileObject = outputArray.get(pileIterator).getAsJsonObject();
                    outputPile.add(new ResourceStock(
                            ResourceType.valueOf(pileObject.get("resource_type").getAsString()),
                            pileObject.get("quantity").getAsInt()
                            ));
                }

                if(Objects.isNull(marketDecks[cardLevel-1][cardColor.ordinal()])){
                    marketDecks[cardLevel-1][cardColor.ordinal()] = new Stack<>();
                }

                marketDecks[cardLevel-1][cardColor.ordinal()].add(new DevelopmentCard(
                        cardPoints,
                        cardLevel,
                        new ProductionEffect(inputPile, outputPile),
                        cardColor,
                        new ResourceRequirement(requiredPiles),
                        "D" + Integer.toString(cardIterator+1)
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Stack<DevelopmentCard> getStackByLevelColor(int level, Color color){
        if(Objects.isNull(marketDecks)) initBuilder();
        Stack<DevelopmentCard> clonedStack = (Stack<DevelopmentCard>) marketDecks[level-1][color.ordinal()].clone();
        Collections.shuffle(clonedStack);
        return clonedStack;
    }
}
