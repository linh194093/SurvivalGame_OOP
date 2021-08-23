package rpg.state;

import java.awt.Graphics;

import rpg.api.Animation;
import rpg.api.Texture;
import rpg.entity.creature.Player;
import rpg.entity.creature.npc.Boss;
import rpg.entity.creature.npc.MonsterManager;
import rpg.game.Game;
import rpg.game.ProgressBar;
import rpg.world.nature.LightPort;
import rpg.world.nature.Port;

public class GameState {
    private Game game;
    private Player player;
    private Port teleport;
    private ProgressBar progressBar;
    private LightPort lightPort;
    private Boss[] boss = new Boss[3]; //
    public MonsterManager[] monsters = new MonsterManager[3];
    private Animation firework;
    private int picture_die = 0;

    public GameState(Game game) {
        this.game = game;
        progressBar = new ProgressBar(this);
        teleport = new Port();
        lightPort = new LightPort();
        player = new Player(game, 190, 150, 32, 32);
        monsters[0] = new MonsterManager(game, player, 10);
        monsters[1] = new MonsterManager(game, player, 14);
        monsters[2] = new MonsterManager(game, player, 18);
        boss[0] = new Boss(game, player, 400, 400, 105, 90, 0);
        boss[1] = new Boss(game, player, 400, 400, 105, 90, 1);
        boss[2] = new Boss(game, player, 400, 400, 105, 90, 2);
        firework = new Animation(Texture.fire_work, 200);
    }

    public void update() {
        int id = this.game.getCurrentMap().getId();
        player.update();
        progressBar.update();
        if (id == 0) {
            teleport.update();
            lightPort.update();
        } else if (id == 1) {
            teleport.update();
            lightPort.update();
            boss[id - 1].update();
            monsters[1].update();
        } else if (id == 2) {
            teleport.update();
            lightPort.update();
            boss[id - 1].update();
            monsters[2].update();
        } else if (id == 3) {
            firework.update();
            monsters[0].update();
            boss[id - 1].update();
            teleport.update();
            lightPort.update();
        }

    }

    public void render(Graphics g) {
        int id = this.game.getCurrentMap().getId();
        progressBar.render(g, 0, 0);
        if (id == 2) {
            teleport.render(g, 0, 7);
            teleport.render(g, 10, 18.2);
            if (lightPort.getSemaphore() == 0)
                lightPort.render(g, 0, 6.5);
            if (lightPort.getSemaphore() == 1)
                lightPort.render(g, 10, 18);
            player.render(g);
            monsters[2].render(g);
            boss[id - 1].render(g);
        } else if (id == 1) {
            teleport.render(g, 0, 4);
            teleport.render(g, 22.5, 11.5);// bo sung
            if (lightPort.getSemaphore() == 0)
                lightPort.render(g, 0, 3.8);
            if (lightPort.getSemaphore() == 1)
                lightPort.render(g, 22.6, 10.8);
            player.render(g);
            monsters[1].render(g);
            boss[id - 1].render(g);
        } else if (id == 3) {
            teleport.render(g, 8.9, 0);// bo sung
            lightPort.render(g, 8.9, 0);
            player.render(g);
            monsters[0].render(g);
            boss[id - 1].render(g);
        } else if (id == 0) {
            teleport.render(g, 22, 10);
            lightPort.render(g, 22, 9.5);
            player.render(g);
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public LightPort getLightPort() {
       return lightPort;
    }

    public void endgame(Graphics g) {
        int id = this.game.getCurrentMap().getId();
        boss[id - 1].render(g);
        if (boss[id - 1].getHP() == 0) {
            if (picture_die < firework.getImageLength() * 6) {
                if (picture_die == 0) {
                    firework.setIndex(0);
                }
                picture_die++;
                renderendgame(g, 200, 300, 160, 116);
                renderendgame(g, 450, 100, 160, 116);
                renderendgame(g, 600, 600, 160, 116);
                renderendgame(g, 600, 300, 160, 116);
                renderendgame(g, 200, 600, 160, 116);
            }
        }
    }

    public void renderendgame(Graphics g, float x, float y, int width, int height) {

        g.drawImage(firework.getCurrentImage(Texture.fire_work), (int) (x - width / 2), (int) (y - height / 2), width,
                height, null);

    }

}
