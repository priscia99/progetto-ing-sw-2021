package it.polimi.ingsw.view.CLI;

import java.util.ArrayList;

public class Menu {

    private String header;
    private ArrayList<String> items;

    public Menu(String header, ArrayList<String> items) {
        this.header = header;
        this.items = items;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        for (String i : this.items) {
            list.append(String.format("> %s\n", i));
        }
        return String.format("%s\n%s", this.header, list);
    }
}
