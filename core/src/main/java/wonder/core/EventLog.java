package wonder.core;

import wonder.core.Events.GameCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventLog {
    private final List<Event> log;

    public EventLog() {
        log = new ArrayList<>();
    }

    public List<Event> log() {
        return log;
    }

    public Game gameById(int gameId) {
        Optional<GameCreated> gameCreated = log.stream()
                .filter(event -> event instanceof GameCreated)
                .map(event -> ((GameCreated) event))
                .filter(event -> event.gameId() == gameId)
                .findFirst();
        return gameCreated.isPresent() ? gameCreated.get().game() : null;
    }
}
