package flashtanki.server.garage;

import java.util.HashMap;
import java.util.Map;

public enum GarageItemType {
    Weapon(1, "weapon"),
    Hull(2, "armor"),
    Paint(3, "paint"),
    Supply(4, "inventory"),
    Subscription(5, "special"),
    Kit(6, "kit"),
    Present(7, "special"),
    GivenPresents(9, "given_presents"),
    Resistance(10, "resistance"),
    Lootboxes(11, "special");

    private final int key;
    private final String categoryKey;

    GarageItemType(int key, String categoryKey) {
        this.key = key;
        this.categoryKey = categoryKey;
    }

    public int getKey() {
        return key;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    private static final Map<Integer, GarageItemType> map = new HashMap<>();
    static {
        for (GarageItemType itemType : GarageItemType.values()) {
            map.put(itemType.key, itemType);
        }
    }

    public static GarageItemType get(int key) {
        return map.get(key);
    }
}
