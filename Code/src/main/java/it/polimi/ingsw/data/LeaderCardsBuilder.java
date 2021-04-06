package it.polimi.ingsw.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.Effect;
import it.polimi.ingsw.model.card.requirement.LevelRequirement;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class LeaderCardsBuilder{
    static private ArrayList<LeaderCard> leaderCardList;
    static final private String leaderCardsPath = "assets/leader_cards.json";

    static private void initBuilder(){
        // TODO: Code LeaderCardsBuilder init function
        try {
            CustomLogger.getLogger().info(String.format(("Starting leader cards parsing from file %s"), leaderCardsPath));
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(leaderCardsPath));
            JsonParser parser = new JsonParser();
            JsonArray cardsArray = parser.parse(reader).getAsJsonArray();

            for(int cardIterator=0; cardIterator<cardsArray.size(); cardIterator++){
                JsonObject tempObject = cardsArray.get(cardIterator).getAsJsonObject();
                JsonObject reqObject = tempObject.get("requirement").getAsJsonObject();
                String reqType = reqObject.get("requirement_type").getAsString();

                if(reqType.equals("LEVEL")){
                    new LevelRequirement(Color.valueOf(reqObject.get("color").getAsString()));

                }

               //  leaderCardList.add(new LeaderCard())
            }
        }catch (FileNotFoundException e){
            CustomLogger.getLogger().severe(String.format(("Parser error: %s file not found"), leaderCardsPath));
        }catch (Exception e){
            CustomLogger.getLogger().severe("General parsing error");
            CustomLogger.getLogger().severe(e.getMessage());
        }

    }

    static public List<LeaderCard> getDeck(){
        if(Objects.isNull(leaderCardList)) initBuilder();
        return new ArrayList<>(leaderCardList);
    }

}
