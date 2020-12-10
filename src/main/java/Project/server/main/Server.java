package Project.server.main;

import java.io.*;
import java.net.*;
/**G³ówna klasa odpowiadjaca za dzia³anie serwera*/
public class Server {
	
	public ServerSocket socket;
	public BufferedReader reader;
	public PrintWriter writer;
	public Socket client;
	
	public static int port;
	public Server(int x) {
		this.port=x;
	}
	public void create_Server() {
		try {
			socket = new ServerSocket(this.port);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void listen() {
		/*
		 * TODO
		 */
	}
	public void write() {
		/*
		 * TODO
		 */
	}
	
}
