package rpg.entity.items;

import java.awt.Graphics;

import rpg.api.Texture;
import rpg.entity.creature.Player;
import rpg.game.Game;

public class BuffDamage extends Item {

    public BuffDamage(Game game, float x, float y, int width, int height) {
        super(game, x, y, width, height);

    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        if (isAppear == 1)
            g.drawImage(Texture.item[2], getRectForAttack().x, getRectForAttack().y, this.width, this.height, null);
    }

    @Override
    public void itemBuff(Player player) {
        System.out.println("tang damage");
        if (player.getDamage() >= 13)
            return;
        player.setDamage(player.getDamage() + 1);
    }
}