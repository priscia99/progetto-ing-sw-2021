package it.polimi.ingsw.view.CLI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class CLI {


    private static final String file_path = "src/main/java/it/polimi/ingsw/view/CLI/pages.json";

    public static void main(String[] args) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file_path));
            JsonParser parser = new JsonParser();
            JsonArray pageArray = parser.parse(reader).getAsJsonArray();

            for(int pageIterator=0; pageIterator< pageArray.size(); pageIterator++) {
                JsonObject pageJson = pageArray.get(pageIterator).getAsJsonObject();
                Page page = new Page(pageJson);
                System.out.print(page.renderPage());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
