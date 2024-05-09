package heart.events.impl;

import heart.events.Cancellable;

public class TickEvent {
    Direction direction;

    public TickEvent(Direction direction) {
        this.direction = direction;
    }

}
