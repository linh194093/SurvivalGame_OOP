package rpg.game;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import rpg.api.KeyAction;
import rpg.game.music.Music;
import rpg.game.setting.Setting;
import rpg.state.GameState;
import rpg.world.Map;
import rpg.world.WorldMap;

public class Game implements Runnable {
	private GameState gameState;
	private Display display;
	public int width, height, currentMap;
	public String title;
	private Rectangle[] port;
	private Map map;
	private WorldMap worldMap;
	private Thread thread;
	private boolean running = false;
	private String musicFilePath;
	private Music music;
	private KeyAction key;
	private BufferStrategy bs;
	private Graphics g;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		key = new KeyAction();

	}

	private void init() {
		worldMap = new WorldMap();
		map = worldMap.getMap(0);
		currentMap = 0;
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(key);
		gameState = new GameState(this);
	}

	// thread

	public synchronized void start() {
		if (running) {
			return;
		}
		System.out.println("Che Do : " + Setting.level);
		running = true;
		thread = new Thread(this);
		thread.start();
		musicFilePath = "src/Assets/nhac3.wav";
		music = new Music(musicFilePath);
		if (Setting.musicable == true)
			music.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		init();
		int fps = 35;
		double timePerTick = 1000000000.0 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if (delta >= 1) {
				update();
				render();
				delta--;
			}

		}
		stop();
	}

	////

	public void changeMap() {
		if (currentMap == 0) {
			if (gameState.getPlayer().getRectForAttack().intersects(port[0])) {
				currentMap = 1;
				gameState.getLightPort().setSemaphore(0);
				gameState.getLightPort().setIsVisitable(true);
				gameState.getPlayer().setX(50);
				gameState.getPlayer().setY(170);
			}
		} else if (currentMap == 1) {
			if (gameState.getPlayer().getRectForAttack().intersects(port[0])) {
				currentMap = 0;
				gameState.getLightPort().setIsVisitable(true);
				gameState.getPlayer().setX(700);
				gameState.getPlayer().setY(320);
			} else if (gameState.getPlayer().getRectForAttack().intersects(port[1])
					&& gameState.getPlayer().getKillDragon(1) == 1) {
				currentMap = 2;
				gameState.getLightPort().setSemaphore(0);
				gameState.getLightPort().setIsVisitable(true);
				gameState.getPlayer().setX(40);
				gameState.getPlayer().setY(246);
			}
		} else if (currentMap == 2) {
			if (gameState.getPlayer().getRectForAttack().intersects(port[0])) {
				currentMap = 1;
				gameState.getLightPort().setSemaphore(1);
				gameState.getLightPort().setIsVisitable(true);
				gameState.getPlayer().setX(750);
				gameState.getPlayer().setY(384);
			} else if (gameState.getPlayer().getRectForAttack().intersects(port[1])
					&& gameState.getPlayer().getKillDragon(2) == 1) {
				currentMap = 3;
				gameState.getLightPort().setSemaphore(0);
				gameState.getLightPort().setIsVisitable(true);
				gameState.getPlayer().setX(320);
				gameState.getPlayer().setY(32);
			}
		}
		if (currentMap == 3) {
			if (gameState.getPlayer().getRectForAttack().intersects(port[0])) {
				currentMap = 2;
				gameState.getLightPort().setSemaphore(1);
				gameState.getLightPort().setIsVisitable(true);
				gameState.getPlayer().setX(352);
				gameState.getPlayer().setY(580);
			}
		}
	}

	public KeyAction getKeyaction() {
		return key;
	}

	public Map getCurrentMap() {
		return map;
	}

	public GameState getGameState() {
		return gameState;
	}

	private void update() {
		key.update();
		gameState.update();
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(2);
			return;
		}
		g = bs.getDrawGraphics();

		if (gameState.getPlayer().getKillDragon(3) == 1) {
			gameState.endgame(g);
			display.renderWinGame(g);
			music.setRunning(false);
			if (key.space == true) {
				new GameStart();
				display.getFrame().dispose();
				this.stop();
			}
		} else if (gameState.getPlayer().getDead()) {
			display.renderEndGame(g);
			music.setRunning(false);
			if (key.space == true) {
				new GameStart();
				display.getFrame().dispose();
				this.stop();

			}
		} else {
			map = worldMap.getMap(currentMap);
			port = map.getPort();
			changeMap();
			map.render(g);
			gameState.render(g);
		}
		bs.show();
		g.dispose();
	}

}
