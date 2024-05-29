package AttackSkill;

import java.util.HashMap;
import java.util.Map;

public enum ATTACK_SKILL {
    NONE(0),
    NORMAL(1),
    ARROW_LIGHT(2),
    CIRCLE_FIRE(3),
    MULTI_ARROW(4),
    MOON_LIGHT(5),
    PURPLE(6);
    private int value;
    private static Map map = new HashMap<>();
    private ATTACK_SKILL(int value) {
        this.value = value;
    }
    static {
        for (ATTACK_SKILL attackSkill : ATTACK_SKILL.values()) {
            map.put(attackSkill.value, attackSkill);
        }
    }
    public static ATTACK_SKILL valueOf(int attackSkill) {
        return (ATTACK_SKILL) map.get(attackSkill);
    }
    public int getValue() {
        return value;
    }
}
