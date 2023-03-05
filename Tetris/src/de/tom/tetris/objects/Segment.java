package de.tom.tetris.objects;

import java.awt.Color;
import java.awt.Graphics;

import de.tom.tetris.Tetris;

public class Segment {
	
	// attributes
	Tetris tetris;
	
	int x, y, segmentType;
	boolean existing = false;
	
	// constructor
	public Segment(Tetris tetris, int x, int y, int segmentType) {
		this.tetris = tetris;
		this.x = x;
		this.y = y;
		this.segmentType = segmentType;
		existing = true;
		
	}
	// methods:
	// setters
	
	public void draw(Graphics g) {
		//TODO: Draw!
		g.setColor(Color.RED);
		int width = (int) ((float) 500*((float)tetris.getFrame().getContentPane().getHeight()/(float)600));
		if(width > tetris.getFrame().getContentPane().getWidth()) width = tetris.getFrame().getContentPane().getWidth();
		int height = (int) ((float) 600*((float)tetris.getFrame().getContentPane().getWidth()/(float)500));
		if(height > tetris.getFrame().getContentPane().getHeight()) height = tetris.getFrame().getContentPane().getHeight();

		g.fillRect((x*Math.round(tetris.getScale()))+tetris.getFrame().getContentPane().getWidth()/2-(width/2), (y*Math.round(tetris.getScale()))+tetris.getFrame().getContentPane().getHeight()/2-(height/2), Math.round(tetris.getScale()), Math.round(tetris.getScale()));
	}
	
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void move(int directionX) {
		x = x + directionX;
	}
	
	
	public void remove() {
		existing = false;
	}
	
	
	// getters
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
		
	}
		
	public boolean isExisting() {
		return existing;
	}
	
	public boolean isTouchingFloor() {
		if(getY()*Math.round(tetris.getScale())+Math.round(tetris.getScale()) >= tetris.getFrame().getContentPane().getHeight()) return true;
		return false;
	}
	
	
	
	
	
	
	
}
