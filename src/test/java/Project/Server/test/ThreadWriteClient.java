package Project.Server.test;

import java.io.PrintWriter;
import java.net.Socket;

import Project.server.main.Server;

public class ThreadWriteClient  extends Thread{
	String command;
	int port;
	@Override
	public void run() {
		try {
		Socket client_socket = new Socket("127.0.0.1",port);
		PrintWriter writer = new PrintWriter(client_socket.getOutputStream());
		writer.print(command);
		writer.flush();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

}