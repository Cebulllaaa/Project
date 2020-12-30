package ConstantConnectionTests;

import Project.server.main.Server;
import Project.server.main.ServerActivities;
import Project.server.main.StartServerConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.Socket;

import org.junit.Test;

public class TwoPlayersSimulation {
	int port;
	Server server;
	StartServerConnection stc;
	ConstantConnectionClient client1;
	ConstantConnectionClient client2;
	
	Socket client1socket;
	Socket client2socket;
	
	@Test
	public void gametest() {
		try {
			port = 6000;
			server = new Server(port,2);
			server.create_Server();
			stc = new StartServerConnection(server);
			stc.start();
			
			client1socket = new Socket("127.0.0.1",port);
			client1 = new ConstantConnectionClient(port,client1socket);
			client1.start();
			stc.join(100);
			assertEquals("1;1;",client1.command);
			
			client2socket = new Socket("127.0.0.1",port);
			client2 = new ConstantConnectionClient(port,client2socket);
			client2.start();
			stc.join(100);
			assertEquals("1;4;",client2.command);
			
			server.command_ms.activiti =ServerActivities.SEND_GAME_INFORMATION;
			stc.join(100);
			
			assertEquals("2;2;1;",client1.command);
			assertEquals("2;2;1;",client2.command);
			
			server.command_ms.activiti =ServerActivities.SEND_START_GAME;
			stc.join(100);
			
			assertEquals("3;",client1.command);
			assertEquals("3;",client2.command);
			
			server.command_ms.game.create_Board();
			server.command_ms.game.create_Queue();
			for(int i =0 ; i<10; i++) {
				server.command_ms.activiti =ServerActivities.SEND_BOARD;
				stc.join(100);
				server.command_ms.activiti =ServerActivities.SEND_WHOSE_TURN;
				stc.join(100);
				System.out.println(server.command_ms.getCommand());
				System.out.println(server.command_ms.game.getMove());
				server.command_ms.game.increase_Queue();
				
			}
			server.command_ms.activiti =ServerActivities.SEND_BOARD;
			stc.join(100);
			
			server.command_ms.game.set_winner(1);
			server.command_ms.activiti =ServerActivities.SEND_END_GAME;
			stc.join(100);
			//System.out.println("odczekalem");
			
			assertEquals("6;1;",client1.command);
			assertEquals("6;1;",client2.command);
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
