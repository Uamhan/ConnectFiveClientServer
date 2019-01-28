package connect5;

import java.io.IOException;

/**
 * Class Acts as application starting The Connect Five Server.
 * 
 * @author Uamhan Mac Fhearghusa
 *
 */

public class ServerC5ServerApplication {
	
	/**
	 * Main method of Server Executed when server started.
	 * 
	 * @param port - port number for server to listen on
	 *
	 */
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		int port = 8080;
		
		ServerC5Server server = new ServerC5Server(port);
		System.out.println("Server Starting...");
		server.start();	
	}
	
}
