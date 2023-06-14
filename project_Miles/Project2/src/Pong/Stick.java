package Pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Stick {
	private static final int X =20;
	private static final int WIDTH=15;
	private static final int HEIGHT= 70;
	private int y=150;
	private int ys=0;
	private Game game;
	
	public Stick(Game game) {
		this.game=game;
	}
	
	

	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	//Para pintar el palo
	public void paint(Graphics g) {    
		g.setColor(Color.WHITE);
	    g.fillRect(X, y, WIDTH, HEIGHT);   
	  } 
	//Metodo para mover los palos
	public void move() {
		if (y + ys > 0 && y + ys < game.getHeight()-60) {
			y += ys;
		}
	}

	
	//Metodo para recoger cuando se suelta las teclas
	public void keyReleased(KeyEvent e) {
		ys = 0;
	}
	//Metodo para recoger cuando se pulsa las teclas
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			ys = 1;	
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			ys = -1;
		}
			
	}
	//Metodo para pasar la informacion del palo en un objeto
	public Rectangle getBounds() {
		return new Rectangle(X, y, WIDTH, HEIGHT);
	}
	//Metodo para saber el valor de x de la parte derecha del objeto
	public int getRightX() {
		return X + WIDTH;
	}

}
