package wonder.core;

import wonder.core.Resources.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcePool {
    private final List<Map<Type, Integer>> pool;

    public ResourcePool() {
        pool = new ArrayList<>();
        pool.add(new HashMap<Type, Integer>());
    }

    public void add(Resources resources) {
        resources.resources().stream()
            .filter(Resources::isNormal)
            .forEach(type -> {
                pool.stream().forEach(typeIntegerMap -> {
                    final int count = typeIntegerMap.containsKey(type) ? typeIntegerMap.put(type, 0) + 1 : 1;
                    typeIntegerMap.put(type, count);
                });
            });

        List<Map<Type, Integer>> newPool = new ArrayList<>();
        resources.resources().stream()
                .filter(Resources::isOptional)
                .map(Resources::toNormal)
                .forEach(type -> pool.forEach(typeIntegerMap -> {
                    Map<Type, Integer> option = new HashMap<>(typeIntegerMap);
                    final int count = option.containsKey(type) ? option.put(type, 0) + 1 : 1;
                    option.put(type, count);
                    newPool.add(option);
                }));
        if (!newPool.isEmpty()) pool.addAll(newPool);
    }

    public boolean contains(Map<Type, Integer> resources) {
        if (resources.isEmpty()) return true;
        return pool.stream().anyMatch(typeIntegerMap -> resourcesAffordable(resources, typeIntegerMap));
    }

    private boolean resourcesAffordable(Map<Type, Integer> want, Map<Type, Integer> have) {
        return want.keySet().stream().allMatch(type -> {
            return have.containsKey(type) && have.get(type) >= want.get(type);
        });
    }

    public static Map<Type, Integer> cost(Type... types) {
        Map<Type, Integer> cost = new HashMap<>();
        for (Type type : types) {
            if (cost.containsKey(type)) {
                cost.put(type, cost.get(type) + 1);
            } else {
                cost.put(type, 1);
            }
        }
        return cost;
    }
}
