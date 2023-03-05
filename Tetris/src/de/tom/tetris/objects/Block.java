package de.tom.tetris.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;


import de.tom.tetris.Tetris;
import de.tom.tetris.constants.BlockRotation;
import de.tom.tetris.constants.BlockType;
import de.tom.tetris.constants.SegmentType;

public class Block {
	
	// attributes
	Tetris tetris;
	int middleX, middleY, blockType, rotation;
	boolean existing;
	
	ArrayList<Segment> segments;
	
	// constructor
	public Block(Tetris tetris, int middleX, int middleY) {
		this.tetris = tetris;
		existing = true;
		this.middleX = middleX;
		this.middleY = middleY;
		rotation = BlockRotation.UP;
		blockType = new Random().nextInt(7);
		setupSegments();
		
		
	}
	
	// methods:
	
	public void draw(Graphics g) {
		// TODO: draw all segments.
		for(Segment segment : segments) {
			segment.draw(g);
		}

	}
	
	public void rotate(int direction) {
		
		ArrayList<Segment> newSegments = new ArrayList<>();
		if(blockType != BlockType.BLOCK_7 && blockType != BlockType.BLOCK_1) {
			for(Segment oldSegment : segments) {
				Segment newSegment = oldSegment;
					
				// Left part of block:
				if(newSegment.getX() < middleX) {
					// upper left part:
					if(newSegment.getY() < middleY) {
						if(direction > 0) newSegment.setX(middleX+1); else newSegment.setY(middleY+1);

					// bottom left part:
					} else if(newSegment.getY() > middleY) {
						if(direction > 0) newSegment.setY(middleY-1); else newSegment.setX(middleX+1);
						
					// middle left part:
					} else if(newSegment.getY() == middleY) {
						newSegment.setY(middleY-(1*direction));
						
						newSegment.setX(middleX);							
					}	
				// Middle part of block:
				} else if(newSegment.getX() == middleX) {
					// upper mid of block:
					if(newSegment.getY() < middleY) {
						newSegment.setX(middleX+(1*direction));
						
						newSegment.setY(middleY);
					// bottom mid of block:
					} else if(newSegment.getY() > middleY) {
						newSegment.setX(middleX-(1*direction));
						
						newSegment.setY(middleY);
					}
				// Right part of block:
				} else if(newSegment.getX() > middleX) {
					// upper right part:
					if(newSegment.getY() < middleY) {
						if(direction > 0) newSegment.setY(middleY+1); else newSegment.setX(middleX-1);
					// middle right part:
					} else if(newSegment.getY() == middleY) {
						newSegment.setY(middleY+(1*direction));
						newSegment.setX(middleX);
					// bottom right part:	
					} else if(newSegment.getY() > middleY) {
						if(direction > 0) newSegment.setX(middleX-1); else newSegment.setY(middleY-1);
					}
						
				}
					
				newSegments.add(newSegment);
					
			}
			//segments.clear();
				
			// TODO: Check if one of the segments is on an already existing segment! (NOT from the same block tho.)
			segments = newSegments;
				
			return;
		} else if(blockType != BlockType.BLOCK_1){
			// 4*1 Block
			for(Segment oldSegment : segments) {
				Segment newSegment = oldSegment;
				if(newSegment.getX() < middleX) {
					newSegment.setX(middleX);
					newSegment.setY(middleY-1);						
				} else if(newSegment.getX() == middleX) {
					if(newSegment.getY() < middleY) {
						newSegment.setX(middleX-1);
						newSegment.setY(middleY);
					} else if(newSegment.getY() == middleY+1) {
						newSegment.setX(middleX+1);
						newSegment.setY(middleY);
					} else if(newSegment.getY() == middleY+2) {
						newSegment.setX(middleX+2);
						newSegment.setY(middleY);
					}
				} else if(newSegment.getX() == middleX+1) {
					newSegment.setX(middleX);
					newSegment.setY(middleY+1);
				} else if(newSegment.getX() == middleX+2) {
					newSegment.setX(middleX);
					newSegment.setY(middleY+2);
				}
					
				newSegments.add(newSegment);
			}
			segments = newSegments;
		}
		
	}
	
	public void setupSegments() {
		segments = new ArrayList<>();
		for(int i = 0; i < 16; i++) {
			if(BlockType.segments[blockType][i] == 1) {
				segments.add(new Segment(tetris, middleX+((i%4))-1, middleY+(i*4/16)-1, blockType));
			}

		}
		// TODO: Set segments relative to blockType
	}
	
	
	public void setLocation(int middleX, int middleY) {
		for(Segment segment : segments) {
			
			segment.setX(segment.getX()+(middleX-this.middleX));
			segment.setY(segment.getY()+(middleY-this.middleY));

		}
		this.middleX = middleX;
		this.middleY = middleY;
	}
	
	public boolean willBlockTouchOtherBlock(int middleX, int middleY) {
		for(Segment segment : segments) {
			if(tetris.isOtherSegmentOnChords(this, segment.getX()+(middleX-this.middleX), segment.getY()+(middleY-this.middleY))) return true;
		}
		return false;
	}
	
	public int getX() {
		return middleX;
	}
	
	public int getY() {
		return middleY;
	}
	
	// true if any segment of this block is on a specific position.
	public boolean hasSegmentOnPosition(int x, int y) {
		for(Segment segment : segments) {
			if(segment.getX() == x && segment.getY() == y) return true;
		}
		return false;
	}
	
	public boolean isTouchingFloor() {
		for(Segment segment : segments) {
			if(segment.isTouchingFloor()) return true;
		}
		return false;
	}
	
	public void move(int directionX) {
		// TODO: check if outside border || overlapping with other segments!
		middleX = middleX + directionX;
		for(Segment segment : segments) {
			segment.move(directionX);
			// TODO: check!
		}
	}
	
	
	
}
