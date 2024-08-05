package de.tom.tetris.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NewKeyHandler implements KeyListener{
	
	boolean[] keysDown;
	
	public NewKeyHandler() {
		keysDown = new boolean[1250];
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getExtendedKeyCode() > keysDown.length) return;
		keysDown[e.getExtendedKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getExtendedKeyCode() > keysDown.length) return;
		keysDown[e.getExtendedKeyCode()] = false;
		
		
	}
	
	
	public boolean isKeyDown(int keyID) {
		if(keyID > keysDown.length) return false;
		return keysDown[keyID];
	}

}
