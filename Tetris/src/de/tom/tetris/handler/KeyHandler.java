package de.tom.tetris.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.tom.tetris.Tetris;
import de.tom.tetris.window.WindowPane;

public class KeyHandler implements KeyListener{
	
	public boolean leftKeyDown = false;
	
	public boolean rightKeyDown = false;
	
	public boolean aKeyDown = false;
	
	public boolean dKeyDown = false;
	
	public boolean downKeyDown = false;
	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 37) {
			leftKeyDown = true;
			
		}
		if(e.getKeyCode() == 39) {
			rightKeyDown = true;
		}
		
		if(e.getKeyCode() == 65) {
			aKeyDown = true;
		}
		if(e.getKeyCode() == 68) {
			dKeyDown = true;
		}
		if(e.getKeyCode() == 40) {
			downKeyDown = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 37) {
			leftKeyDown = false;
		} else if(e.getKeyCode() == 39) {
			rightKeyDown = false;
		} else if(e.getKeyCode() == 65) {
			aKeyDown = false;
		} else if(e.getKeyCode() == 68) {
			dKeyDown = false;
		} else if(e.getKeyCode() == 40) {
			downKeyDown = false;
		}
		
			
		
	}
	
	
	

}
