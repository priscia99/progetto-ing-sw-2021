package it.polimi.ingsw.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.player_board.faith_path.*;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class FaithPathBuilder {
    private static final int PATH_DIM = 25;
    private static final String file_path = "faith_path.json";
    private static Cell[] path = null;

    /**
     * Initialize the faith path builder by reading the data from the proper JSON file and
     * creating all the elements for the faith path
     */
    private static void initBuild(){
        try {
            path = new Cell[PATH_DIM];
            InputStream stream = LeaderCardsBuilder.class.getClassLoader().getResourceAsStream(file_path);
            InputStreamReader streamReader = new InputStreamReader(stream);
            JsonReader reader = new JsonReader(streamReader);
            JsonParser parser = new JsonParser();
            JsonArray cellsArray = parser.parse(reader).getAsJsonArray();

            for(int cellIterator=0; cellIterator<cellsArray.size(); cellIterator++){
                JsonObject cellObject = cellsArray.get(cellIterator).getAsJsonObject();
                String cellType = cellObject.get("cell_type").getAsString();
                JsonObject favorObject;
                switch (cellType){
                    case "EMPTY_CELL":
                        path[cellIterator] = new EmptyCell();
                        break;
                    case "POINTS_CELL":
                        path[cellIterator] = new PointsCell(cellObject.get("points").getAsInt());
                        break;
                    case "POPE_CELL":
                        favorObject = cellObject.get("pope_favor").getAsJsonObject();
                        path[cellIterator] = new PopeCell(new PopeFavor(
                                favorObject.get("first_cell_index").getAsInt(),
                                favorObject.get("points").getAsInt()
                        ), 0);
                        break;
                    case "FINAL_CELL":
                        favorObject = cellObject.get("pope_favor").getAsJsonObject();
                        path[cellIterator] = new PopeCell(new PopeFavor(
                                favorObject.get("first_cell_index").getAsInt(),
                                favorObject.get("points").getAsInt()
                        ), 20);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal argument given in file " + file_path);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Cell[] getPath(){
        if(Objects.isNull(path)) initBuild();
        return path.clone();
    }
}
