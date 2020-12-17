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
	//W ramach tesu uzyje komunikacji pomiedzy dwoma serwerami 
	@Test
	public void writeServertest() {
		int port_serwer = 6004;
			
		Server serwer = new Server(port_serwer);
		serwer.create_Server();
		
		ThreadListenClient client1 = new ThreadListenClient();
		client1.port_serwer = port_serwer;
		
		ThreadWrite serwerw = new ThreadWrite();
		serwerw.serwer = serwer;
		
		String test = "TEST";
		String test2 = "Instruction from Server";
		String test3 = "Other instruction from Server";
		try {
			serwer.command = test;
			serwerw.serwer = serwer;
			
			client1.start();
			serwerw.start();
							//Spostrzezenie wywolanie raz metody write sprawi ze tylko jeden klient otrzyma informacje
							// Bede musial zaimplementowac funkcje Estabilish Connection i wysylac wiadomosci do klientow 
							// z osobna
			serwerw.join();
			client1.join();
			
			assertEquals(client1.command,test);
			
			serwer.command = test2;
			
			client1 = new ThreadListenClient();
			serwerw = new ThreadWrite();
			serwerw.serwer = serwer;
			client1.port_serwer = port_serwer;
			
			client1.start();
			serwerw.start();
			
			client1.join();
			serwerw.join();
			
			assertEquals(client1.command,test2);
			assertFalse(test.equals(serwer.command));
			
			serwer.command = test3;
			
			client1 = new ThreadListenClient();
			serwerw = new ThreadWrite();
			serwerw.serwer = serwer;
			client1.port_serwer = port_serwer;
			
			client1.start();
			serwerw.start();
			
			client1.join();
			serwerw.join();
			
			assertEquals(client1.command,test3);
			assertFalse(test.equals(serwer.command));
			assertFalse(test2.equals(serwer.command));
			
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	@Test
	public void EstabilishConnectionTest() {
		int port_serwer = 6005;
		Server serwer  = new Server(port_serwer);
		serwer.create_Server();
		
		ThreadConnection testconnection = new ThreadConnection();
		testconnection.port_serwer = port_serwer;
		ThreadConnectionServer serverconnection = new ThreadConnectionServer();
		
		serverconnection.serwer = serwer;
		
		serverconnection.start();
		testconnection.start();
		
	}
}
