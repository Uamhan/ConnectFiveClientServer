package connect5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * 
 * @author Uamhan Mac Fhearghusa
 * 
 * 	server side player object stores connection values for a players client and handles the passing and receiving of data to and from the client
 * 
 * 	@param connection - Socket connection from client to server
 * 	@param name - name of the player
 * 	@param token - token representing the player on the board
 * 	@param oos - object output stream, stream to send message object to client
 * 	@param ois - object input stream, stream used to receive message objects from client
 * 	@param message - current message object to be sent to or received from client 
 *
 */
public class ServerC5Player {

	private Socket connection;
	private String name;
	private char token;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private C5Message message;
	
	
	/*
	 * constructor takes in socket and instantiates object streams and gets the name value from client
	 */
	ServerC5Player(Socket socket) throws IOException, ClassNotFoundException {
		
		this.connection = socket;
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		message = new C5Message();
		message = (C5Message) ois.readObject();
		this.name = message.getText();
		
	}

	/*
	 * sendMessageToClient method
	 * takes string as input
	 * creates new message object sets message object text to input striing
	 * and sends the message to client
	 */
	public void sendMessageToClient(String message) throws IOException {
		this.message = new C5Message();
		this.message.setText(message);
		oos.writeObject(this.message);
	}
	
	/*
	 * sendGameState method
	 * takes game board as input 
	 * creates new message
	 * sets message object board field to the input board and sends message to client
	 */
	public void sendGameState(C5GameBoard board) throws IOException {
		this.message = new C5Message();
		this.message.setBoard(board);
		oos.writeObject(this.message);
		oos.reset();
	}
	
	/*
	 * getPlayerMove method
	 * creates new message 
	 * sets value of yourTurn to true
	 * sends message to client waits for user input response
	 * reads in users response and returns this values
	 */
	public int getPlayerMove() throws ClassNotFoundException, IOException {
		this.message = new C5Message();
		this.message.setYourTurn(true);
		oos.writeObject(this.message);
		this.message = (C5Message) ois.readObject();
		return this.message.getTurnChoice();
	}
	
	
	public Socket getConnection() {
		return connection;
	}

	public void setConnection(Socket connection) {
		this.connection = connection;
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getToken() {
		return token;
	}

	public void setToken(char token) {
		this.token = token;
	}
	
	public C5Message getMessage() {
		return message;
	}

	public void setMessage(C5Message message) {
		this.message = message;
	}
	
	
	
}
