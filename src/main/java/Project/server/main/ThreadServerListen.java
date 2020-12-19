package Project.server.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadServerListen extends Thread {
	public Socket socket;
	public BufferedReader reader;
	public InputStreamReader inputreader;
	private String Cmd;
	public ThreadServerListen(Socket x) {
		try {
			inputreader = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(inputreader);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	public String getCmd() {
		return Cmd;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Dotarlem tu");
			Cmd = reader.readLine();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
