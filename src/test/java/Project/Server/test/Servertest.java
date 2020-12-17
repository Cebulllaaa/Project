package Project.Server.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.junit.Test;

import Project.server.main.Server;

import java.util.Set;


public class Servertest {

	@Test
	public void ServerConstructortest() {
		Server serwer = new Server(6000);
		assertEquals(6000,serwer.port);
	} 
	@Test
	public void choose_typetest() {
		Server serwer = new Server(6000);
		serwer.choose_Type();
		assertEquals(1,serwer.type_Game);
	}
	@Test 
	public void createServertest() {
		Server serwer = new Server(5999);
		serwer.create_Server();
		assertTrue(serwer.socket.isBound());	
		
	}
	@Test
	public void listenServertest() {
		int port = 6003;
		Server serwer = new Server(port);
		serwer.create_Server();
		ThreadListen listen;
		ThreadWriteClient write;
		String test = "TEST";
		String test2 = "Some Command";
		String test3 = "Other Command";
		try {
			listen = new ThreadListen();
			listen.serwer = serwer;
			write = new ThreadWriteClient();
			write.port = port;
			write.command=test;
			listen.start();
			write.start();
			write.join();
			listen.join();
			assertEquals (test,serwer.command); 
			
			listen = new ThreadListen();
			listen.serwer=serwer;
			write = new ThreadWriteClient();
			write.port = port;
			write.command = test2;
			listen.start();
			write.start();
			write.join();
			listen.join();
			assertFalse(test.equals(serwer.command));
			assertEquals(test2,serwer.command);
			
			listen = new ThreadListen();
			listen.serwer=serwer;
			write = new ThreadWriteClient();
			write.port = port;
			write.command = test3;
			listen.start();
			write.start();
			write.join();
			listen.join();
			assertFalse(test.equals(serwer.command));
			assertEquals(test3,serwer.command);
			assertFalse(test2.equals(serwer.command));
		}
		catch(Exception e) {
			System.out.println(e);
		}
	} 
	@Test
	public synchronized void EstabilishConnectionTest() {
		int port_serwer = 6005;
		Server serwer  = new Server(port_serwer);
		serwer.create_Server();
		serwer.for_connection_test=true;
		
		ThreadConnection testconnection = new ThreadConnection();
		ThreadConnection testconnection2 = new ThreadConnection();
		ThreadConnection testconnection3 = new ThreadConnection();
		ThreadConnection testconnection4= new ThreadConnection();
		ThreadConnection testconnection5 = new ThreadConnection();
		ThreadConnection testconnection6= new ThreadConnection();
		
		testconnection.port_serwer = port_serwer;
		testconnection2.port_serwer = port_serwer;
		testconnection3.port_serwer = port_serwer;
		testconnection4.port_serwer = port_serwer;
		testconnection5.port_serwer = port_serwer;
		testconnection6.port_serwer = port_serwer;
		
		ThreadConnectionServer serverconnection = new ThreadConnectionServer();
		
		serverconnection.serwer = serwer;
		
		serverconnection.start();
		
		testconnection.start();
		testconnection2.start();
		testconnection3.start();
		testconnection4.start();
		testconnection5.start();
		testconnection6.start();
		
		try {
		testconnection.join();
		testconnection2.join();
		testconnection3.join();
		testconnection4.join();
		testconnection5.join();
		testconnection6.join();
		
		serwer.connection_iterator =1;
		serverconnection.join();
		assertEquals(6,serwer.connected);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	@Test
	public void testServerWriter() {
		int port_serwer = 6006;
		Server serwer  = new Server(port_serwer);
		serwer.create_Server();
		
		String test = "TEST";
		String test2 = "Instruction from Server";
		String test3 = "Other instruction from Server";
		
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
		
		ThreadConnectionServer serverconnection = new ThreadConnectionServer();
		serwer.command = test;
		serverconnection.serwer =serwer;
		
		serverconnection.start();
		client1.start();
		client2.start();
		client3.start();
		client4.start();
		client5.start();
		client6.start();
		
		try {
			client1.join();
			client2.join();
			client3.join();
			client4.join();
			client5.join();
			client6.join();
			
			assertEquals(client1.command,test);
			assertEquals(client2.command,test);
			assertEquals(client3.command,test);
			assertEquals(client4.command,test);
			assertEquals(client5.command,test);
			assertEquals(client6.command,test);
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
