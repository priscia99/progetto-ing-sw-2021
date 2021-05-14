package it.polimi.ingsw.view.CLI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CLI {

    // private final String file_path = "src/main/java/it/polimi/ingsw/view/CLI/pages.json";
    private String file_path = null;
    private Map<String, Page> pages = new HashMap<>();

    public CLI(String file_path) {
        this.file_path = file_path;
    }

    public void init() {
        try {
            JsonReader reader = new JsonReader(new FileReader(file_path));
            JsonParser parser = new JsonParser();
            JsonArray pageArray = parser.parse(reader).getAsJsonArray();

            for(int pageIterator=0; pageIterator< pageArray.size(); pageIterator++) {
                JsonObject pageJson = pageArray.get(pageIterator).getAsJsonObject();
                Page page = new Page(pageJson);
                pages.put(page.getTitle(), page);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void renderPage(String title, ArrayList<String> params) {
        pages.get(title).getBody();
    }
}
