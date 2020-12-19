package Project.Server.connection.Simulations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.junit.Test;

import Project.Server.test.ThreadListenClient;
import Project.Server.test.ThreadWriteClient;
import Project.server.main.ServerActivities;
import Project.server.main.ThreadConnectionServer;
import Project.server.main.Server;
/*Jest to klasa która, symuluje  gre, (2 zostanie ona zakonczona w 2 ruchach )
 * jej zadaniem jest sprawdzic czy na poziomie komunikacyjnym serwera wszystko dziala 
*/
public class FastGameSimulation {
	 int port_serwer = 6009;
	Server server;
	boolean started = false;
	boolean correctmove;
	ThreadConnectionServer serverconnection;
	ThreadListenClient client1;
	ThreadListenClient client2;
	ThreadListenClient client3;
	ThreadListenClient client4;
	ThreadListenClient client5;
	ThreadListenClient client6;
	ThreadWriteClient write;
	String test = "Answer";
@Test
	public void main() {
		prepare_simulation();
	}
	public void prepare_simulation() {
		server = new Server(port_serwer);
		server.create_Server();
		estabilishConnection();
		started =true;
		estabilishConnection();
	}
	public void prepare_client_listeners() {
		client1 = new ThreadListenClient();
		client1.port_serwer = port_serwer;
		client2 = new ThreadListenClient();
		client2.port_serwer = port_serwer;
		client3 = new ThreadListenClient();
		client3.port_serwer = port_serwer;
		client4 = new ThreadListenClient();
		client4.port_serwer = port_serwer;
		client5 = new ThreadListenClient();
		client5.port_serwer = port_serwer;
		client6 = new ThreadListenClient();
		client6.port_serwer = port_serwer;
	}
	public void estabilishConnection() {
		if(!started) {
			serverconnection = new ThreadConnectionServer(server);
			serverconnection.start();
			send_id();
			server.command_ms.activiti=ServerActivities.SEND_GAME_INFORMATION;
			send_game_info();
			server.command_ms.activiti=ServerActivities.SEND_START_GAME;
			send_start_info();
			server.command_ms.activiti=ServerActivities.SEND_BOARD;
			send_board();
			server.command_ms.activiti=ServerActivities.SEND_WHOSE_TURN;
			server.command_ms.game.create_Queue();
			send_whose_turn();
			System.out.println(server.command_ms.game.Queue.get(server.command_ms.game.in_Queue));
		}
		else {
			server.command_ms.activiti=ServerActivities.LISTEN;
			listen_client();
			assertEquals (test,server.command_ms.getCommand()); 
			correctmove = true; //TODO
			if(correctmove) {
				server.command_ms.game.increase_Queue();
			}
			server.command_ms.activiti=ServerActivities.SEND_BOARD;
			send_board();
			server.command_ms.activiti=ServerActivities.SEND_WHOSE_TURN;
			send_whose_turn();
			System.out.println(server.command_ms.game.Queue.get(server.command_ms.game.in_Queue));
			server.command_ms.activiti=ServerActivities.LISTEN;
			listen_client();
			server.command_ms.activiti=ServerActivities.SEND_BOARD;
			send_board();
			server.command_ms.activiti=ServerActivities.SEND_END_GAME;
			server.command_ms.game.set_winner(1);
			send_end_info();
			server.connection_iterator=1;
		}
	}	
	public void send_id() {
		String cmd1 = "1;1;";
		String cmd2 = "1;2;";
		String cmd3 = "1;3;";
		String cmd4 = "1;4;";
		String cmd5 = "1;5;";
		String cmd6 = "1;6;";
		
		prepare_client_listeners();
		
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
			
			boolean p1 = (client1.command.equals(cmd1) || client1.command.equals(cmd2) 
					|| 	client1.command.equals(cmd3) || client1.command.equals(cmd4) 
					|| client1.command.equals(cmd5) || client1.command.equals(cmd6));
			boolean p2 = (client2.command.equals(cmd1) || client2.command.equals(cmd2) 
					|| 	client2.command.equals(cmd3) || client2.command.equals(cmd4) 
					|| client2.command.equals(cmd5) || client2.command.equals(cmd6) );
			boolean p3 = (client3.command.equals(cmd1) || client3.command.equals(cmd2) 
					|| 	client3.command.equals(cmd3) || client3.command.equals(cmd4) 
					|| client3.command.equals(cmd5) || client3.command.equals(cmd6) );
			boolean p4 = (client4.command.equals(cmd1) || client4.command.equals(cmd2) 
					|| 	client4.command.equals(cmd3) || client4.command.equals(cmd4) 
					|| client4.command.equals(cmd5) || client4.command.equals(cmd6) );
			boolean p5 = (client5.command.equals(cmd1) || client5.command.equals(cmd2) 
					|| 	client5.command.equals(cmd3) || client5.command.equals(cmd4) 
					|| client5.command.equals(cmd5) || client5.command.equals(cmd6) );
			boolean p6 = (client6.command.equals(cmd1) || client6.command.equals(cmd2) 
					|| 	client6.command.equals(cmd3) || client6.command.equals(cmd4) 
					|| client6.command.equals(cmd5) || client6.command.equals(cmd6) );
			
			assertTrue(p1);
			assertTrue(p2);
			assertTrue(p3);
			assertTrue(p4);
			assertTrue(p5);
			assertTrue(p6);
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}	
	public void send_game_info() {
		String cmd =  "2;6;1;";
		prepare_client_listeners();
		
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
		}
		catch(Exception e) {
			System.out.println(e);
		}
		assertEquals(cmd,client1.command);
		assertEquals(cmd,client2.command);
		assertEquals(cmd,client3.command);
		assertEquals(cmd,client4.command);
		assertEquals(cmd,client5.command);
		assertEquals(cmd,client6.command);
	
	}
	public void send_start_info() {
		String cmd =  "3;";
		prepare_client_listeners();
		
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
		}
		catch(Exception e) {
			System.out.println(e);
		}
		assertEquals(cmd,client1.command);
		assertEquals(cmd,client2.command);
		assertEquals(cmd,client3.command);
		assertEquals(cmd,client4.command);
		assertEquals(cmd,client5.command);
		assertEquals(cmd,client6.command);
	
	}
	public void send_board() {
		String cmd =  "4;TODO;";
		prepare_client_listeners();
		
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
		}
		catch(Exception e) {
			System.out.println(e);
		}
		assertEquals(cmd,client1.command);
		assertEquals(cmd,client2.command);
		assertEquals(cmd,client3.command);
		assertEquals(cmd,client4.command);
		assertEquals(cmd,client5.command);
		assertEquals(cmd,client6.command);
		
	}
	public void send_whose_turn() {
		String cmd0 = "5;";
		prepare_client_listeners();
		
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
		}
		catch(Exception e) {
			System.out.println(e);
		}
		int id = server.command_ms.game.Queue.get(server.command_ms.game.in_Queue);
		String cmd = cmd0 +Integer.toString(id) +";";
		assertEquals(cmd,client1.command);
		assertEquals(cmd,client2.command);
		assertEquals(cmd,client3.command);
		assertEquals(cmd,client4.command);
		assertEquals(cmd,client5.command);
		assertEquals(cmd,client6.command);
	}
	public synchronized void listen_client() {
		server.command_ms.setCommand("");
		String test = "Answer";
		write = new ThreadWriteClient();
		write.port = port_serwer;
		write.command=test;
	
		write.start();
		try {
			write.join();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		while(server.command_ms.getCommand().isEmpty()) {
			
		}
		assertEquals (test,server.command_ms.getCommand());
	}
	public void send_end_info() {
		String cmd =  "6;1;";
		prepare_client_listeners();
		
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
		}
		catch(Exception e) {
			System.out.println(e);
		}
		assertEquals(cmd,client1.command);
		assertEquals(cmd,client2.command);
		assertEquals(cmd,client3.command);
		assertEquals(cmd,client4.command);
		assertEquals(cmd,client5.command);
		assertEquals(cmd,client6.command);
		
	}
}
