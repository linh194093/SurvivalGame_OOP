package rpg.entity.creature.npc;

//////////////Minh Sua //////////////////////
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import rpg.api.Animation;
import rpg.api.Texture;
import rpg.entity.Entity;
import rpg.entity.creature.Creature;
import rpg.entity.creature.Player;
import rpg.game.Game;
import java.awt.*;

public class Boss extends Npc {

	private int picture_attack;
	double alpha = 0;
	private Fire fire;
	private int ID;
	private Animation bom;
	private int picture_die = 0;

	public Boss(Game game, Player player, float x, float y, int width, int height, int ID) {
		super(game, player, x, y, width, height);
		this.ID = ID;

		move_up = new Animation(Texture.boss_up[ID], 200);
		move_down = new Animation(Texture.boss_down[ID], 200);
		move_left = new Animation(Texture.boss_left[ID], 200);
		move_right = new Animation(Texture.boss_right[ID], 200);

		center_X = 600;
		center_Y = 400;
		this.HP = 750 * k;
		this.damage = 8 + 2 * ID;
		this.armor = 3 + 2 * ID;
		time_move = 0;
		picture_attack = 0;
		fire = new Fire(game, x, y, 192, 192, 0);
		bom = new Animation(Texture.bom_bum, 100);

	}

	public void state_update() {
		die();
		move_down.update();
		move_left.update();
		move_right.update();
		move_up.update();
		move();

	}

	public void move() {
		moveX();
		// moveY();
		x += deltaX;
		y += deltaY;
	}

	public void moveX() {
		if (outOfRange(player.getX(), player.getY())) {
			allowAttack = true;

			if (Math.abs(player.getX() - x) >= 50f) {
				if (player.getX() > x + 3.0f) {
					deltaX = 3.0f;

				} else
					deltaX = -3.0f;
				deltaY = 0;
				return;
			} else
				moveY();
		} else {
			picture_attack = 0;
			allowAttack = false;
			if (System.currentTimeMillis() - time_move > 3000 || !outOfRange(this.x, this.y)) {
				time_move = System.currentTimeMillis();

				deltaX = 0;
				deltaY = 0;
				if (ThreadLocalRandom.current().nextInt(1, 100) < 50) {
					super.moveX();
				} else {
					super.moveY();
				}
			}
			return;
		}

	}

	public void moveY() {
		if (Math.abs(player.getY() - y) >= 50.0f) {

			if (player.getY() > y + 3.0f) {
				deltaY = 3.0f;
			} else
				deltaY = -3.0f;
			deltaX = 0;
			return;

		}
		deltaX = deltaY = 0;
		return;
	}

	private Graphics2D rotate(Graphics g, int up_down) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate((int) (x), (int) (y));

		float distance_x = player.getX() - this.x;
		float distance_y = this.y - player.getY();
		distance_y *= up_down;
		if (distance_y < 0.000000001) {
			distance_y = 0.00000001f;
		}
		try {
			alpha = Math.atan((double) (distance_x / distance_y)) * up_down;
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
		}

		g2d.rotate(alpha);
		g2d.translate(-width / 2, -height / 2);

		return g2d;
	}

	private void unrotate(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(0, 0);
		g2d.rotate(-alpha);
	}

	// ----------------------------------------------------
	private void updateFire() {

		if (picture_attack >= fire.getFire().getImageLength()) {
			picture_attack = 0;
			fire.setIsBreak(false);
			fire.setPictureAttack(picture_attack);
		}
		if (picture_attack == 0) {
			if (player.getY() < y)
				fire.setToado(x, y - 2);
			else
				fire.setToado(x, y + 10);
			// fire.setRoad(player.getX(), player.getY());

			fire.setRoad(player.getX(), player.getY());
		}

		fire.setPictureAttack(picture_attack);
		picture_attack += 1;

		// attackOther();
		attackPlayer();
	}

	// ------------------------------------------------------

	@Override
	public void update() {
		bom.update();
		if (dead)
			return;
		state_update();
		fire.update();
		playerAttack();
	}

	@Override
	public void render(Graphics g) {
		if (dead) {
			if (picture_die < bom.getImageLength() * 3) {
				if (picture_die == 0) {
					bom.setIndex(0);
				}
				picture_die++;
				// bom_die.setToado(x, y);
				// bom_die.getBomAnimation().setIndex(picture_die);
				renderbom(g, 160, 116);
			}

			return;
		}
		if (allowAttack) {
			updateFire();
			fire.render(g);
		}

		if (HP != 0) {
			g.setColor(Color.green);
			g.fillRect((int) x - 70, (int) y - 40, 150, 4);
			g.setColor(Color.red);
			g.fillRect((int) x - 70, (int) y - 40, HP / (5 * k), 4);
			// g.fillRect((int)x,(int) y, width, height);

			if (deltaX == 0 && deltaY == 0) {

				if ((int) player.getY() < (int) y) {

					rotate(g, 1).drawImage(move_up.getCurrentImage(Texture.boss_up[ID]), (int) 0, (int) 0, width,
							height, null);
				} else {

					rotate(g, -1).drawImage(move_down.getCurrentImage(Texture.boss_down[ID]), (int) 0, (int) 0, width,
							height, null);
				}
			}

			if (deltaX < 0) {
				g.drawImage(move_left.getCurrentImage(Texture.boss_left[ID]), (int) (x - width / 2),
						(int) (y - height / 2), width, height, null);
			}
			if (deltaX > 0) {

				g.drawImage(move_right.getCurrentImage(Texture.boss_right[ID]), (int) (x - width / 2),
						(int) (y - height / 2), width, height, null);

			}
			if (deltaY < 0) {
				g.drawImage(move_up.getCurrentImage(Texture.boss_up[ID]), (int) (x - width / 2), (int) (y - height / 2),
						width, height, null);

			}
			if (deltaY > 0) {
				g.drawImage(move_down.getCurrentImage(Texture.boss_down[ID]), (int) (x - width / 2),
						(int) (y - height / 2), width, height, null);

			}
		}

		unrotate(g);

	}

	@Override
	public void die() {

		if (HP <= 0) {
			dead = true;
			player.setKillDragon(game.getCurrentMap().getId());
			player.setHP(player.getHP() + 1000);
		}
	}

	// @Override
	// public void attackOther() {
	// if (allowAttack) {
	// player.setRectForAttack((int) player.getX(), (int) player.getY(),
	// (int) player.getRectForAttack().getWidth(), (int)
	// player.getRectForAttack().getHeight());
	// fire.setRectForAttack((int) (x - fire.getWidth() / 2) + 30, (int) (y -
	// fire.getHeight() / 2) + 45, 120,
	// 110);
	// if (attackOther.attack(player, fire, this.damage - player.getArmor())) {

	// }

	// }

	// }

	@Override
	public void playerAttack() {
		player.setRectForAttack(15);
		this.setRectForAttack((int) (x - width / 2), (int) (y - height / 2), width, height);
		if (player.isAttack())
			attackOther(this, player, player.getDamage() - this.armor);
		player.setRectForAttack(-15);

	}

	public void renderbom(Graphics g, int width, int height) {

		g.drawImage(bom.getCurrentImage(Texture.bom_bum), (int) (x - width / 2), (int) (y - height / 2), width, height,
				null);

	}

	@Override
	public void attackOther(Creature target, Entity source, int damage) {
		if (source.getRectForAttack().intersects(target.getRectForAttack())) {
			target.hurt(damage);
		}
	}

	@Override
	public void attackPlayer() {

		if (allowAttack) {
			player.setRectForAttack((int) player.getX(), (int) player.getY(),
					(int) player.getRectForAttack().getWidth(), (int) player.getRectForAttack().getHeight());
			fire.setRectForAttack((int) (x - fire.getWidth() / 2) + 30, (int) (y - fire.getHeight() / 2) + 45, 120,
					110);
			attackOther(player, fire, this.damage - player.getArmor());

		}
	}

}