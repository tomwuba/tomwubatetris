package de.tom.tetris;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import de.tom.tetris.handler.KeyHandler;
import de.tom.tetris.objects.Block;
import de.tom.tetris.objects.Segment;
import de.tom.tetris.window.WindowPane;

public class Tetris extends Thread{
	
	// game-attributes
	// nice
	
	private JFrame jframe;
	
	
	int gameSpeed = 1;
	
	String pathToData;
	Image backgroundImage;
	ArrayList<Block> blocks = new ArrayList<>();
	
	Block nextBlock, currentBlock;
	
	boolean died, paused;
	
	

	
	
	
	
	// constructor
	public Tetris() {
		setupJFrame();
		died = false;
		paused = false;
		this.start();
		
	}
	
	// methods
	
	
	void setupJFrame() {
		try {
			pathToData = new File(URLDecoder.decode(Tetris.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/..", "UTF-8")).getParentFile().getParentFile().getAbsolutePath();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		backgroundImage = Toolkit.getDefaultToolkit().getImage(getPathToData() + "/data/background.png");
		jframe = new JFrame("Tetris");
		jframe.setIconImage(new ImageIcon(pathToData + "/data/tetris.png").getImage());
		nextBlock = new Block(this, 19, 5);
		currentBlock = new Block(this, 7, 1);
		
		WindowPane windowPane = new WindowPane(this);
		windowPane.setPreferredSize(new Dimension(500, 600));
		
		jframe.addKeyListener(new KeyHandler(this));
		jframe.getContentPane().add(windowPane);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setLocationByPlatform(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jframe.setVisible(true);
	}
	
	
	
	@Override
	public void run() {
		
		long timeInFuture = 0L;	
		while(!isInterrupted()) {
			getFrame().getContentPane().repaint();
			
			if(timeInFuture <= System.currentTimeMillis()) {
				timeInFuture = System.currentTimeMillis() + getGameSpeed();
				updateGameInSpeed();
			}
			
			

		}
	}
	
	
	void updateGameInSpeed() {
		updateCurrentBlock();
		boolean gotLinesRemoved = false;
		for(int i = 0; i < 30; i++) {
			if(isLineFull(i)) {
				eraseLine(i);
				moveOtherLines(i);
				gotLinesRemoved = true;
			}
		}
		// TODO: Play sound
		if(gotLinesRemoved);
	}
	
	
	
	public void updateCurrentBlock() {
		
			
		if(currentBlock.isTouchingFloor() || currentBlock.willBlockTouchOtherBlock(currentBlock.getX(), currentBlock.getY()+1)) {
			// Block is on the floor!
			
			addBlock(currentBlock);
			createNextBlock();
			
			return;
		}
		currentBlock.setLocation(currentBlock.getX(), currentBlock.getY()+1);
	}
	
	public void moveCurrentBlock(int direction) {
		currentBlock.setLocation(currentBlock.getX()+direction, currentBlock.getY());
	}
	
	public void faster() {
		gameSpeed++;
	}
	
	public void addBlock(Block block) {
		blocks.add(block);
	}
	
	public void createNextBlock() {
		nextBlock.setLocation(7, 1);
		currentBlock = nextBlock;
		nextBlock = new Block(this, 19, 5);
	}
	
	public void eraseLine(int lineNumber) {
		for(Block block : blocks) {
			if(!block.equals(getCurrentBlock())) {
				for(Segment segment : block.getSegments()) {
					if(segment.getY() == lineNumber) segment.remove();
				}
			}
			
			
		}
		
		
	}
	
	public void moveOtherLines(int lineNumber) {
		for(Block block : blocks) {
			if(!block.equals(getCurrentBlock())) {
				block.fall(lineNumber);
			}

		}
	}
	
	// main-method
	public static void main(String[] args) {
		new Tetris();
	}
	
	
	// getters
	
	public Image getBackGroundImage() {
		return backgroundImage;
	}
	public float getScale() {
		float width = ((float) 500*((float)jframe.getContentPane().getHeight()/(float)600));
		if(width > jframe.getContentPane().getWidth()) width = jframe.getContentPane().getWidth();


		return 20.0f*((float)width/500.0f);
	}
	
	public JFrame getFrame() {
		return jframe;
	}
	
	public String getPathToData() {
		return pathToData;
	}
	
	public long getGameSpeed() {
		if(gameSpeed*100L >= 600L) return 30L;
		return 600L-gameSpeed*100L;
	}
	
	public boolean isOtherSegmentOnChords(Block checkedFor, int x, int y) {
		for(Block block : blocks) {
			if(!block.equals(checkedFor) && block.hasSegmentOnPosition(x, y) && block.getSegmentOnChords(x, y).isExisting()) return true;
		}
		return false;
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}
	
	public Block getNextBlock() {
		return nextBlock;
	}
	
	public Block getCurrentBlock() {
		return currentBlock;
	}
	
	
	
	
	public boolean isLineFull(int lineNumber) {
		int numbSegmentsInLine = 0;
		for(int i = 0; i < 15; i++) {
			
			if(isOtherSegmentOnChords(getCurrentBlock(), i, lineNumber)) numbSegmentsInLine++;
			
			
		}
		if(numbSegmentsInLine > 14) return true;
		return false;
	}
	
	
	boolean isDead() {
		return died;
	}
	
	boolean isPaused() {
		return paused;
	}
	
	
	
	
	
	
	
	
	

}
