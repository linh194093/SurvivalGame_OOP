package rpg.world.nature;

import java.awt.Graphics;
import java.awt.Image;

import rpg.api.Texture;

public class Statue extends Tile{
	private int id;
	private static Image[] statues= {Texture.getTexture("statue_1"),Texture.getTexture("statue_2")};

	public Statue() {
		super(statues, 0, 0);
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(statues[id], x*32, y*32, null);
	}

}
