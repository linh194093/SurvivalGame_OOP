package rpg.entity.creature.npc;

import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Graphics;
import java.awt.Color;
import rpg.api.Animation;
import rpg.entity.Entity;
import rpg.entity.creature.Creature;
import rpg.entity.creature.Player;
import rpg.entity.items.ManageItems;
import rpg.game.Game;
import rpg.interact.Interact;

public class Monster extends Npc implements Interact {

    private Animation monsterMove;
    private BufferedImage[] Image;
    private int idItems;
    private ManageItems manageItems;

    public Monster(Game game, Player player, float x, float y, int width, int height) {
        super(game, player, x, y, width, height);
        this.HP = 200 * k;
        this.damage = 2 + k - 1;
        this.armor = 2;
        this.R = 50.0;
        time_move = 0;
        manageItems = new ManageItems(game);
        idItems = 0;

    }

    public void setRandomCenter() {
        center_X = ThreadLocalRandom.current().nextInt(1, 300);
        center_Y = ThreadLocalRandom.current().nextInt(1, 300);
    }

    public void setAnimationImage(BufferedImage[] Image) {
        this.Image = Image;
        monsterMove = new Animation(Image, 200);
    }

    public Animation getAnimationImage() {
        return monsterMove;
    }

    public BufferedImage[] getImage() {
        return Image;
    }

    public void setIdItems(int id) {
        this.idItems = id % manageItems.getSizeListItems();
    }

    public void update() {
        die();
        this.setRectForAttack((int) x, (int) y, width, height);
        if (dead) {

            if (manageItems.getItem(idItems).getIsAppear() == 0) {
                manageItems.getItem(idItems).setRectForAttack((int) x, (int) y, 32, 32);
                manageItems.getItem(idItems).setIsAppear(1);
                manageItems.getItem(idItems).setRectForAttack((int) x, (int) y, 32, 32);
            }
            playerTakeItems();

        } else {
            monsterMove.update();
            move();
            playerAttack();
            // attackOther();
            attackPlayer();

        }
    }

    public void render(Graphics g) {
        if (dead) {
            if (manageItems.getItem(idItems).getIsAppear() == 1)
                manageItems.getItem(idItems).render(g);

            return;
        }
        g.setColor(Color.gray);
        g.fillRect((int) x + 4, (int) y - 4, 25, 4); //
        g.setColor(Color.red);
        g.fillRect((int) x + 4, (int) y - 4, HP / (8 * k), 4);
        g.drawImage(getAnimationImage().getCurrentImage(getImage()), (int) x, (int) y, 32, 32, null);
    }

    // @Override
    // public void attackOther() {
    // player.setRectForAttack(0);
    // this.setRectForAttack(0);
    // if (!dead)
    // attackOther.attack(player, this, this.damage);

    // }

    @Override
    public void playerAttack() {
        player.setRectForAttack(10);
        this.setRectForAttack((int) x, (int) y, width, height);
        if (player.isAttack())
            if (!dead)
                attackOther(this, player, player.getDamage() - this.armor);
        player.setRectForAttack(-10);
    }

    @Override
    public void move() {
        super.move();
        if (System.currentTimeMillis() - time_move > 1000 || !outOfRange(x, y)) {
            time_move = System.currentTimeMillis();
            // rand = Math.random();
            if (ThreadLocalRandom.current().nextInt(1, 100) < 50) {
                moveX();
            } else {
                moveY();
            }
        }
        // x+= deltaX;
        // y+= deltaY;

    }

    public void playerTakeItems() {
        if (player.getTakeItems() && manageItems.getItem(idItems).getIsAppear() == 1) {
            player.setRectForAttack(10);
            if (manageItems.getItem(idItems).takeItem(player)) {
                player.setTakeItems(false);
            }
            player.setRectForAttack(-10);
        }
    }

    @Override
    public void attackOther(Creature target, Entity source, int damage) {
        if (source.getRectForAttack().intersects(target.getRectForAttack())) {
            target.hurt(damage);
        }
    }

    @Override
    public boolean interactOther(Entity other) {
        return other.getRectForAttack().intersects(this.getRectForAttack());
    }

    @Override
    public void attackPlayer() {

        player.setRectForAttack(0);
        this.setRectForAttack(0);
        if (!dead)
            attackOther(player, this, this.damage);
    }

}
