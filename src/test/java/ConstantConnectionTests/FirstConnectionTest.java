package ConstantConnectionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import Project.Server.test.ThreadWriteClient;
import Project.server.main.Server;
import Project.server.main.StartServerConnection;

public class FirstConnectionTest {
	int port;
	Server server;
	StartServerConnection stc;
	ThreadWriteClient client1;
	
	
	@Test
	public void first_test() {
		port = 6000;
		server = new Server(port);
		server.create_Server();
		stc = new StartServerConnection(server);
		client1 = new ThreadWriteClient();
		client1.port = port;
		stc.run();
		client1.run();
		assertTrue(true);
		
	}
}
