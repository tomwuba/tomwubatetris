package de.tom.tetris.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.tom.tetris.Tetris;
import de.tom.tetris.handler.KeyHandler;
import de.tom.tetris.objects.Block;

@SuppressWarnings("serial")
public class WindowPane extends JPanel{
	
	// attributes
	
	Tetris tetris;
	


	
	long timeInFuture = 0L;
	
	// constructor
	public WindowPane(Tetris tetris) {
		this.tetris = tetris;
		
	}
	
	
	// methods
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		fillBackground(g);
		
		for(Block block : tetris.getBlocks()) {
			block.draw(g);
		}
		tetris.getCurrentBlock().draw(g);
		
		
	}
	
	
	
	void fillBackground(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, tetris.getFrame().getWidth(), tetris.getFrame().getHeight());
		int width = (int) ((float) 500*((float)this.getHeight()/(float)600));
		if(width > this.getWidth()) width = this.getWidth();
		int height = (int) ((float) 600*((float)this.getWidth()/(float)500));
		if(height > this.getHeight()) height = this.getHeight();
		g.setColor(Color.GRAY);
		g.fillRect(this.getWidth()/2-(width/2), this.getHeight()/2-(height/2), width, height);
		g.drawImage(tetris.getBackGroundImage(), this.getWidth()/2-(width/2), this.getHeight()/2-(height/2), width, height, null);

	}

}
