package wonder.web;

import spark.ResponseTransformer;
import wonder.core.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Web {
    private static EventLog log = new EventLog();

    public static void main(String[] args) {
        GameMaster master = new GameMaster(new GameSetup(), log);
        Map<Integer, Player> players = new HashMap<>();
        players.put(1, new Player(1, "Player 1", new Wonder("Babylon")));
        players.put(2, new Player(2, "Player 2", new Wonder("Olympia")));
        players.put(3, new Player(3, "Player 3", new Wonder("Rhodos")));
        master.initiateGame(players, 1);

        put("/play/:cardIndex/:playerId/:gameId", (req, res) -> {
            final int playerId = Integer.valueOf(req.params(":playerId"));
            final int gameId = Integer.valueOf(req.params(":gameId"));
            final int cardIndex = Integer.valueOf(req.params(":cardIndex"));
            master.cardPlayed(cardIndex, players.get(playerId), log.gameById(gameId));
            return "OK";
        });

        get("/cards/:playerId/:gameId", (req, res) -> {
            res.type("text/json");
            final int playerId = Integer.valueOf(req.params(":playerId"));
            final int gameId = Integer.valueOf(req.params(":gameId"));
            return master.cardsAvailable(players.get(playerId), log.gameById(gameId));
        }, new JsonTransformer());

        get("/cards/:gameId", (req, res) -> {
            res.type("text/json");
            final int gameId = Integer.valueOf(req.params(":gameId"));
            return master.playedCards(log.gameById(gameId));
        }, new JsonTransformer());

        get("/coins/:playerId/:gameId", (req, res) -> {
            res.type("text/json");
            final int playerId = Integer.valueOf(req.params(":playerId"));
            final int gameId = Integer.valueOf(req.params(":gameId"));
            return "{\"coins\":" + master.coinsAvailable(players.get(playerId), log.gameById(gameId)) + "}";
        });

        exception(Exception.class, (e, request, response) -> {
            response.status(403);
            response.body(e.toString());
        });
    }

    public static class JsonTransformer implements ResponseTransformer {

        @Override
        public String render(Object model) {
            if (model instanceof List) {
                final List cards = (List) model;
                if (!cards.isEmpty() && cards.get(0) instanceof Card) {
                    return render((List<Card>) cards);
                }
            }
            if (model instanceof Map) {
                StringBuilder builder = new StringBuilder();
                final Map<Player, List<Card>> playerListMap = (Map<Player, List<Card>>) model;
                if (playerListMap.isEmpty()) {
                    return "{}";
                }
                builder.append("{\"players\":");
                for (Player player : playerListMap.keySet()) {
                    builder.append("{");
                    builder.append("\"");
                    builder.append(player.id());
                    builder.append("\":");
                    builder.append(render(playerListMap.get(player)));
                    builder.append("},");
                }
                builder.append("}");
                return builder.toString();
            }
            return model.toString();
        }

        public String render(List<Card> cards) {
            StringBuilder builder = new StringBuilder();
            builder.append("{\"cards\":[");
            for (int i = 0, count = cards.size(); i < count; i += 1) {
                builder.append("{\"name\":\"");
                builder.append(cards.get(i).name());
                builder.append("\"}");
                if (i + 1 < count) builder.append(",");
            }
            builder.append("]}");
            return builder.toString();
        }

    }
}