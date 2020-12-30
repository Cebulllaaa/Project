package ConstantConnectionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.Socket;

import org.junit.Test;

import Project.Server.test.ThreadWriteClient;
import Project.server.main.Server;
import Project.server.main.ServerActivities;
import Project.server.main.StartServerConnection;

public class ConstantConnectionSimplySimulation {
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
		server = new Server(port,6);
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
		
		server.command_ms.activiti =ServerActivities.SEND_GAME_INFORMATION;
		stc.join(100);
		
		assertEquals("2;6;1;",client1.command);
		assertEquals("2;6;1;",client2.command);
		assertEquals("2;6;1;",client3.command);
		assertEquals("2;6;1;",client4.command);
		assertEquals("2;6;1;",client5.command);
		assertEquals("2;6;1;",client6.command);
		
		server.command_ms.activiti =ServerActivities.SEND_START_GAME;
		stc.join(100);
		
		assertEquals("3;",client1.command);
		assertEquals("3;",client2.command);
		assertEquals("3;",client3.command);
		assertEquals("3;",client4.command);
		assertEquals("3;",client5.command);
		assertEquals("3;",client6.command);
		
		server.command_ms.game.create_Board();
		server.command_ms.activiti =ServerActivities.SEND_BOARD;
		stc.join(100);
		
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;86;87;"
				+ "88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;109;110;111;112;"
				+ "113;114;115;116;117;118;119;120",client1.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84"
				+ ";85;86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107"
				+ ";108;109;110;111;112;113;114;115;116;117;118;119;120",client2.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;"
				+ "85;86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;"
				+ "108;109;110;111;112;113;114;115;116;117;118;119;120",client3.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;"
				+ "86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;"
				+ "109;110;111;112;113;114;115;116;117;118;119;120",client4.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;"
				+ "86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;"
				+ "109;110;111;112;113;114;115;116;117;118;119;120",client5.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;"
				+ "86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;"
				+ "109;110;111;112;113;114;115;116;117;118;119;120",client6.command);
		
		server.command_ms.game.create_Queue();
		server.command_ms.activiti =ServerActivities.SEND_WHOSE_TURN;
		stc.join(100);
		
		System.out.println(server.command_ms.getCommand());
		assertEquals(server.command_ms.getCommand(),client1.command);
		assertEquals(server.command_ms.getCommand(),client2.command);
		assertEquals(server.command_ms.getCommand(),client3.command);
		assertEquals(server.command_ms.getCommand(),client4.command);
		assertEquals(server.command_ms.getCommand(),client5.command);
		assertEquals(server.command_ms.getCommand(),client6.command);
		
	
		
	//	assertEquals("Move",server.command_ms.game.getMove());
		server.command_ms.game.increase_Queue();
		
		server.command_ms.activiti =ServerActivities.SEND_BOARD;
		stc.join(100);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;86;87;"
				+ "88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;109;110;111;112;"
				+ "113;114;115;116;117;118;119;120",client1.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84"
				+ ";85;86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107"
				+ ";108;109;110;111;112;113;114;115;116;117;118;119;120",client2.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;"
				+ "85;86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;"
				+ "108;109;110;111;112;113;114;115;116;117;118;119;120",client3.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;"
				+ "86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;"
				+ "109;110;111;112;113;114;115;116;117;118;119;120",client4.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;"
				+ "86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;"
				+ "109;110;111;112;113;114;115;116;117;118;119;120",client5.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;"
				+ "86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;"
				+ "109;110;111;112;113;114;115;116;117;118;119;120",client6.command);
		server.command_ms.activiti =ServerActivities.SEND_WHOSE_TURN;
		stc.join(100);
		
		System.out.println(server.command_ms.getCommand());
		assertEquals(server.command_ms.getCommand(),client1.command);
		assertEquals(server.command_ms.getCommand(),client2.command);
		assertEquals(server.command_ms.getCommand(),client3.command);
		assertEquals(server.command_ms.getCommand(),client4.command);
		assertEquals(server.command_ms.getCommand(),client5.command);
		assertEquals(server.command_ms.getCommand(),client6.command);
		
		server.command_ms.activiti =ServerActivities.SEND_BOARD;
		stc.join(100);
		
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;86;87;"
				+ "88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;109;110;111;112;"
				+ "113;114;115;116;117;118;119;120",client1.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84"
				+ ";85;86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107"
				+ ";108;109;110;111;112;113;114;115;116;117;118;119;120",client2.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;"
				+ "85;86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;"
				+ "108;109;110;111;112;113;114;115;116;117;118;119;120",client3.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;"
				+ "86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;"
				+ "109;110;111;112;113;114;115;116;117;118;119;120",client4.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;"
				+ "86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;"
				+ "109;110;111;112;113;114;115;116;117;118;119;120",client5.command);
		assertEquals("4;61;62;63;64;65;66;67;68;69;70;71;72;73;74;75;76;77;78;79;80;81;82;83;84;85;"
				+ "86;87;88;89;90;91;92;93;94;95;96;97;98;99;100;101;102;103;104;105;106;107;108;"
				+ "109;110;111;112;113;114;115;116;117;118;119;120",client6.command);
		
		server.command_ms.game.set_winner(1);
		server.command_ms.activiti =ServerActivities.SEND_END_GAME;
		stc.join(100);
		
		assertEquals("6;1;",client1.command);
		assertEquals("6;1;",client2.command);
		assertEquals("6;1;",client3.command);
		assertEquals("6;1;",client4.command);
		assertEquals("6;1;",client5.command);
		assertEquals("6;1;",client6.command);
		}
		catch(Exception e) {		
			}
		
		assertTrue(true);
		
	}
}
