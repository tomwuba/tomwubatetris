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
		g.drawImage(tetris.getSegmentImages()[segmentType], Math.round((x*tetris.getScale())+tetris.getFrame().getContentPane().getWidth()/2-(width/2)), Math.round(y*tetris.getScale()+tetris.getFrame().getContentPane().getHeight()/2-(height/2)), Math.round(tetris.getScale()), Math.round(tetris.getScale()), null);
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
		if(getY() >= 29) return true;
		return false;
	}
	
	public boolean isOutSideMap() {
		if(getX() < 0 || getX() > 14 || getY() > 29) return true;
		return false;
	}
	
	
	
	
	
	
	
}
