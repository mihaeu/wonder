package wonder.web;

import wonder.core.Cards.LumberYard;

import static spark.Spark.get;

public class Web {
    public static void main(String[] args) {
        get("/hello", (req, res) -> new LumberYard(3));
    }
}