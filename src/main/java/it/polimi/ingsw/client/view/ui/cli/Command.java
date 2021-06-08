package it.polimi.ingsw.client.view.ui.cli;

import java.util.HashMap;

public class Command {

    private final String key;
    private final String description;
    private final HashMap<String, String> parameters;
    private final boolean onlyForCurrent;

    public Command(String key, String description, boolean onyForCurrent, HashMap<String, String> parameters) {
        this.key = key;
        this.description = description;
        this.onlyForCurrent = onyForCurrent;
        this.parameters = parameters;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public boolean identifiedBy(String key) {
        return this.key.equals(key);
    }

    public boolean isOnlyForCurrent() {
        return onlyForCurrent;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }
}
