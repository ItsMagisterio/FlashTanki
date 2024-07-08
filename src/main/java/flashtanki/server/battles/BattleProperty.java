package flashtanki.server.battles;

import java.util.HashMap;
import java.util.Map;

public class BattleProperty<T> {
    private final String key;
    public final Class<T> type;
    public final T defaultValue;

    private static final Map<String, BattleProperty<?>> properties = new HashMap<>();

    public static final BattleProperty<Boolean> DamageEnabled = new BattleProperty<>("damage_enabled", Boolean.class, true);
    public static final BattleProperty<Boolean> FriendlyFireEnabled = new BattleProperty<>("friendly_fire", Boolean.class, false);
    public static final BattleProperty<Boolean> SelfDamageEnabled = new BattleProperty<>("self_damage_enabled", Boolean.class, true);
    public static final BattleProperty<Boolean> InstantSelfDestruct = new BattleProperty<>("instant_self_destruct", Boolean.class, false);
    public static final BattleProperty<Boolean> SuppliesCooldownEnabled = new BattleProperty<>("supplies_cooldown_enabled", Boolean.class, true);
    public static final BattleProperty<Boolean> DeactivateMinesOnDeath = new BattleProperty<>("deactivate_mines_on_death", Boolean.class, true);
    public static final BattleProperty<Boolean> ParkourMode = new BattleProperty<>("parkour_mode", Boolean.class, false);
    public static final BattleProperty<Boolean> RearmingEnabled = new BattleProperty<>("rearming_enabled", Boolean.class, true);
    public static final BattleProperty<Integer> MaxPeople = new BattleProperty<>("maxPeopleCount", Integer.class, 2);
    public static final BattleProperty<Boolean> privateBattle = new BattleProperty<>("privateBattle", Boolean.class, false);
    public static final BattleProperty<Boolean> ProBattle = new BattleProperty<>("proBattle", Boolean.class, false);
    public static final BattleProperty<Integer> MinRank = new BattleProperty<>("min_rank", Integer.class, 1);
    public static final BattleProperty<Integer> MaxRank = new BattleProperty<>("min_rank", Integer.class, 30);
    public static final BattleProperty<Boolean> WithoutBonuses = new BattleProperty<>("withoutBonuses", Boolean.class, false);
    public static final BattleProperty<Boolean> WithoutCrystals = new BattleProperty<>("withoutCrystals", Boolean.class, false);
    public static final BattleProperty<Boolean> WithoutSupplies = new BattleProperty<>("withoutSupplies", Boolean.class, false);
    public static final BattleProperty<Integer> ScoreLimit = new BattleProperty<>("score_limit", Integer.class, 0);
    public static final BattleProperty<Integer> TimeLimit = new BattleProperty<>("time_limit", Integer.class, 0);
    public static final BattleProperty<Boolean> AutoBalance = new BattleProperty<>("autoBalance", Boolean.class, true);

    private BattleProperty(String key, Class<T> type, T defaultValue) {
        this.key = key;
        this.type = type;
        this.defaultValue = defaultValue;
        properties.put(key, this);
    }

    public static Map<String, BattleProperty<?>> values() {
        return properties;
    }

    public static <T> BattleProperty<T> get(String key) {
        if (properties.containsKey(key)) {
            return (BattleProperty<T>) properties.get(key);
        } else {
            throw new IllegalArgumentException("No such property: " + key);
        }
    }
}