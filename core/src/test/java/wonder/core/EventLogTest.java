package wonder.core;

import org.junit.Test;
import wonder.core.Events.AgeCompleted;
import wonder.core.Events.GameCompleted;
import wonder.core.Events.RoundCompleted;

import static org.junit.Assert.assertTrue;

public class EventLogTest {
    @Test
    public void returnsReversedLog() {
        EventLog log = new EventLog();
        log.add(new AgeCompleted(null, null, null));
        log.add(new RoundCompleted(null, null, null));
        log.add(new GameCompleted(null, null, null));
        assertTrue(log.log().stream().findFirst().get() instanceof AgeCompleted);
        assertTrue(log.reverse().findFirst().get() instanceof GameCompleted);
    }
}
