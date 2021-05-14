package it.polimi.ingsw.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server_model.marble.Marble;
import it.polimi.ingsw.server_model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MarbleMarketBuilder {
    private static final int NUM_ROWS = 3;
    private static final int NUM_COLS = 4;
    private static final String file_path = "assets/marble_market.json";
    private static ArrayList<Marble> parsedMarbles;
    private Marble[][] market;
    private Marble notForSale;

    public MarbleMarketBuilder(){
        buildMarket();
    }

    // Parse marbles from json input file
    private static void parseMarbles(){
        try {
            JsonReader reader = new JsonReader(new FileReader(file_path));
            JsonParser parser = new JsonParser();
            JsonArray marbleArray = parser.parse(reader).getAsJsonArray();
            parsedMarbles = new ArrayList<>();

            for(int arrayIterator=0; arrayIterator<marbleArray.size(); arrayIterator++){
                JsonObject marblePile = marbleArray.get(arrayIterator).getAsJsonObject();
                int marbleQuantity = marblePile.get("quantity").getAsInt();
                for(int i=0; i<marbleQuantity; i++){
                    parsedMarbles.add(new Marble(ResourceType.valueOf(marblePile.get("type").getAsString())));
                }
            }

        }catch(FileNotFoundException e){
            CustomLogger.getLogger().severe(String.format(("Parser error: %s file not found"), file_path));
        }catch(Exception e){
            CustomLogger.getLogger().severe("General parsing error");
            e.printStackTrace();
        }
    }

    // Build a new market using the list of parsed marbles from json file
    private void buildMarket(){
        if(Objects.isNull(parsedMarbles)) parseMarbles();
        Random random = new Random();
        market = new Marble[NUM_ROWS][NUM_COLS];
        if(Objects.isNull(parsedMarbles)) parseMarbles();
        ArrayList<Marble> marbles = new ArrayList<>(parsedMarbles);

        for(int i=0; i<market.length; i++) {
            for (int j = 0; j < market[i].length; j++) {
                market[i][j] = marbles.remove(random.nextInt(marbles.size()));
            }
        }
        if(marbles.size() == 1){
            notForSale = marbles.get(0);
        }
        else{
            CustomLogger.getLogger().severe("Error while parsing marble market.");
        }
    }

    // Get the only marble excluded from the market matrix - not for sale marble
    public Marble getNotForSale(){
        return notForSale;
    }

    // Get market as Marbles matrix
    public Marble[][] getMarket(){
        return market;
    }
}
