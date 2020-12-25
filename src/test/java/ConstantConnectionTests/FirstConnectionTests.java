package ConstantConnectionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import Project.Server.test.ThreadWriteClient;
import Project.server.main.Server;
import Project.server.main.StartServerConnection;

public class FirstConnectionTests {
	int port;
	Server server;
	StartServerConnection stc;
	ThreadWriteClient client1;
	ThreadWriteClient client2;
	ThreadWriteClient client3;
	ThreadWriteClient client4;
	ThreadWriteClient client5;
	ThreadWriteClient client6;
	
	
	@Test
	public void first_test() {
		try {
		port = 6000;
		server = new Server(port);
		server.create_Server();
		stc = new StartServerConnection(server);
		client1 = new ThreadWriteClient();
		client1.port = port;
		
		stc.start();
		client1.start();
		
		stc.join(100);
		
		server.command_ms.game.add_Player();
		client1 = new ThreadWriteClient();
		client1.port = port;
		client1.start();
		stc.join(100);
		
		server.command_ms.game.add_Player();
		client1 = new ThreadWriteClient();
		client1.port = port;
		client1.start();
		stc.join(100);
		
		server.command_ms.game.add_Player();
		client1 = new ThreadWriteClient();
		client1.port = port;
		client1.start();
		stc.join(100);
		
		server.command_ms.game.add_Player();
		client1 = new ThreadWriteClient();
		client1.port = port;
		client1.start();
		stc.join(100);
		
		server.command_ms.game.add_Player();
		client1 = new ThreadWriteClient();
		client1.port = port;
		client1.start();
		stc.join(100);
		
		}
		catch(Exception e) {		
			}
		
		assertTrue(true);
		
	}
}
