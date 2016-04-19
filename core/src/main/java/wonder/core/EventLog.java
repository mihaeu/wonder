package wonder.core;

import java.util.ArrayList;
import java.util.List;

public class EventLog {
    private final List<Event> log;

    public EventLog() {
        log = new ArrayList<>();
    }

    public List<Event> log() {
        return log;
    }
}
