package it.polimi.ingsw.view.CLI;

public class Command {

    private final String key;
    private final Runnable action;
    private final boolean activeOnly;
    private final String description;

    public Command(String key, Runnable action, boolean activeOnly, String description) {
        this.key = key;
        this.action = action;
        this.activeOnly = activeOnly;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public boolean identifiedBy(String key) {
        return this.key.equals(key);
    }

    public boolean isActiveOnly() {
        return this.activeOnly;
    }

    public void execute() {
        action.run();
    }
}
