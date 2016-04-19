package wonder.core;

import wonder.core.Events.CardPlayed;
import wonder.core.Events.GameCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
                .filter(event -> event.game().id() == gameId)
                .findFirst();
        return gameCreated.isPresent() ? gameCreated.get().game() : null;
    }


    public Stream<Event> byGame(final Game game) {
        return log().stream().filter(event -> game == event.game());
    }

    public Stream<CardPlayed> byCardByPlayer(final Player player, final Game game) {
        return byGame(game)
                .filter(event -> event instanceof CardPlayed)
                .map(event -> (CardPlayed) event)
                .filter(event -> event.player() == player);
    }
}
