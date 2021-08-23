package rpg.entity.items;

import rpg.api.Animation;
import rpg.entity.Entity;
import rpg.entity.creature.Player;
import rpg.game.Game;
import rpg.interact.Interact;

import java.awt.*;

public class Item extends Entity implements Interact {

    protected Animation item;
    protected int isAppear;

    public Item(Game game, float x, float y, int width, int height) {
        super(game, x, y, width, height);
        isAppear = 0;
    }

    public void setRectForAttack(Rectangle a) {
        rectForAttack.setBounds(a);
    }

    public int getIsAppear() {
        return isAppear;
    }

    public void setIsAppear(int isAppear) {
        this.isAppear = isAppear;
    }

    public boolean takeItem(Player player) {
        if (isAppear == 1)
            if (interactOther(player) && game.getKeyaction().pick_up) {
                itemBuff(player);
                isAppear = -1;
                return true;
            }
        return false;
    }

    public void itemBuff(Player player) {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public boolean interactOther(Entity other) {
        return other.getRectForAttack().intersects(this.getRectForAttack());
        // return false;
    }

}
