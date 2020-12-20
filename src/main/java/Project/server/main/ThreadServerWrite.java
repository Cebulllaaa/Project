package Project.server.main;

import java.io.PrintWriter;
import java.net.Socket;
/*Watek sluzacy do wysylania przez serwer informacji do klienta */
public class ThreadServerWrite extends Thread{
	public Socket client_socket;
	public PrintWriter writer;
	public String command;
	public ThreadServerWrite(Socket x , String y) {
		this.client_socket = x;
		this.command = y;
		try
		{writer = new PrintWriter(client_socket.getOutputStream());}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	@Override
	public void run() {
		writer.print(command);
		writer.flush();
		writer.close();
	//	System.out.println("napisal");
	}
	
}
