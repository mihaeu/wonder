package wonder.web;

import spark.ResponseTransformer;
import wonder.core.*;
import wonder.core.Cards.ClayPit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Web {
    public static void main(String[] args) {
        GameMaster master = new GameMaster(new GameSetup());
        Map<Integer, Player> players = new HashMap<>();
        players.put(1, new Player(1, "Player 1", new Wonder("Babylon")));
        players.put(2, new Player(2, "Player 2", new Wonder("Olympia")));
        players.put(3, new Player(3, "Player 3", new Wonder("Rhodos")));
        master.initiateGame(players);

        put("/play/:playerId/:gameId", (req, res) -> {
            final int playerId = Integer.valueOf(req.params(":playerId"));
            final int gameId = Integer.valueOf(req.params(":gameId"));
            master.cardPlayed(new ClayPit(), players.get(playerId), master.games().get(gameId));
            return "OK";
        });

        get("/cards/:playerId/:gameId", (req, res) -> {
            res.type("text/json");
            final int playerId = Integer.valueOf(req.params(":playerId"));
            final int gameId = Integer.valueOf(req.params(":gameId"));
            return master.cardsAvailable(players.get(playerId), master.games().get(gameId));
        }, new JsonTransformer());

        get("/cards/:gameId", (req, res) -> {
            final int gameId = Integer.valueOf(req.params(":gameId"));
            return master.playedCards(master.games().get(gameId));
        });

        get("/coins/:playerId/:gameId", (req, res) -> {
            final int playerId = Integer.valueOf(req.params(":playerId"));
            final int gameId = Integer.valueOf(req.params(":gameId"));
            return master.coinsAvailable(players.get(playerId), master.games().get(gameId));
        }, new JsonTransformer());

        exception(Exception.class, (e, request, response) -> {
            response.status(403);
            response.body(e.toString());
        });
    }

    public static class JsonTransformer implements ResponseTransformer {

        @Override
        public String render(Object model) {
            StringBuilder builder = new StringBuilder();
            builder.append("{\"cards\":[");
            List<Card> cards = ((List<Card>) model);
            for (int i = 0, count = cards.size() - 1; i < count; i += 1) {
                builder.append("{\"name\":\"" + cards.get(i).name() + "\"}");
                if (i + 1 < count) builder.append(",");
            }
            builder.append("]}");
            return builder.toString();
        }

    }
}