package it.polimi.ingsw.client.view.representation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class Representation {

    protected String assetId;
    protected String format;
    protected String assetType = "";

    public Representation(String assetId, String assetType) {
        this.assetId = assetId;
        this.assetType = assetType;
        this.readFormat(this.assetType);
    }

    private void readFormat(String key) {
        try {
            String FILEPATH = "assets/format.json";
            JsonReader reader = new JsonReader(new FileReader(FILEPATH));
            JsonParser parser = new JsonParser();
            format = parser.parse(reader).getAsJsonObject().get(key).getAsString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getAssetLink() {
        return this.assetId;
    }

    public abstract String getCLIRender();
}
