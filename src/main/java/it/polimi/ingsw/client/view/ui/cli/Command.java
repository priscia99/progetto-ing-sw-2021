package it.polimi.ingsw.client.view.ui.cli;

import java.util.HashMap;

public class Command {

    private final String key;
    private final String description;
    private final HashMap<String, String> parameters;
    private final boolean onlyForCurrent;

    /**
     * Initialize a command by passing all parameters
     * @param key identification key for the command
     * @param description short description of the command
     * @param onyForCurrent set true if the command is available only for the player in turn
     * @param parameters command parameters
     */
    public Command(String key, String description, boolean onyForCurrent, HashMap<String, String> parameters) {
        this.key = key;
        this.description = description;
        this.onlyForCurrent = onyForCurrent;
        this.parameters = parameters;
    }

    /**
     * Retrieves the key of the command
     * @return the key of the command
     */
    public String getKey() {
        return key;
    }

    /**
     * Retrieves the description of the command
     * @return the description of the command
     */
    public String getDescription() {
        return description;
    }

    /**
     * Check if the command key is the same as the provided key as a parameter
     * @param key the key of the searched command
     * @return true if the keys are the same, false otherwise
     */
    public boolean identifiedBy(String key) {
        return this.key.equals(key);
    }

    /**
     *
     * @return true if the command is available only for the player in turn, false otherwise
     */
    public boolean isOnlyForCurrent() {
        return onlyForCurrent;
    }

    /**
     * Retrieves all parameters of the command
     * @return a map in which the key is the parameter letter, the value is a short description for the parameters
     */
    public HashMap<String, String> getParameters() {
        return parameters;
    }
}
