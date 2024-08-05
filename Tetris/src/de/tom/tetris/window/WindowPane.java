package de.tom.tetris.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import de.tom.tetris.Tetris;
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
		if(tetris.gameOver && !tetris.isDead()) {
			// Game hasn't started yet (technically also over)
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.RED);
			tetris.gameFont = new Font(tetris.gameFont.getName(), Font.PLAIN, 50);
			g.setFont(tetris.gameFont);
			g.drawString("Tetris", (this.getWidth()/2) - g.getFontMetrics().stringWidth("Tetris")/2, this.getHeight()/4);
			tetris.gameFont = new Font(tetris.gameFont.getName(), Font.PLAIN, 15);
			g.setFont(tetris.gameFont);
			g.drawString("Press F2 to start the game.", (this.getWidth()/2) - g.getFontMetrics().stringWidth("Press F2 to start the game.")/2, this.getHeight()/3);
			return;
		}
		Iterator<Block> it = tetris.getBlocks().iterator();
		while(it.hasNext()) {
			Block block = it.next();
			if(block.isExisting()) {
				block.draw(g);
			} 

		}
		
		tetris.getCurrentBlock().draw(g);
		tetris.getNextBlock().draw(g);
		if(tetris.gamePaused) {
			
			
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.RED);
			tetris.gameFont = new Font(tetris.gameFont.getName(), Font.PLAIN, 50);
			g.setFont(tetris.gameFont);
			if(tetris.isDead()) {
				// Game is Over and the character died:
				g.drawString("You died", (this.getWidth()/2) - g.getFontMetrics().stringWidth("You died")/2, this.getHeight()/4);
				tetris.gameFont = new Font(tetris.gameFont.getName(), Font.PLAIN, 15);
				g.setFont(tetris.gameFont);
				g.drawString("Press F2 to start the game.", (this.getWidth()/2) - g.getFontMetrics().stringWidth("Press F2 to start the game.")/2, this.getHeight()/3);
				g.setColor(Color.WHITE);
				g.drawString("Score: " + tetris.getScore(), (this.getWidth()/2) - g.getFontMetrics().stringWidth("Press F2 to start the game.")/2, this.getHeight()/3);
				
				return;
			}
			if(!tetris.gameOver) {
				// Game is paused but not over:
				g.setColor(Color.RED);
				tetris.gameFont = new Font(tetris.gameFont.getName(), Font.PLAIN, 50);
				g.setFont(tetris.gameFont);
				g.setColor(Color.YELLOW);
				g.drawString("Pause", (this.getWidth()/2) - g.getFontMetrics().stringWidth("Pause")/2, this.getHeight()/4);
				tetris.gameFont = new Font(tetris.gameFont.getName(), Font.PLAIN, 15);
				g.setFont(tetris.gameFont);
				g.drawString("Press F2 to resume.", (this.getWidth()/2) - g.getFontMetrics().stringWidth("Press F2 to resume.")/2, this.getHeight()/3);
			}
			
			
			
		}
		
		
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
