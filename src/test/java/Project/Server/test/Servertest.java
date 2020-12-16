package Project.Server.test;
import static org.junit.Assert.assertEquals;
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
		//W przypadku wprowadzenia nowych trybow gry , do zmiany
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
		Server serwer = new Server(6003);
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
			write.port = 6003;
			write.command=test;
			//listen.start();
		//	write.start();
		//	write.start();
			Socket client_socket = new Socket("127.0.0.1",6003);
			PrintWriter writer = new PrintWriter(client_socket.getOutputStream());
			writer.print(test);
			writer.flush();
			serwer.listen();
			assertEquals (test,serwer.command);
			System.out.println("Hmmm" + serwer.command);
	/*		serwer.listen();
			writer.print(test2);
			writer.flush();
			assertEquals(serwer.command,test2);
			serwer.listen();
			writer.print(test2);
			writer.flush();
			assertEquals(serwer.command,test3); */
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	} 
	/*W ramach tesu uzyje komunikacji pomiedzy dwoma serwerami 
	@Test
	public void writeServertest() {
		Server serwer = new Server(6002);
		serwer.create_Server();
		Server serwer2  = new Server(6003);
		serwer.create_Server();
		Server serwer3 = new Server(6004);
		serwer3.create_Server();
		String test = "TEST";
		String test2 = "Instruction from Server";
		String test3 = "Other instruction from Server";
		try {
			serwer2.client_socket = new Socket("127.0.0.1",6002);
			serwer3.client_socket = new Socket("127.0.0.1",6002);
			serwer2.listen();
			serwer3.listen();
			serwer.command = test;
			serwer.write();
			assertEquals(serwer2.command, test);
			assertEquals(serwer3.command,test);
			serwer2.listen();
			serwer3.listen();
			serwer.command = test2;
			serwer.write();
			assertEquals(serwer2.command,test2);
			assertEquals(serwer3.command,test);
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	} */ 
}
