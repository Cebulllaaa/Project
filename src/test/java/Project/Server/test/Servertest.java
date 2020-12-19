package Project.Server.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.junit.Test;

import Project.server.main.ServerActivities;
import Project.server.main.Server;

import java.util.Set;


public class Servertest {
/*
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
			assertEquals (test,serwer.command_ms.getCommand()); 
			
			listen = new ThreadListen();
			listen.serwer=serwer;
			write = new ThreadWriteClient();
			write.port = port;
			write.command = test2;
			listen.start();
			write.start();
			write.join();
			listen.join();
			assertFalse(test.equals(serwer.command_ms.getCommand()));
			assertEquals(test2,serwer.command_ms.getCommand());
			
			listen = new ThreadListen();
			listen.serwer=serwer;
			write = new ThreadWriteClient();
			write.port = port;
			write.command = test3;
			listen.start();
			write.start();
			write.join();
			listen.join();
			assertFalse(test.equals(serwer.command_ms.getCommand()));
			assertEquals(test3,serwer.command_ms.getCommand());
			assertFalse(test2.equals(serwer.command_ms.getCommand()));
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
		serwer.command_ms.activiti= ServerActivities.WRITE_TEST;
		
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
		serwer.command_ms.setCommand(test);
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
	/** Ten test sprawdza sytuacje w ktorej 7 klientow probuje podlaczyc sie do gry : Trylma game */
/*	@Test
	public void testWriteDifferentID(){
		int port_serwer = 6007;
		Server serwer  = new Server(port_serwer);
		serwer.create_Server();
		
		String cmd1 = "1;1;";
		String cmd2 = "1;2;";
		String cmd3 = "1;3;";
		String cmd4 = "1;4;";
		String cmd5 = "1;5;";
		String cmd6 = "1;6;";
		String cmd7 = "1;-1;";
		
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
			
			/*Z uwagi na charakter watkow nie wiadomo , ktory klient otrzyma ktore id , ale beda one
			 * ale beda one unikalne (oraz nie dojdzie do sytuacji ze ktos otrzyma id : 3 jezeli nikt nie 
			 * posiada id : 2 */
		/*	
			System.out.println(client1.command);
			System.out.println(client2.command);
			System.out.println(client3.command);
			System.out.println(client4.command);
			System.out.println(client5.command);
			System.out.println(client6.command);
			System.out.println(client7.command); */
		/*	
			boolean p1 = (client1.command.equals(cmd1) || client1.command.equals(cmd2) 
					|| 	client1.command.equals(cmd3) || client1.command.equals(cmd4) 
					|| client1.command.equals(cmd5) || client1.command.equals(cmd6) 
					|| client1.command.equals(cmd7));
			boolean p2 = (client2.command.equals(cmd1) || client2.command.equals(cmd2) 
					|| 	client2.command.equals(cmd3) || client2.command.equals(cmd4) 
					|| client2.command.equals(cmd5) || client2.command.equals(cmd6) 
					|| client2.command.equals(cmd7));
			boolean p3 = (client3.command.equals(cmd1) || client3.command.equals(cmd2) 
					|| 	client3.command.equals(cmd3) || client3.command.equals(cmd4) 
					|| client3.command.equals(cmd5) || client3.command.equals(cmd6) 
					|| client3.command.equals(cmd7));
			boolean p4 = (client4.command.equals(cmd1) || client4.command.equals(cmd2) 
					|| 	client4.command.equals(cmd3) || client4.command.equals(cmd4) 
					|| client4.command.equals(cmd5) || client4.command.equals(cmd6) 
					|| client4.command.equals(cmd7));
			boolean p5 = (client5.command.equals(cmd1) || client5.command.equals(cmd2) 
					|| 	client5.command.equals(cmd3) || client5.command.equals(cmd4) 
					|| client5.command.equals(cmd5) || client5.command.equals(cmd6) 
					|| client5.command.equals(cmd7));
			boolean p6 = (client6.command.equals(cmd1) || client6.command.equals(cmd2) 
					|| 	client6.command.equals(cmd3) || client6.command.equals(cmd4) 
					|| client6.command.equals(cmd5) || client6.command.equals(cmd6) 
					|| client6.command.equals(cmd7));
			boolean p7 = (client7.command.equals(cmd1) || client7.command.equals(cmd2) 
					|| 	client7.command.equals(cmd3) || client7.command.equals(cmd4) 
					|| client7.command.equals(cmd5) || client7.command.equals(cmd6) 
					|| client7.command.equals(cmd7));
			
			assertTrue(p1);
			assertTrue(p2);
			assertTrue(p3);
			assertTrue(p4);
			assertTrue(p5);
			assertTrue(p6);
			assertTrue(p7);
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	} 
	*/
	@Test
	public void newListenTest() {
		int port_serwer = 6008;
		Server serwer  = new Server(port_serwer);
		serwer.create_Server();
		
		ThreadWriteClient write;
		String test = "TEST";
		String test2 = "Some Command";
		String test3 = "Other Command";
		serwer.command_ms.activiti = ServerActivities.LISTEN;
		try {
			write = new ThreadWriteClient();
			write.port = port_serwer;
			write.command=test;
			serwer.write();
			System.out.println("zaraz zacnzie pisac");
			write.start();
			
			write.join();
		
			assertEquals (test,serwer.command_ms.getCommand()); 
			
			/*
			write = new ThreadWriteClient();
			write.port = port;
			write.command = test2;
			listen.start();
			write.start();
			write.join();
			listen.join();
			assertFalse(test.equals(serwer.command_ms.getCommand()));
			assertEquals(test2,serwer.command_ms.getCommand());
			
			listen = new ThreadListen();
			listen.serwer=serwer;
			write = new ThreadWriteClient();
			write.port = port;
			write.command = test3;
			listen.start();
			write.start();
			write.join();
			listen.join();
			assertFalse(test.equals(serwer.command_ms.getCommand()));
			assertEquals(test3,serwer.command_ms.getCommand());
			assertFalse(test2.equals(serwer.command_ms.getCommand())); */
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
