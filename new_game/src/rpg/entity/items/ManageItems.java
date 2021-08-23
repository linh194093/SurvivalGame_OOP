package rpg.entity.items;

import java.util.LinkedList;
import java.util.List;
import rpg.game.Game;

public class ManageItems {
    private BuffHP bottleHP;
    private BuffDamage sword;
    private BuffDefense shield;
    private List<Item> listItems;

    public ManageItems(Game game) {
        bottleHP = new BuffHP(game, 0, 0, 20, 20);
        sword = new BuffDamage(game, 0, 0, 20, 20);
        shield = new BuffDefense(game, 0, 0, 20, 20);
        listItems = new LinkedList<>();
        listItems.add(bottleHP);
        listItems.add(sword);
        listItems.add(shield);
    }

    public Item getItem(int id) {
        return listItems.get(id);
    }

    public int getSizeListItems() {
        return listItems.size();
    }
}