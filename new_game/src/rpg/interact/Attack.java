package rpg.interact;

import rpg.entity.Entity;
import rpg.entity.creature.Creature;

public interface Attack {
    void attackOther(Creature target, Entity source, int damage);
}
