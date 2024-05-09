package heart.events;

public class Cancellable {

    private boolean cancelled;

    public Cancellable() {
        this.cancelled = false;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}