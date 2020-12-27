package ConstantConnectionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.Socket;

import org.junit.Test;

import Project.Server.test.ThreadWriteClient;
import Project.server.main.Server;
import Project.server.main.StartServerConnection;

public class FirstConnectionTests {
	int port;
	Server server;
	StartServerConnection stc;
	ConstantConnectionClient client1;
	ConstantConnectionClient client2;
	ConstantConnectionClient client3;
	ConstantConnectionClient client4;
	ConstantConnectionClient client5;
	ConstantConnectionClient client6;
	ConstantConnectionClient client7;
	
	Socket client1socket;
	Socket client2socket;
	Socket client3socket;
	Socket client4socket;
	Socket client5socket;
	Socket client6socket;
	Socket client7socket;
	
	@Test
	public void first_test() {
		try {
		port = 6000;
		server = new Server(port);
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
		assertEquals("1;2;",client2.command);
		
		client3socket = new Socket("127.0.0.1",port);
		client3 = new ConstantConnectionClient(port,client3socket);
		client3.start();
		stc.join(100);
		assertEquals("1;3;",client3.command);
		
		client4socket = new Socket("127.0.0.1",port);
		client4 = new ConstantConnectionClient(port,client4socket);
		client4.start();
		stc.join(100);
		assertEquals("1;4;",client4.command);
		
		client5socket = new Socket("127.0.0.1",port);
		client5 = new ConstantConnectionClient(port,client5socket);
		client5.start();
		stc.join(100);
		assertEquals("1;5;",client5.command);
		
		client6socket = new Socket("127.0.0.1",port);
		client6 = new ConstantConnectionClient(port,client6socket);
		client6.start();
		stc.join(100);
		assertEquals("1;6;",client6.command);
		
		client7socket = new Socket("127.0.0.1",port);
		client7 = new ConstantConnectionClient(port,client7socket);
		client7.start();
		stc.join(100);
		assertEquals("1;-1;",client7.command);
		
		}
		catch(Exception e) {		
			}
		
		assertTrue(true);
		
	}
}
