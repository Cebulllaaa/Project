package Project.Server.test;

import java.io.PrintWriter;
import java.net.Socket;

import Project.server.main.Server;

public class ThreadWriteClient  extends Thread{
	public String command;
	public int port;
	@Override
	public void run() {
		try {
		
		Socket client_socket = new Socket("127.0.0.1",port);
		PrintWriter writer = new PrintWriter(client_socket.getOutputStream(),true);
		writer.print(command);
		writer.flush(); 
		writer.close();
		System.out.println("napisalem " + command);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

}
