package rpg.world.nature;

import java.awt.Graphics;

import rpg.api.Animation;
import rpg.api.Texture;


public class Port {
	private Animation port;

	public Port(){
		port=new Animation(Texture.port,300);	
	}
	public void update()
	{
		port.update();
	}
	public void render(Graphics g,double x,double y) {
		
		g.drawImage(port.getCurrentImage(Texture.port), (int)(32*x),(int)(32*y),32*3,32*2, null);
	}
}
