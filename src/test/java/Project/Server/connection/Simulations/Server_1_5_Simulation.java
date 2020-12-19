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
import Project.server.main.ServerActivities;
import Project.server.main.ThreadConnectionServer;
import Project.server.main.Server;

public class Server_1_5_Simulation {
	@Test
	public void make_Simulation() {
		int port_serwer = 6000;
		Server serwer  = new Server(port_serwer);
		serwer.create_Server();
		String cmd;
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
		
		ThreadConnectionServer serverconnection = new ThreadConnectionServer(serwer);
		serverconnection.start();
/* ETAP 1 PODANIE ID */
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
			
			/*
			System.out.println(client1.command);
			System.out.println(client2.command);
			System.out.println(client3.command);
			System.out.println(client4.command);
			System.out.println(client5.command);
			System.out.println(client6.command);  */
/* ETAP 2 PODANIE INFORMACJI O GRZE*/		
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
			
			serwer.command_ms.activiti=ServerActivities.SEND_GAME_INFORMATION;
			
			client1.start();
			client2.start();
			client3.start();
			client4.start();
			client5.start();
			client6.start();
			
			client1.join();
			client2.join();
			client3.join();
			client4.join();
			client5.join();
			client6.join();
/*ETAP 3 WYSLANIE INFORMACJI O STARCIE GRY*/			
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
			
			serwer.command_ms.activiti=ServerActivities.SEND_START_GAME;
			cmd = "3;";
			
			client1.start();
			client2.start();
			client3.start();
			client4.start();
			client5.start();
			client6.start();
			
			client1.join();
			client2.join();
			client3.join();
			client4.join();
			client5.join();
			client6.join();
			
			assertEquals(cmd,client1.command);
			assertEquals(cmd,client2.command);
			assertEquals(cmd,client3.command);
			assertEquals(cmd,client4.command);
			assertEquals(cmd,client5.command);
			assertEquals(cmd,client6.command);
			
			
/*ETAP 4 WYSLANIE TABLICY */				
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
			
			serwer.command_ms.activiti=ServerActivities.SEND_BOARD;
			cmd = "4;TODO;";
			
			client1.start();
			client2.start();
			client3.start();
			client4.start();
			client5.start();
			client6.start();
			
			client1.join();
			client2.join();
			client3.join();
			client4.join();
			client5.join();
			client6.join();
			
			assertEquals(cmd,client1.command);
			assertEquals(cmd,client2.command);
			assertEquals(cmd,client3.command);
			assertEquals(cmd,client4.command);
			assertEquals(cmd,client5.command);
			assertEquals(cmd,client6.command);
			
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
			
/*ETAP 5 WYSLANIE CZYJA JEST KOLEJ */
			serwer.command_ms.game.create_Queue();
			serwer.command_ms.activiti=ServerActivities.SEND_WHOSE_TURN;
			String cmd0 = "5;";
			
			
			client1.start();
			client2.start();
			client3.start();
			client4.start();
			client5.start();
			client6.start();
			
			client1.join();
			client2.join();
			client3.join();
			client4.join();
			client5.join();
			client6.join();
			
			int id = serwer.command_ms.game.Queue.get(0);
			cmd = cmd0 +Integer.toString(id) +";";
			
			assertEquals(cmd,client1.command);
			assertEquals(cmd,client2.command);
			assertEquals(cmd,client3.command);
			assertEquals(cmd,client4.command);
			assertEquals(cmd,client5.command);
			assertEquals(cmd,client6.command);
			
			serwer.connection_iterator=1;
			
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
		
		
}
