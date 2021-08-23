package rpg.entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import rpg.game.Game;
import rpg.game.GameStart;
import rpg.api.Animation;
import rpg.api.Texture;

public class Player extends Creature {
    private int dem = 0;
    private int previous_state = 0;
    private boolean takeItems;
    private long cooldown = 1000, time = 5000, lastTime = 0;
    private Animation attack_up, attack_down, attack_left, attack_right;
    private int killDragon[];

    public Player(Game game, float x, float y, int width, int height) {
        super(game, x, y, width, height);

        move_up = new Animation(Texture.player_up, 300);
        move_down = new Animation(Texture.player_down, 300);
        move_left = new Animation(Texture.player_left, 300);
        move_right = new Animation(Texture.player_right, 300);

        attack_up = new Animation(Texture.attack_up, 100);
        attack_down = new Animation(Texture.attack_down, 100);
        attack_left = new Animation(Texture.attack_left, 100);
        attack_right = new Animation(Texture.attack_right, 100);
        this.HP = 1500;
        this.damage = 15;
        this.armor = 2;
        rectForAttack.x = (int) x;
        rectForAttack.y = (int) y;
        rectForAttack.width = 6;
        rectForAttack.height = 8;
        takeItems = false;
        MAXHP = 1500;
        killDragon = new int[4];

    }

    private boolean checkTime() {
        time = System.currentTimeMillis() - lastTime;
        if (time > cooldown) {

            time = 0;

            lastTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    private void timeAttack() {

        if (allowAttack == false)
            return;

        if (dem < Texture.getLengthBufferedImage(Texture.attack_right) * 5)
            return;

        if (checkTime()) {
            allowAttack = true;
            dem = 0;
        } else
            allowAttack = false;
    }

    public boolean isAttack() {
        return allowAttack;
    }

    @Override
    public void update() {
        setRectForAttack((int) x + 10, (int) y + 20, (int) rectForAttack.getWidth(), (int) rectForAttack.getHeight());
        if ((!this.dead) && (game.getGameState().getLightPort().getEnd() == false)) {
            die();
            state_update();
            loca_update();
            move();
            timeAttack();
        }
    }

    public void state_update() {
        move_up.update();
        move_down.update();
        move_left.update();
        move_right.update();
        attack_up.update();
        attack_down.update();
        attack_right.update();
        attack_left.update();

    }

    private void loca_update() { // update vi tri
        deltaX = 0.f;
        deltaY = 0.f;
        if (game.getKeyaction().up)
            if (y > 0)
                deltaY = -3.0f;
        if (game.getKeyaction().down)
            if (y < GameStart.MAX_HEIGHT - 32)
                deltaY = 3.0f;
        if (game.getKeyaction().left)
            if (x > 0)
                deltaX = -3.0f;
        if (game.getKeyaction().right)
            if (x < GameStart.MAX_WIDTH - 32)
                deltaX = 3.0f;
        if (game.getKeyaction().attack) {
            allowAttack = true;
        }
        if (game.getKeyaction().pick_up) {
            takeItems = true;

        }
    }

    public void setTakeItems(boolean takeItems) {
        this.takeItems = takeItems;
    }

    public boolean getTakeItems() {
        return this.takeItems;
    }

    public void drawLeft(Graphics g) {
        if (!allowAttack)
            g.drawImage(move_left.getCurrentImage(Texture.player_left), (int) x, (int) y, width, height, null);
        else {
            g.drawImage(attack_left.getCurrentImage(Texture.attack_left), (int) x, (int) y, width, height, null);
            dem++;
        }
        previous_state = 1;
    }

    public void drawRight(Graphics g) {
        if (!allowAttack)
            g.drawImage(move_right.getCurrentImage(Texture.player_right), (int) x, (int) y, width, height, null);
        else {

            g.drawImage(attack_right.getCurrentImage(Texture.attack_right), (int) x, (int) y, width, height, null);
            dem++;
        }
        previous_state = 2;
    }

    public void drawUp(Graphics g) {
        if (!allowAttack)
            g.drawImage(move_up.getCurrentImage(Texture.player_up), (int) x, (int) y, width, height, null);
        else {
            g.drawImage(attack_up.getCurrentImage(Texture.attack_up), (int) x, (int) y, width, height, null);
            dem++;
        }
        previous_state = 3;
    }

    public void drawDown(Graphics g) {
        if (!allowAttack)
            g.drawImage(move_down.getCurrentImage(Texture.player_down), (int) x, (int) y, width, height, null);
        else {
            g.drawImage(attack_down.getCurrentImage(Texture.attack_down), (int) x, (int) y, width, height, null);
            dem++;
        }
        previous_state = 4;
    }

    @Override
    public void render(Graphics g) {
        if (HP > 0)
        {
        if (game.getGameState().getLightPort().getEnd() == false) {
            if (deltaX == 0 && deltaY == 0) {
                if (previous_state == 0) {
                    drawRight(g);
                }
                if (previous_state == 1)
                    drawLeft(g);
                if (previous_state == 2)
                    drawRight(g);
                if (previous_state == 3)
                    drawUp(g);
                if (previous_state == 4)
                    drawDown(g);
            }
            if (deltaX < 0) {
                drawLeft(g);
            }
            if (deltaX > 0) {
                drawRight(g);
            }
            if (deltaY < 0) {
                drawUp(g);
            }
            if (deltaY > 0) {
                drawDown(g);
            }
            g.setColor(Color.green);
            g.fillRect((int) x - 5, (int) y - 4, 50, 4);
            g.setColor(Color.red);
            g.fillRect((int) x - 5, (int) y - 4, this.HP / 30, 4);
        }
    }
    }

    public int getKillDragon(int i) {
        return killDragon[i];
    }

    public void setKillDragon(int i) {
        this.killDragon[i] = 1;
    }

}
