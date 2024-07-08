package flashtanki.server.battles;

import java.util.HashMap;
import java.util.Map;

public class BattleProperties {
    private Map<BattleProperty<?>, Object> properties;

    public BattleProperties(Map<BattleProperty<?>, Object> properties) {
        this.properties = properties;
    }

    public <T> T get(BattleProperty<T> property) {
        return property.type.cast(properties.getOrDefault(property, property.defaultValue));
    }

    public <T> T getOrNull(BattleProperty<T> property) {
        Object value = properties.getOrDefault(property, null);
        return value != null ? property.type.cast(value) : null;
    }

    public <T> void set(BattleProperty<T> property, T value) {
        properties.put(property, value);
    }

    public <T> void setValue(BattleProperty<T> property, Object value) {
        if (!value.getClass().equals(property.type)) {
            throw new IllegalArgumentException("Value is not " + property.type);
        }
        properties.put(property, value);
    }
}