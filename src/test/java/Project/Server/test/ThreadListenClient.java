package Project.Server.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import Project.server.main.Server;

public class ThreadListenClient extends Thread{
	public Socket client_socket;
	public String command;
	public Server serwer;
	public int port_serwer;
	@Override
	public void run() {
		command ="";
		while(command.isEmpty()) {
			try {
			client_socket = new Socket("127.0.0.1",port_serwer);
			InputStreamReader inputreader = new InputStreamReader(client_socket.getInputStream());
			BufferedReader reader = new BufferedReader(inputreader);
			command = reader.readLine();
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}
