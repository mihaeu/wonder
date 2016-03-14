package wonder.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageTest {
    @Test
    public void returnsMessage() {
        assertEquals("Hello World!", new Message().getMessage());
    }
}
