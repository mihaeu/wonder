package wonder.core;

import wonder.core.Events.CardPlayed;
import wonder.core.Events.GameCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EventLog {
    private final List<Event> log;

    public EventLog() {
        log = new ArrayList<>();
    }

    public List<Event> log() {
        return log;
    }

    public boolean add(Event event) {
        return log.add(event);
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

    public Stream<Event> byPlayerByGame(final Player player,final Game game) {
        return byGame(game).filter(event -> player == event.player());
    }

    public Stream<CardPlayed> byCardByPlayer(final Player player, final Game game) {
        return byGame(game)
                .filter(event -> event instanceof CardPlayed)
                .filter(event -> event.player() == player)
                .map(event -> (CardPlayed) event);
    }

    public Stream<Event> byEvent(final Class event, final Player player, final Game game) {
        return byPlayerByGame(player, game)
                .filter(otherEvent -> event == otherEvent.getClass());
    }

    @SuppressWarnings("unchecked")
    public Stream<Event> byEvent(final Class event, final Game game) {
        return byGame(game)
                .filter(otherEvent -> event.isAssignableFrom(otherEvent.getClass()));
    }

    Stream<Event> reverse() {
        List<Event> temp = log.stream().collect(Collectors.toList());
        return IntStream.range(0, temp.size())
                .mapToObj(value -> temp.get(temp.size() - value - 1));
    }
}
