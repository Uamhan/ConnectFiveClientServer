package connect5;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Socket based server used for Connect Five game.
 * 
 * @author Uamhan Mac Fhearghusa
 * 
 * 	server class 
 * 	class contains socket and port information so the user can connect
 * 	
 * 	@param socket - the server socket for the clients to connect to
 * 	@param port - the port on which the clients will connect
 * 	@param game - the game object in which all game logic is executed
 * 	
 *
 */

public class ServerC5Server {

	private ServerSocket socket;
	private int port;
	private ServerC5Game game;
	
	/*
	 * constructor takes in port number to create socket on
	 */
	public ServerC5Server (int port) {
		this.port = port;
	}
	
	/*
	 * creates new server socket 
	 * instantiates first connection as player one 
	 * instantiates second connect as player two 
	 * instantiates new game and starts in with both players connections as parameters
	 */
	public void start() throws IOException, ClassNotFoundException {
		
		socket = new ServerSocket(port,2);
		System.out.println("Server Started at port : " + port);
		
		System.out.println("Waiting for players to connect.");
			
		ServerC5Player player1 = new ServerC5Player(socket.accept());
		System.out.println(player1.getName() + " connected. waiting on Player 2");
		player1.sendMessageToClient("Waiting on player 2");
		
		ServerC5Player player2 = new ServerC5Player(socket.accept());
		System.out.println(player2.getName() + " connected. starting game...");
		
		game = new ServerC5Game();
		
		game.start(player1,player2);
		
		socket.close();
	}
	
}
