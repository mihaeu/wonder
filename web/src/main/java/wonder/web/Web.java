package wonder.web;

import wonder.core.Cards.ClayPit;
import wonder.core.GameMaster;
import wonder.core.GameSetup;
import wonder.core.Player;
import wonder.core.Wonder;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.exception;
import static spark.Spark.put;

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
            return master.log().toString();
        });

        exception(IllegalArgumentException.class, (e, request, response) -> {
            response.status(403);
            response.body(e.getMessage());
        });
    }
}