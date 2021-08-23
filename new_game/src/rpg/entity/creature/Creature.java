package rpg.entity.creature;

import rpg.game.Game;
import rpg.game.GameStart;
import rpg.api.Animation;
import rpg.entity.Entity;

public abstract class Creature extends Entity {
    protected int MAXHP;
    protected int HP;
    protected int damage, armor;
    protected float deltaX, deltaY;
    protected boolean allowAttack, dead;
    protected Animation move_up, move_down, move_left, move_right;

    public Creature(Game game, float x, float y, int width, int height) {
        super(game, x, y, width, height);
        this.deltaX = 0;
        this.deltaY = 0;
        dead = false;

    }

    public void init() {

    }

    // move, xu ly va cham
    public void move() {
        if (x + width + deltaX >= GameStart.MAX_WIDTH || x + deltaX <= 0)
            return;
        if (y + deltaY <= 0 || y + height + deltaY >= GameStart.MAX_HEIGHT)
            return;

        int tx, ty, tyY;
        if (deltaX > 0) {
            tx = (int) (deltaX + rectForAttack.x + rectForAttack.width) / 32;
            ty = (int) (rectForAttack.y) / 32;
            tyY = (int) (rectForAttack.y + rectForAttack.height) / 32;
            if (!collisionWithTile(tx, ty) && !collisionWithTile(tx, tyY)) {
                x += deltaX;
            } else {
                x = tx * 32 + -rectForAttack.width - 1 - rectForAttack.x + x;
            }
        } else if (deltaX < 0) {
            tx = (int) (deltaX + rectForAttack.x) / 32;
            ty = (int) (rectForAttack.y) / 32;
            tyY = (int) (rectForAttack.y + rectForAttack.height) / 32;
            if (!collisionWithTile(tx, ty) && !collisionWithTile(tx, tyY)) {
                x += deltaX;
            } else {
                x = tx * 32 + 32 - rectForAttack.x + x;
            }
        }

        // moveY
        if (deltaY < 0) {
            ty = (int) ((deltaY + rectForAttack.y) / 32);
            if (!collisionWithTile((int) (rectForAttack.x) / 32, ty)
                    && !collisionWithTile((int) (rectForAttack.x + rectForAttack.width) / 32, ty)) {
                y += deltaY;
            } else {
                y = ty * 32 + 32 - rectForAttack.y + y;
            }
        } else if (deltaY > 0) {
            ty = (int) (deltaY + rectForAttack.y + rectForAttack.height) / 32;

            if (!collisionWithTile((int) (rectForAttack.x) / 32, ty)
                    && !collisionWithTile((int) (rectForAttack.x + rectForAttack.width) / 32, ty)) {
                y += deltaY;
            } else {
                y = ty * 32 - rectForAttack.y + y - rectForAttack.height - 1;
            }
        }
    }

    protected boolean collisionWithTile(int x, int y) {
        if (game.getCurrentMap().getIsRock(x, y) == 1)
            return true;
        else
            return false;
    }

    // cac ham thuoc tinh

    public void die() {
        if (this.HP <= 0)
            this.dead = true;
    }

    public void hurt(int damage) {
        this.HP = this.HP - damage;
        if (HP < 0)
            HP = 0;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int deltaHP) {
        this.HP = deltaHP;
        if (HP >= MAXHP)
            HP = MAXHP;

    }

    public int getMAXHP() {
        return MAXHP;
    }

    public void setMAXHP(int maxHP) {
        MAXHP = maxHP;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public boolean getDead() {
        return this.dead;
    }

   

    // cac ham cho attack

    public void setRectForAttack(int range) {
        this.setRectForAttack((int) (rectForAttack.x - range), (int) (rectForAttack.y - range),
                rectForAttack.width + range * 2, rectForAttack.height + range * 2);
    }

//    public boolean allowedAttack() {
//        return this.allowAttack;
//    }

    public void attackOther(Creature Other) {

    }

    public void state_update() {

    }

}