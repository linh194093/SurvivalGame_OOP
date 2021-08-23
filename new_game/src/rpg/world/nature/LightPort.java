package rpg.world.nature;

import java.awt.Graphics;

import rpg.api.Animation;
import rpg.api.Texture;


public class LightPort {
	private Animation lightPort;
	private boolean isVisitable;
	private int semaphore;
	private boolean end;
	public LightPort(){
		isVisitable=false;
		end=false;
		lightPort=new Animation(Texture.lightPort,50);	
	}
	public void update()
	{
		if(isVisitable==true)
			lightPort.update();
	}
	public void render(Graphics g,double x,double y) {
		if(isVisitable==true)
		{
			end=true;
			g.drawImage(lightPort.getCurrentImage(Texture.lightPort), (int)(32*x),(int)(32*y),32*3,32*2, null);
			if(lightPort.getIndex()+1==lightPort.getImageLength())
			{
				lightPort.setIndex(0);
				isVisitable=false;
				end=false;
			}
		}
	}
	public void setIsVisitable(boolean isVisitable)
	{
		this.isVisitable=isVisitable;
	}
	public boolean getIsVisitable()
	{
		return isVisitable;
	}
	public void setSemaphore(int semaphore)
	{
		this.semaphore=semaphore;
	}
	public int getSemaphore()
	{
		return semaphore;
	}
	public void setEnd(boolean end)
	{
		this.end=end;
	}
	public boolean getEnd()
	{
		return end;
	}
}

