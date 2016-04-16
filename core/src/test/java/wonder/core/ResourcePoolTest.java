package wonder.core;

import org.junit.Test;
import wonder.core.Resources.Type;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static wonder.core.Resources.Type.*;

public class ResourcePoolTest {
    @Test
    public void addsNormalResources() {
        ResourcePool pool = new ResourcePool();
        pool.add(new Resources(Ore));
        pool.add(new Resources(Ore));
        pool.add(new Resources(Clay, Wood));

        Map<Type, Integer> cost = new HashMap<>();
        cost.put(Ore, 2);
        cost.put(Clay, 1);
        cost.put(Wood, 1);
        assertTrue(pool.contains(cost));

        Map<Type, Integer> tooExpensive = new HashMap<>();
        tooExpensive.put(Ore, 3);
        tooExpensive.put(Clay, 1);
        tooExpensive.put(Wood, 1);
        assertFalse(pool.contains(tooExpensive));
    }

    @Test
    public void addsOptionalResources() {
        ResourcePool pool = new ResourcePool();
        pool.add(new Resources(Ore));
        pool.add(new Resources(Ore));
        pool.add(new Resources(OptionalClay, OptionalWood));

        Map<Type, Integer> cost = new HashMap<>();
        cost.put(Ore, 1);
        cost.put(Wood, 1);
        assertTrue(pool.contains(cost));

        Map<Type, Integer> tooExpensive = new HashMap<>();
        tooExpensive.put(Clay, 1);
        tooExpensive.put(Wood, 1);
        assertFalse(pool.contains(tooExpensive));
    }
}
