package connect5;

import java.io.Serializable;

/**
 * 
 * @author Uamhan Mac Fhearghusa
 *	
 *	Game board class holds the state of the game and contains functionality to initialise a new board or to print the current board	
 *
 *	@param Width - width of the game board
 *	@param Height - height of the game board
 *	@param board - 2d array representing the game board
 *
 *	
 */

public class C5GameBoard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private  int WIDTH = 9;
	private int HEIGHT = 6;
	
	private  char[][] board = new char[WIDTH][HEIGHT];
	
	/*
	 * fills the game board with empty values.
	 */
	public void createBoard() {
		for (int h = 0; HEIGHT > h; h += 1)
		{
			for (int w = 0; WIDTH > w; w += 1)
			{
                board[w][h] = '.';
            }
        }
    }
	
	
	/*
	 * prints the game board
	 */
	public void printBoard() {
		for (int h = 0; HEIGHT > h; h += 1)
		{
			for (int w = 0; WIDTH > w; w += 1)
            {
                System.out.print("["+board[w][h]+"]");
            }
			System.out.println();
        }
		System.out.println("---------------------------");
		System.out.println("[1][2][3][4][5][6][7][8][9]");
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}
	
	
	
}
