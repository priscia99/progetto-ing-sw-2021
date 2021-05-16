package it.polimi.ingsw.view.CLI;

public class Command {

    private final String key;
    private final Runnable action;

    public Command(String key, Runnable action) {
        this.key = key;
        this.action = action;
    }

    public String getKey() {
        return key;
    }

    public boolean identifiedBy(String key) {
        return this.key.equals(key);
    }

    public void execute() {
        action.run();
    }
}
