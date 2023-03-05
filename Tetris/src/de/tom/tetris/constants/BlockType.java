package de.tom.tetris.constants;

public abstract class BlockType {
	
	
	public static final int BLOCK_1 = 0,
							BLOCK_2 = 1,
							BLOCK_3 = 2,
							BLOCK_4 = 3,
							BLOCK_5 = 4,
							BLOCK_6 = 5,
							BLOCK_7 = 6;

	
	public static int[][] segments = {
									  // Block 1 (2x2-Block)
									  {1, 1, 0, 0, 
									   1, 1, 0, 0,
									   0, 0, 0, 0,
									   0, 0, 0, 0},
									  // Block 2 (T-Block)
									  {1, 1, 1, 0,
									   0, 1, 0, 0,
									   0, 0, 0, 0,
									   0, 0, 0, 0},
									  // Block 3 (Z-Block)
									  {1, 1, 0, 0,
									   0, 1, 1, 0,
									   0, 0, 0, 0,
									   0, 0, 0, 0},
									  // Block 4 (Z-Block-mirror)
									  {0, 1, 1, 0,
									   1, 1, 0, 0,
									   0, 0, 0, 0,
									   0, 0, 0, 0},
									  // Block 5 (L-Block)
									  {1, 1, 1, 0,
									   1, 0, 0, 0,
									   0, 0, 0, 0,
									   0, 0, 0, 0},
									  // Block 6 (L-Block-mirror)
									  {1, 1, 1, 0,
									   0, 0, 1, 0,
									   0, 0, 0, 0,
									   0, 0, 0, 0},
									  // Block 7 (I-Block)
									  {0, 1, 0, 0,
									   0, 1, 0, 0,
									   0, 1, 0, 0,
									   0, 1, 0, 0}};

}
