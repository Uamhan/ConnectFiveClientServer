package connect5;

import java.io.Serializable;

/**
 * 
 * @author Uamhan Mac Fhearghusa
 * 
 * Message class stores all data to be sent back and forward between the client and server
 * 
 * 	@param board - stores game board.
 * 	@param yourTurn - bool representing if its currently the players turn
 * 	@param text - string message to be sent
 * 	@param turnChoice - the user choice when its their turn
 *
 */

public class C5Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private C5GameBoard board;
	private boolean yourTurn;
	private String text;
	private int turnChoice;
	
	public C5GameBoard getBoard() {
		return board;
	}
	public void setBoard(C5GameBoard board) {
		this.board = board;
	}
	public boolean isYourTurn() {
		return yourTurn;
	}
	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getTurnChoice() {
		return turnChoice;
	}
	public void setTurnChoice(int turnChoice) {
		this.turnChoice = turnChoice;
	}
	
}
