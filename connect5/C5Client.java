package connect5;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Uamhan Macfhearghusa
 * 
 * 	Client class that handles all information passed to a from the server
 * 
 *	@param host - Location of the server
 *	@param port - PortNumber to connect to on server.
 *	@param name - Name of player using the client.
 *	@param client - Socket used to pass/receive data to/from server 
 *	@param ois - Object input stream used to receive message object from server
 *	@param oos - Object output stream used to send message object to server
 *	@param message - message object that contains information to be passed/received from server
 *	@param validInput - boolean value used in checking valid user input
 *
 */

public class C5Client {
 
	private String host;
	private int port;
	private String name;
	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private C5Message message;
	private boolean validInput;
	
	C5Client(String host,int port,String name){
		this.host = host;
		this.port = port;
		this.name = name;
	}
	/*
	 * Connect Method (initialisation method)
	 * Establishes connection to server
	 * Instantiates object streams and begins main client loop with listen()
	 * passes user name to server
	 */
	public void connect() throws UnknownHostException, IOException, ClassNotFoundException {
		client = new Socket(host,port);
		message = new C5Message();
		message.setText(name);
		oos = new ObjectOutputStream(client.getOutputStream());
		ois = new ObjectInputStream(client.getInputStream());
		oos.writeObject(message);
		System.out.println("Connected to game server as " + name);
		listen();
	}

	
	/*
	 * Listen Method(Main Loop)
	 * main logic loop for client
	 * listens for new message from server
	 * if message contains a board prints board to console
	 * if contains message prints message to console
	 * if contains boolean value for isYourTurn asks for user input for move and sends to server
	 * 
	 */
	public void listen() throws IOException, ClassNotFoundException {
		while(!client.isClosed()) {		
			try {	
				validInput=false;
				message = new C5Message();
				message = (C5Message)ois.readObject();
				
				if(message.getBoard()!=null) {
					message.getBoard().printBoard();
				}
				
				else if(message.getText()!=null){
					System.out.println(message.getText());
				}
				else if(message.isYourTurn()) {
					while(!validInput) {
						@SuppressWarnings("resource")
						Scanner scanner = new Scanner(System.in);
							
							int choice = Integer.parseInt(scanner.next());
							if(choice >=1 && choice <=9 && choice == (int)choice) {
								this.message = new C5Message();
								message.setTurnChoice(choice);
								oos.writeObject(message);
								validInput=true;
							}
							else {
								System.out.println("invalid input try again");
							}
					}
							
				}			
			}
			catch(Exception e) {	
			}		
		}	
		System.out.println("disconected from server Closing game.");
	}
	
}
