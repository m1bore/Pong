package Pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball {
	//tamaño de la pelota
	private static final int DIAMETER = 55;
	
	//x e y posicion de la pelota
	private int x=225;
	private final int X=225;
	private int y=150;
	private final int Y=150;
	//velocidad
	private int xs=1;
	private int ys=1;
	private Game game;
	
	public Ball(Game game) {
		this.game=game;

	}
	//Metodo para el movimiento
	public void move() {
		if (x + xs< 0)
			xs = 1;	
		if (x + xs > game.getWidth() - DIAMETER)
			xs = -1;
		if (y + ys < 0)
			ys = 1;
		if (y + ys > game.getHeight() - DIAMETER)
			ys = -1;
		if (x + xs < game.palo.getRightX() - 28) {
				game.score ++;
		 int num = 0;
			if (num!=game.getScore()) {
				setX(X);
				setY(Y);
				setXs(1);
				setYs(1);
				
			}
			num =game.getScore();
		 }
		if (x + xs > game.palo2.getRightX()-42) {
			game.score2 ++;
			int num2 = 0;
		if (num2!=game.getScore2()) {
			setX(X);
			setY(Y);
			setXs(-1);
			setYs(1);
			
		}
		num2 =game.getScore2();
		}
		if (collision()){
			xs = 1;
			x = game.palo.getRightX()+DIAMETER-45;
		}
		if (collision2()){
			xs = -1;
			x = game.palo2.getRightX()-DIAMETER-15;
		}

		x += xs;
		y += ys;
	}
	//Con este metodo detectamos las colisiones
	private boolean collision() {
		return game.palo.getBounds().intersects(getBounds());
	}
	private boolean collision2() {
		return game.palo2.getBounds().intersects(getBounds());
	}
	
	
	//Para pintar la pelota
	public void paint(Graphics g) {    
		g.setColor(Color.WHITE);
	    g.fillOval(x, y, DIAMETER, DIAMETER);   
	  }    
	//Metodo para pasar la informacion de la pelota en un objeto
	private Rectangle getBounds() {
		return new Rectangle(x, y, DIAMETER, DIAMETER);
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setXs(int xs) {
		this.xs = xs;
	}
	public void setYs(int ys) {
		this.ys = ys;
	}
	
}
