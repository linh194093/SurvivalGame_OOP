package rpg.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import rpg.state.GameState;

public class ProgressBar {
	private GameState gameState;
	private int barHP;
	private int barArmor;
	private int barDamage;
	private String stringHP;
	private String stringArmor;
	private String stringDamage;
	
	public ProgressBar(GameState gameState)
	{
		this.gameState=gameState;
	}
	public void update(){
		barHP=gameState.getPlayer().getHP();
		barArmor=gameState.getPlayer().getArmor();
		barDamage=gameState.getPlayer().getDamage();
		stringHP=String.valueOf(barHP);
		stringArmor=String.valueOf(barArmor);
		stringDamage=String.valueOf(barDamage);
		
	}
	public void render(Graphics g,double x,double y) {
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		g.setColor(Color.red);
		g.drawString("HP:", 10, 20);
		g.drawString(stringHP, 50, 20);
		g.setColor(Color.white);
		g.fillRect(100, 10, 100, 4); 
	    g.setColor(Color.red);
	    g.fillRect(100, 10, barHP/15,4);
    	
    	g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
    	g.setColor(Color.green);
    	g.drawString("Armor:", 10, 40);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
    	g.setColor(Color.green);
    	g.drawString(stringArmor, 100, 40);
    	
    	g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
    	g.setColor(Color.blue);
    	g.drawString("Damage:", 10, 60);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
    	g.setColor(Color.blue);
    	g.drawString(stringDamage, 100, 60);
	}	
}

