package wonder.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public enum Type {
        Stone,
        Clay,
        Wood,
        Ore,
        Glass,
        Textile,
        Papyrus
    }
}
