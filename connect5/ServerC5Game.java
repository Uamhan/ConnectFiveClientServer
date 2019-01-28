package connect5;


import java.io.IOException;

/** 
 * 
 * @author Uamhan Mac Fhearghusa
 *	
 *	@param board - the current game board
 *	@param player1 - the player object representing the x's and the first to connect to the server
 *	@param player2 - seconf player to connect to the server representing o's
 *	@param currentPlayer - the player who's turn it currently is
 *	@param waitingPlayer - the player who's waiting for their turn
 */

public class ServerC5Game {

	private C5GameBoard board;
	private ServerC5Player player1;
	private ServerC5Player player2;
	private ServerC5Player currentPlayer;
	private ServerC5Player waitingPlayer;
	
	/*
	 * start method (Initialisation method)
	 * sends messages to users saying who has joing the game and that the game has started
	 * instantiates the board and fills it with blanks
	 * sets player tokens
	 * sets players turns
	 * calls main game loop
	 */
	public void start(ServerC5Player player1,ServerC5Player player2) throws IOException, ClassNotFoundException {
		
		player1.sendMessageToClient(player2.getName() + " Has joined game starting...");
		player2.sendMessageToClient("joined " + player1.getName() + ". game starting...");
	
		
		board = new C5GameBoard();
		board.createBoard();
		board.printBoard();
		player1.setToken('x');
		player2.setToken('o');
		this.player1 = player1;
		this.player2 = player2;
		currentPlayer = player1;
		waitingPlayer = player2;
		gameLoop();
	}
	
	/*
	 * main game loop continues as long as both players are still connected to the server
	 * sends game state to clients 
	 * Notify the player who's turn it is to make a move and the other to wait
	 * swaps currentPlayer with waitingPlayer and vice versa
	 */
	public void gameLoop() throws IOException, ClassNotFoundException
	{
		while(!player1.getConnection().isClosed()&&!player2.getConnection().isClosed()) {
				
				currentPlayer.sendGameState(board);
				waitingPlayer.sendGameState(board);
			
				//currentPlayer.setInput(new BufferedReader(new InputStreamReader(currentPlayer.getConnection().getInputStream())));
				
				currentPlayer.sendMessageToClient("It's your turn "+currentPlayer.getName()+". Please Enter a column (1-9)");
				waitingPlayer.sendMessageToClient("It's " + currentPlayer.getName() + "'s turn. Please Wait.");
				
				makeMove(currentPlayer);

				if(currentPlayer.equals(player1)) {
					currentPlayer=player2;
					waitingPlayer=player1;
				}else {
					currentPlayer=player1;
					waitingPlayer=player2;
				}
		
		}
		
	}
	/*
	 * makeMove method
	 * takes in player object
	 * sends call to player client to give move input
	 * places the token in the column the player has selected
	 * calls checkWin method
	 */
	public void makeMove(ServerC5Player player) throws IOException, ClassNotFoundException {
		
		int column = player.getPlayerMove()-1; //to account for user input being 1-9 and array spots being 0-8
		for(int i = board.getHeight()-1;i>=0;i--)
		{
			if(board.getBoard()[column][i] == '.') {
				board.getBoard()[column][i] = player.getToken();
				checkWin(player.getToken());
			}
		}
	}
 
	/*
	 * checkWin method
	 * logic behind winning the game checks grid in four directions starting at the top left
	 * horizontal checks - checks to make sure their are 4 spots to the right if so checks for 5 in a row
	 * vertical checks - checks to make sure their are 4 spots above if so checks for 5 in a row
	 * vertical right - right diagonal checks ensures enough space and then checks for 5 in a row
	 * vertical left - left diagonal checks to ensure enough space and then checks for 5 in a row
	 * 
	 * these four checks cover all possible win conditions
	 * if a win condition is met the processWin method is called
	 */
	public void checkWin(char token) throws IOException {
		
		for(int r = 0; r <board.getHeight();r++) {
			for(int c = 0; c <board.getWidth();c++) 
			{
				
				if(token=='.') 
					continue;//no need to check empty spaces
				
				//checks right
				if(c+4<board.getWidth() &&
					token == board.getBoard()[c+1][r] &&
					token == board.getBoard()[c+2][r] &&
					token == board.getBoard()[c+3][r] &&
					token == board.getBoard()[c+4][r] ) 
					{
						processWin(token);
					}
				
				if(r-4 >= 0) {
					//check up	
					if(token == board.getBoard()[c][r-1] &&
						token == board.getBoard()[c][r-2] &&
						token == board.getBoard()[c][r-3] &&
						token == board.getBoard()[c][r-4] ) 
						{
							processWin(token);
						}
					//check up and right
					if(c+4<board.getWidth() &&
						token == board.getBoard()[c+1][r-1] &&
						token == board.getBoard()[c+2][r-2] &&
						token == board.getBoard()[c+3][r-3] &&
						token == board.getBoard()[c+4][r-4] ) 
						{
							processWin(token);
						}
					//check up and left
					if(c - 4 >= 0 &&
						token == board.getBoard()[c-1][r-1] &&
						token == board.getBoard()[c-2][r-2] &&
						token == board.getBoard()[c-3][r-3] &&
						token == board.getBoard()[c-4][r-4] ) 
						{
							processWin(token);
						}
				}
			}
		}
		
	}
	
	/*
	 * processWin method
	 * 
	 * sends the current game state to both players
	 * sends relevant game outcome information to both players
	 * closes socket connection
	 */
	public void processWin(char token) throws IOException {
		
		player1.sendGameState(board);
		player2.sendGameState(board);
		
		if (token == 'x'){
			player1.sendMessageToClient("Game Over... You Win!");
			player2.sendMessageToClient("Game Over... " + player1.getName() + " Wins, Better luck next time.");
		}
		else {
			player2.sendMessageToClient("Game Over... You Win!");
			player1.sendMessageToClient("Game Over... " + player2.getName() + " Wins, Better luck next time.");
		}

		player1.getConnection().close();
		player2.getConnection().close();
		
	}

	
}
