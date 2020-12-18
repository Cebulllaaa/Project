package Project.Server.connection.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.junit.Test;

import Project.Server.test.ThreadConnectionServer;
import Project.Server.test.ThreadListenClient;
import Project.server.main.Server;
/**Klasa testujaca aktywnosci komuniakcyjne serwera od indeksach 2-6*/
public class ServerConnectionFunctionstest {
	@Test
	public void send_Game_information_test() {
		int port_serwer = 6000;
		Server serwer  = new Server(port_serwer);
		serwer.create_Server();
		ThreadListenClient client1 = new ThreadListenClient();
		client1.port_serwer = port_serwer;
		ThreadListenClient client2 = new ThreadListenClient();
		client2.port_serwer = port_serwer;
		ThreadListenClient client3 = new ThreadListenClient();
		client3.port_serwer = port_serwer;
		ThreadListenClient client4 = new ThreadListenClient();
		client4.port_serwer = port_serwer;
		ThreadListenClient client5 = new ThreadListenClient();
		client5.port_serwer = port_serwer;
		ThreadListenClient client6 = new ThreadListenClient();
		client6.port_serwer = port_serwer;
		ThreadListenClient client7 = new ThreadListenClient();
		client7.port_serwer = port_serwer;
		
		ThreadConnectionServer serverconnection = new ThreadConnectionServer();
		serverconnection.serwer =serwer;
		
		serverconnection.start();
		client1.start();
		client2.start();
		client3.start();
		client4.start();
		client5.start();
		client6.start();
		client7.start();
		
		try {
			client1.join();
			client2.join();
			client3.join();
			client4.join();
			client5.join();
			client6.join();
			client7.join();
			
			
			System.out.println(client1.command);
			System.out.println(client2.command);
			System.out.println(client3.command);
			System.out.println(client4.command);
			System.out.println(client5.command);
			System.out.println(client6.command);
			System.out.println(client7.command); 
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
