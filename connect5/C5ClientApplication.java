package connect5;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 * @author Uamhan Mac Fhearghusa
 * 
 * client application main method class.
 * sets base connect details and instantiates the main client method.
 * calls connect on client object.
 * 
 * 	@param host - the address of the server your going to connect to
 * 	@param port - the port to connect to on the server
 *	@param name - username of person connecting to server
 */

public class C5ClientApplication {

	public static void main (String [] args) throws UnknownHostException, IOException, ClassNotFoundException {
		//--------------------------
		//values can be changed if server is not on same machine.
		String host = "localhost";
		int port = 8080;
		//--------------------------
		
		String name;
		
		System.out.println("Welcome to Connect 5 Please enter a display name");
		Scanner scanner = new Scanner(System.in);
		name = scanner.next();
		C5Client client = new C5Client(host,port,name);
		client.connect();
		scanner.close();
	}
	
}
