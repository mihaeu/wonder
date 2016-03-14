package wonder.web;

import wonder.core.Card;

import static spark.Spark.get;

public class Web {
    public static void main(String[] args) {
        get("/hello", (req, res) -> new Card("Lumber Yard", Card.Type.Brown, Card.Age.One, 3));
    }
}