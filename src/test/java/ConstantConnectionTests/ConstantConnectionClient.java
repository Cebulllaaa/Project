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
	boolean game;
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
			game = true;
			while(game) {
				command = reader.readLine(); 
				if(command.charAt(0) =='1' ) {
					ID = Character.getNumericValue(command.charAt(2));
				}
				if(command.charAt(0) =='5'  ) {
					if(Character.getNumericValue(command.charAt(2)) == ID) {
						writer.println("Move");
						writer.flush(); 
					}
					else {
						writer.println("-2");
						writer.flush();
					}
				}
				if(command.charAt(0) =='6'  ) {
					game = false;
				}
				
			}
			System.out.println("koncze gre");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
