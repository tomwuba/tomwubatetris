package de.tom.tetris;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.tom.tetris.api.SoundAPI;
import de.tom.tetris.constants.GameKeys;
import de.tom.tetris.handler.NewKeyHandler;
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
	CopyOnWriteArrayList<Block> blocks = new CopyOnWriteArrayList<>();
	
	Block nextBlock, currentBlock;
	
	boolean died, paused;
	
	
	Image segmentImages[] = new Image[7];
	
	String segmentPack = "_classic_colour";
	
	long[] keyCooldown;
	
	NewKeyHandler newKeyHandler;
	
	public File tetrisSongFile, fallSoundFile, lineSoundFile;
	
	public Clip tetrisClip;
	
	float volume = 50.0f;
	
	public boolean gameOver = true;
	
	public boolean gamePaused = true;
	
	public Font gameFont;
	
	int score = 0;
	
	JMenuItem starter = new JMenuItem("error");
	JMenuItem newGame = new JMenuItem("error");
	
	
	

	
	
	
	
	// constructor
	public Tetris() {
		tetrisSongFile = new File(Tetris.class.getResource("/sound/tetris.wav").getPath());
		fallSoundFile = new File(Tetris.class.getResource("/sound/fall.wav").getPath());
		lineSoundFile = new File(Tetris.class.getResource("/sound/line.wav").getPath());
		AudioInputStream sound;
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(Tetris.class.getResource("/fonts/font.ttf").getPath())));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		try {
			gameFont = Font.createFont(Font.TRUETYPE_FONT, new File(Tetris.class.getResource("/fonts/font.ttf").getPath()));
			gameFont = new Font(gameFont.getName(), gameFont.getStyle(),40);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		keyCooldown = new long[GameKeys.MAX_KEYS];
		setupJFrame();
		
		this.start();
		try {
			sound = AudioSystem.getAudioInputStream(tetrisSongFile);
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			tetrisClip = (Clip) AudioSystem.getLine(info);
			tetrisClip.open(sound);
			SoundAPI.setVolume(tetrisClip, 0.25f);
			
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// methods
	
	
	void setupJFrame() {
		pathToData = Tetris.class.getResource("/texture/").getPath();
		
		
		try {
			backgroundImage = ImageIO.read(Tetris.class.getResource("/texture/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jframe = new JFrame("Tetris (beta)");
		setupJMenuBar();
		try {
			jframe.setIconImage(ImageIO.read(Tetris.class.getResource("/texture/tetris.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		nextBlock = new Block(this, 19, 5);
		currentBlock = new Block(this, 7, 1);
		// Set Images:
		for(int i = 0; i < 7; i++) {
			try {
				segmentImages[i] = ImageIO.read(Tetris.class.getResource("/texture/segments" + segmentPack + "/segment_" + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		WindowPane windowPane = new WindowPane(this);
		windowPane.setPreferredSize(new Dimension(500, 600));
		newKeyHandler = new NewKeyHandler();
		jframe.addKeyListener(newKeyHandler);
		jframe.getContentPane().add(windowPane);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setLocationByPlatform(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jframe.setVisible(true);
	}
	
	
	void setupJMenuBar() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JMenuBar menubar = new JMenuBar();
		menubar.setPreferredSize(new Dimension(jframe.getWidth(), 20));
		
		JMenu settings = new JMenu("Einstellungen");
		JMenuItem graphic = new JMenuItem("Grafik...            ");
		JMenuItem difficulty = new JMenuItem("Schwierigkeit...            ");
		JMenuItem volume = new JMenuItem("Lautstärke...            ");
		
		settings.add(graphic);
		settings.add(difficulty);
		JSeparator jseperator2 = new JSeparator(JSeparator.HORIZONTAL);
		jseperator2.setBackground(Color.LIGHT_GRAY);
		jseperator2.setForeground(Color.WHITE);
		settings.add(jseperator2);
		settings.add(volume);
		
		JMenu game = new JMenu("Spiel");
		newGame = new JMenuItem(new AbstractAction("Neu                          F1") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		starter = new JMenuItem( new AbstractAction("Starten                F2") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gameOver) {
					if(!gamePaused) {
						gamePaused = true;
						starter.setText("Spiel Fortsetzen                F2");
						tetrisClip.stop();
					} else {
						gamePaused = false;
						starter.setText("Pausieren                ESC");
						tetrisClip.loop(Clip.LOOP_CONTINUOUSLY);
						tetrisClip.start();
					}
					
				} else if(gameOver) {
					gameOver = false;
					gamePaused = false;
					tetrisClip.loop(Clip.LOOP_CONTINUOUSLY);
					tetrisClip.start();
					starter.setText("Pausieren                ESC");
				}
				
			}
		});
		
	
		game.add(newGame);
		
		JSeparator jseperator = new JSeparator(JSeparator.HORIZONTAL);
		jseperator.setBackground(Color.LIGHT_GRAY);
		jseperator.setForeground(Color.WHITE);
		game.add(jseperator);
		game.add(starter);
		
		game.setBackground(Color.BLACK);
		menubar.add(game);
		menubar.add(settings);
		
		jframe.setJMenuBar(menubar);
	}
	
	public void resetGame() {
		
		
		died = false;
		nextBlock = new Block(this, 19, 5);
		currentBlock = new Block(this, 7, 1);
		gamePaused = false;
		gameOver = false;
		blocks.clear();
		score = 0;
		tetrisClip.loop(Clip.LOOP_CONTINUOUSLY);
		tetrisClip.start();
		starter.setText("Pausieren                ESC");
	}
	
	
	
	@Override
	public void run() {
		
		long timeInFuture = 0L;	
		while(!isInterrupted()) {
			
			getFrame().getContentPane().repaint();

			handleKeys();
			
			if(timeInFuture <= System.currentTimeMillis()) {
				timeInFuture = System.currentTimeMillis() + getGameSpeed();
				if(!isDead() && !isPaused()) updateGameInSpeed();
			} else {
				
			}
			
			

		}
	}
	
	
	void updateGameInSpeed() {
		if(gameOver || gamePaused) {
			
			return;
		}
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
	
	
	
	public void handleKeys() {
		if(gamePaused && newKeyHandler.isKeyDown(112) && !(gameOver && !died)) {
			resetGame();
		}
		
		if(newKeyHandler.isKeyDown(113)) {
			if(gamePaused && !gameOver) {
				gamePaused = false;
				tetrisClip.loop(Clip.LOOP_CONTINUOUSLY);
				tetrisClip.start();
				starter.setText("Pausieren                ESC");
					
			} else if(gameOver) {
				gameOver = false;
				gamePaused = false;
				tetrisClip.loop(Clip.LOOP_CONTINUOUSLY);
				tetrisClip.start();
				starter.setText("Spiel Fortsetzen                F2");
			}
		}
			
		if(newKeyHandler.isKeyDown(27)) {
			if(!gamePaused && !gameOver) {
				gamePaused = true;
				tetrisClip.stop();
			} 
		}
		
		if(gamePaused) return;
		
		if(keyCooldown[GameKeys.COOLDOWN_A] > System.currentTimeMillis() && !newKeyHandler.isKeyDown(GameKeys.A_KEY)) keyCooldown[GameKeys.COOLDOWN_A] = 0;
		if(keyCooldown[GameKeys.COOLDOWN_D] > System.currentTimeMillis() && !newKeyHandler.isKeyDown(GameKeys.D_KEY)) keyCooldown[GameKeys.COOLDOWN_D] = 0;

		
		if(newKeyHandler.isKeyDown(GameKeys.A_KEY) && !newKeyHandler.isKeyDown(GameKeys.D_KEY) &&keyCooldown[GameKeys.COOLDOWN_A] <= System.currentTimeMillis()) {
			getCurrentBlock().rotate(-1);
			keyCooldown[GameKeys.COOLDOWN_A] = System.currentTimeMillis() + 400L;
		}
		if(newKeyHandler.isKeyDown(GameKeys.D_KEY) && !newKeyHandler.isKeyDown(GameKeys.A_KEY) &&keyCooldown[GameKeys.COOLDOWN_D] <= System.currentTimeMillis()) {
			getCurrentBlock().rotate(1);
			keyCooldown[GameKeys.COOLDOWN_D] = System.currentTimeMillis() + 400L;
		}
		if(newKeyHandler.isKeyDown(GameKeys.S_KEY) &&keyCooldown[GameKeys.COOLDOWN_S] <= System.currentTimeMillis()) {
			updateCurrentBlock();
			keyCooldown[GameKeys.COOLDOWN_S] = System.currentTimeMillis() + Math.round(getGameSpeed()/10.0f);
		}
		if(newKeyHandler.isKeyDown(GameKeys.LEFT_KEY) && !newKeyHandler.isKeyDown(GameKeys.RIGHT_KEY) &&keyCooldown[GameKeys.COOLDOWN_LEFT] <= System.currentTimeMillis()) {
			moveCurrentBlock(-1);
			keyCooldown[GameKeys.COOLDOWN_LEFT] = System.currentTimeMillis() + 100L;
		}
		if(newKeyHandler.isKeyDown(GameKeys.RIGHT_KEY) && !newKeyHandler.isKeyDown(GameKeys.LEFT_KEY) &&keyCooldown[GameKeys.COOLDOWN_RIGHT] <= System.currentTimeMillis()) {
			moveCurrentBlock(1);
			keyCooldown[GameKeys.COOLDOWN_RIGHT] = System.currentTimeMillis() + 100L;
		}
		
		
	}
	
	
	
	public void updateCurrentBlock() {
		
			
		if(currentBlock.isTouchingFloor() || currentBlock.willBlockTouchOtherBlock(currentBlock.getX(), currentBlock.getY()+1)) {
			// Block is on the floor!
			
			addBlock(currentBlock);
			createNextBlock();
			new SoundAPI().playSound(fallSoundFile, 25);
			return;
		}
		if(!newKeyHandler.isKeyDown(GameKeys.D_KEY)) currentBlock.setLocation(currentBlock.getX(), currentBlock.getY()+1);
	}
	
	public void moveCurrentBlock(int direction) {
		currentBlock.setLocation(currentBlock.getX()+direction, currentBlock.getY());
	}
	
	public void faster() {
		gameSpeed++;
	}
	
	public void addBlock(Block block) {
		blocks.add(block);
		// adds a block
	}
	
	public void createNextBlock() {
		if(currentBlock.getY() < 3) {
			died = true;
			paused = true;
			return;
		}
		
		nextBlock.setLocation(7, 1);
		currentBlock = nextBlock;
		nextBlock = new Block(this, 19, 5);
	}
	
	public void eraseLine(int lineNumber) {
		Iterator<Block> it = blocks.iterator();
		while(it.hasNext()) {
			Block block = it.next();
			if(!block.equals(getCurrentBlock()) && block.isExisting()) {
				for(Segment segment : block.getSegments()) {
					if(segment.getY() == lineNumber) segment.remove();
				}
			}
			
			
		}
		new SoundAPI().playSound(lineSoundFile, 25);
		score++;
		
		
	}
	
	public void moveOtherLines(int lineNumber) {
		Iterator<Block> it = blocks.iterator();
		while(it.hasNext()) {
			Block block = it.next();
			if(!block.equals(getCurrentBlock()) && block.isExisting()) {
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
		Iterator<Block> it = blocks.iterator();
		while(it.hasNext()) {
			Block block = it.next();
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
	
	
	public boolean isDead() {
		return died;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public Image[] getSegmentImages() {
		return segmentImages;
	}
	
	public int getScore() {
		return score;
	}
	
	
	
	
	
	
	
	
	

}
