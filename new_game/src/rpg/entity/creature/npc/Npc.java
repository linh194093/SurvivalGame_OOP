package rpg.entity.creature.npc;

// import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import rpg.entity.creature.Creature;
import rpg.entity.creature.Player;
import rpg.game.Game;
import rpg.interact.Attack;

public abstract class Npc extends Creature implements Attack {
	protected static int k = 1;
	protected Player player;
	protected float center_X, center_Y;
	protected double R;
	protected long time_move;

	public Npc(Game game, Player player, float x, float y, int width, int height) {
		super(game, x, y, width, height);
		this.player = player;
		allowAttack = false;
		time_move = 0;
		center_X = 200f;
		center_Y = 200f;
		R = 200;

	}

	public boolean outOfRange(float x, float y) {
		double a = Math.sqrt((x - center_X) * (x - center_Y) + (y - center_Y) * (y - center_Y));
		if (a >= R) {
			return false;
		} else
			return true;
	}

	public void moveX() {
		deltaX = 0;
		deltaY = 0;
		// if(this.x > 0 && this.x < GameStart.MAX_WIDTH-32) {
		if (outOfRange(this.x, this.y)) {
			if (ThreadLocalRandom.current().nextInt(1, 100) < 50) {
				deltaX = -1.5f;
			} else
				deltaX = 1.5f;
		} else {
			if (center_X > x) {
				deltaX = 1.5f;
			} else
				deltaX = -1.5f;
		}

	}

	public void moveY() {
		deltaX = 0;
		deltaY = 0;

		if (outOfRange(this.x, this.y)) {

			if (ThreadLocalRandom.current().nextInt(1, 100) < 50)
				deltaY = -1.5f;
			else
				deltaY = 1.5f;
		} else {
			if (center_Y > y) {
				deltaY = 1.5f;
			} else
				deltaY = -1.5f;
		}
		// moveX = 0;

	}

	public float getCenter_X() {
		return center_X;
	}

	public void setCenter_X(float center_X) {
		this.center_X = center_X;
	}

	public float getCenter_Y() {
		return center_Y;
	}

	public void setCenter_Y(float center_Y) {
		this.center_Y = center_Y;
	}

	public double getR() {
		return R;
	}

	public static void setK(int k) {
		Npc.k = k;
	}

	public static int getK() {
		return Npc.k;
	}

	public void setR(double r) {
		R = r;
	}

	public long getTime_move() {
		return time_move;
	}

	public void setTime_move(long time_move) {
		this.time_move = time_move;
	}

	// public abstract void attackOther();
	public abstract void attackPlayer();

	public abstract void playerAttack();

	// public abstract void update_move();

}
