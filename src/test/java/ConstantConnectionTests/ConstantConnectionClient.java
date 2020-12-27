package ConstantConnectionTests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConstantConnectionClient extends Thread{
	int port;
	Socket socket;
	public String command;
	PrintWriter writer;
	InputStreamReader inputreader;
	BufferedReader reader;
	public int ID;
	public ConstantConnectionClient(int x, Socket y) {
		this.port =x;
		this.socket = y;
	}
	
	@Override
	public void run() {
		try {
			writer = new PrintWriter(socket.getOutputStream(),true);
			
			writer.println("-1");
			writer.flush(); 
			
			
			inputreader = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(inputreader);
			command = reader.readLine(); 
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
