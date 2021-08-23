package rpg.entity.items;

import java.awt.Graphics;

import rpg.api.Texture;
import rpg.entity.creature.Player;
import rpg.game.Game;

public class BuffHP extends Item {

    public BuffHP(Game game, float x, float y, int width, int height) {
        super(game, x, y, width, height);

    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        if (isAppear == 1)
            g.drawImage(Texture.item[0], getRectForAttack().x, getRectForAttack().y, this.width, this.height, null);
    }

    @Override
    public void itemBuff(Player player) {
        player.setHP(player.getHP() + 200);
        if (player.getMAXHP() <= 2000)
            player.setMAXHP(player.getMAXHP() + 200);
        System.out.println(player.getHP());
    }
}