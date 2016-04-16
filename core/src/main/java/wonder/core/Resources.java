package wonder.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static wonder.core.Resources.Type.*;

public class Resources {

    private final List<Type> resources;

    public Resources(Type ...resources) {
        this.resources = Arrays.asList(resources);
    }

    public Resources(Type resource) {
        this.resources = Collections.singletonList(resource);
    }

    public List<Type> resources() {
        return resources;
    }

    public static boolean isNormal(Type type) {
        return !isOptional(type);
    }

    public static boolean isOptional(Type type) {
        return type == OptionalClay
                || type == OptionalStone
                || type == OptionalWood
                || type == OptionalOre
                || type == OptionalPapyrus
                || type == OptionalGlass
                || type == OptionalTextile;
    }

    public static Type toNormal(Type type) {
        if (type == OptionalClay) return Clay;
        if (type == OptionalStone) return Stone;
        if (type == OptionalWood) return Wood;
        if (type == OptionalOre) return Ore;
        if (type == OptionalPapyrus) return Papyrus;
        if (type == OptionalGlass) return Glass;
        return Textile;
    }

    public enum Type {
        Stone,
        Clay,
        Wood,
        Ore,
        Glass,
        Textile,
        Papyrus,
        OptionalStone,
        OptionalClay,
        OptionalWood,
        OptionalOre,
        OptionalGlass,
        OptionalTextile,
        OptionalPapyrus,
    }
}
