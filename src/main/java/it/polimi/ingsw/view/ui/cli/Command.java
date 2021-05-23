package it.polimi.ingsw.view.ui.cli;

public class Command {

    private final String key;
    private final String description;
    private final Runnable action;
    private final boolean active;

    public Command(String key, String description, boolean active, Runnable action) {
        this.key = key;
        this.description = description;
        this.action = action;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void execute() {
        action.run();
    }
}
