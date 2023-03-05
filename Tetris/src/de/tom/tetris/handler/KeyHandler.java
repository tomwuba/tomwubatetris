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
	
	KeyThread leftKeyThread;
	KeyThread rightKeyThread;
	KeyThread aKeyThread;
	KeyThread dKeyThread;
	KeyThread downKeyThread;
	
	
	
	private Tetris tetris;
	
	public KeyHandler(Tetris tetris) {
		this.tetris = tetris;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 37 && !leftKeyDown) {
			leftKeyDown = true;
			
			leftKeyThread = new KeyThread(37);
			leftKeyThread.start();
			
		}
		if(e.getKeyCode() == 39 && !rightKeyDown) {
			rightKeyDown = true;
			rightKeyThread = new KeyThread(39);
			rightKeyThread.start();

			
		}
		
		if(e.getKeyCode() == 65 && !aKeyDown) {
			aKeyDown = true;
			aKeyThread = new KeyThread(65);
			aKeyThread.start();
			
		}
		if(e.getKeyCode() == 68 && !dKeyDown) {
			dKeyDown = true;
			dKeyThread = new KeyThread(68);
			dKeyThread.start();
			
		}
		if(e.getKeyCode() == 40 && !downKeyDown) {
			downKeyDown = true;
			downKeyThread = new KeyThread(40);
			downKeyThread.start();
			
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 37) {
			leftKeyDown = false;
			leftKeyThread.interrupt();
			
		} else if(e.getKeyCode() == 39) {
			rightKeyDown = false;
			rightKeyThread.interrupt();
			
		} else if(e.getKeyCode() == 65) {
			aKeyDown = false;
			aKeyThread.interrupt();
		} else if(e.getKeyCode() == 68) {
			dKeyDown = false;
			dKeyThread.interrupt();
		} else if(e.getKeyCode() == 40) {
			downKeyDown = false;
			downKeyThread.interrupt();
		}
		
			
		
	}
	
	class KeyThread extends Thread {
		
		int keyCode;
		
		
		public KeyThread(int keyCode) {
			this.keyCode = keyCode;
		}
		
		@Override
		public void run() {
			try {
				switch(keyCode) {
				case 37:
					while(leftKeyDown) {
						if(!rightKeyDown) tetris.moveCurrentBlock(-1);
						sleep(100);
					}
					break;
				case 39:
					while(rightKeyDown) {
						if(!leftKeyDown) tetris.moveCurrentBlock(1);
						sleep(100);
					}
					break;
					
				case 65:
					while(aKeyDown) {
						if(!dKeyDown) tetris.getCurrentBlock().rotate(-1);
						sleep(400);
					}
					break;
				case 68:
					while(dKeyDown) {
						
						if(!aKeyDown) tetris.getCurrentBlock().rotate(1);
						sleep(400);
						
					}
					break;
				}
				
				
				
			} catch (InterruptedException e) {
				
			}
			
		}
	}
	
	
	
	

}
