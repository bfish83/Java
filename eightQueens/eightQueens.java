package eightQueens;

import java.util.Random;


public class eightQueens  {

	private char board[][] = new char[8][8];
	private Random rand = new Random();
	private int numQueens, numTiles;
	private long eTime, bTime, hTime;
	private int eCount, bCount, hCount;

	

	public eightQueens()
	{
		eTime = 0;
		bTime = 0;
		hTime = 0;
		eCount = 0;
		bCount = 0;
		hCount = 0;
		reset();
	}

	public void reset()
	{
		numQueens = 0;
		numTiles = 0;
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				board[i][j] = '_';
			}
		}
		showBoard();
	}

	public char[][] getBoard() 
	{ 
		return board;
	} 
	
	
	public void showBoard()
	{
		System.out.println("\nChess Board");
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				System.out.print(board[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	public void changeTile(int row, int col)
	{
		boolean flag = checkX(row,col);
		if (flag)
		{
			if (board[row][col] == '_')
			{
				board[row][col] = 'Q';
				numQueens++;
				numTiles++;
				setX(row,col);	
			}

			else if (board[row][col] == 'Q')
			{
				board[row][col] = '_';
				numTiles--;
				noX();
				findQueens();
			}

		} else {
			return;
		}
	}

	
	public void exhaustive()
	{
		long start = System.nanoTime();
		while(numQueens < 8)
		{
			if (numTiles < 64)
			{
				changeTile(rand.nextInt(8), rand.nextInt(8));
			} else {
				reset();
			}
		}
		long stop = System.nanoTime();
		long timer = (stop - start);
		System.out.println("Exhaustive search took " + timer + " nanoseconds");
		eTime += timer;
		eCount++;
	}


	public void bruteForce()
	{
		long start = System.nanoTime();
		while (numQueens < 8)
		{
			reset();
			board[0][rand.nextInt(8)] = 'Q';
			board[1][rand.nextInt(8)] = 'Q';
			board[2][rand.nextInt(8)] = 'Q';
			board[3][rand.nextInt(8)] = 'Q';
			board[4][rand.nextInt(8)] = 'Q';
			board[5][rand.nextInt(8)] = 'Q';
			board[6][rand.nextInt(8)] = 'Q';
			board[7][rand.nextInt(8)] = 'Q';
			findQueens();
		}
		long stop = System.nanoTime();
		long timer = (stop - start);
		System.out.println("Brute force search took " + timer + " nanoseconds");
		bTime += timer;
		bCount++;
	}

	public void heuristic()
	{
		int boardNum[][] = new int[8][8];
		for (int row = 0; row < 8; row++)
		{
			for (int col = 0; col < 8; col++)
			{
				if ( row == 0 || row == 7 || col == 0 || col == 7 )
					boardNum[row][col] = 22;
				else if ( ((row == 1 || row == 6) && (col >= 1 && col <= 6)) 
						|| ((row > 1 && row < 6) && (col == 1 || col == 6)) )
					boardNum[row][col] = 24;
				else if ( ((row == 2 || row == 5) && (col >= 2 && col <= 5))
						|| ((row > 2 && row < 5) && (col == 2 || col == 5)) )
					boardNum[row][col] = 26;
				else
					boardNum[row][col] = 28;
			}
		}

		long start = System.nanoTime();

		while(numQueens < 8)
		{
			if (numTiles < 64)
			{
				int last = 29;
				for (int row = 0; row < 8; row++)
				{
					for (int col = 0; col < 8; col++)
					{
						int var = rand.nextInt(8);
						if (boardNum[row][var] < last)
						{
							changeTile(row, var);
							last = boardNum[row][var];
						}
					}
					last = 29;
				}				
			} else {
				reset();
			}
		}

		long stop = System.nanoTime();
		long timer = (stop - start);

		System.out.println("Heuristic search took " + timer + " nanoseconds");

		hTime += timer;
		hCount++;
	}

	public void getTimes()
	{
		long temp = 0;
		if (eCount > 0)
		{
			temp = eTime / eCount;
			eTime = temp;
			System.out.println("AVG Exhaustive search time:\t" + eTime + " nanoseconds");
		}
		if (bCount > 0)
		{
			temp = bTime / bCount;
			bTime = temp;
			System.out.println("AVG Brute-force search time:\t" + bTime + " nanoseconds");
		}
		if (hCount > 0)
		{
			temp = hTime / hCount;
			hTime = temp;
			System.out.println("AVG Heuristic search time:\t" + hTime + " nanoseconds");
		}

	}

	

	public boolean checkX(int row, int col)
	{
		if (board[row][col] == 'X')
			return false;
		else
			return true;
	}

	private void setX(int row, int col)
	{
		int rowCopy = row;
		int colCopy = col;


		for (int i = 0; i < 8; i++)
		{
			if (i != row)
			{
				if (board[i][col] != 'X')
					numTiles++;
				board[i][col] = 'X';
			}
		}

		for (int j = 0; j < 8; j++)
		{
			if (j != col)
			{
				if (board[row][j] != 'X')
					numTiles++;
				board[row][j] = 'X';
			}
		}

		row--;
		col--;
		while ((row >= 0) && (col >= 0))
		{
			if (board[row][col] != 'X')
				numTiles++;
			board[row][col] = 'X';
			row--;
			col--;
		}
		row = rowCopy;
		col = colCopy;

		row--;
		col++;
		while ((row >= 0) && (col < 8))
		{
			if (board[row][col] != 'X')
				numTiles++;
			board[row][col] = 'X';
			row--;
			col++;
		}
		row = rowCopy;
		col = colCopy;

		row++;
		col++;
		while ((row < 8) && (col < 8))
		{
			if (board[row][col] != 'X')
				numTiles++;
			board[row][col] = 'X';
			row++;
			col++;
		}
		row = rowCopy;
		col = colCopy;

		row++;
		col--;
		while ((row < 8) && (col >= 0))
		{
			if (board[row][col] != 'X')
				numTiles++;
			board[row][col] = 'X';
			row++;
			col--;
		}		
	}
	
	private void findQueens()
	{
		numQueens = 0;
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (board[i][j] == 'Q')
				{
					numQueens++;
					setX(i,j);
				}
			}
		}
	}
	
	private void noX()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (board[i][j] == 'X')
				{
					board[i][j] = '_';
					numTiles--;
				}
			}
		}
	}
}