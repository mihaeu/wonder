package wonder.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {
    @Test
    public void getters() {
        Card card = new Card("Lumber Yard", Card.Type.Brown, Card.Age.One, 3);
        assertEquals("Lumber Yard", card.name());
        assertEquals(Card.Type.Brown, card.type());
        assertEquals(Card.Age.One, card.age());
        assertEquals(3, card.minPlayers());
    }
}
