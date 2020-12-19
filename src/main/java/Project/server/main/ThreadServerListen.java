package Project.server.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadServerListen extends Thread {
	public Socket socket;
	public BufferedReader reader;
	public InputStreamReader inputreader;
	private String Cmd;
	private CommandMaster CmdM;
	public ThreadServerListen(Socket x, CommandMaster y) {
		try {
			this.socket = x;
			inputreader = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(inputreader);
			this.CmdM = y;
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
			Cmd = reader.readLine();
			CmdM.setCommand(getCmd());
		//	System.out.println("ustawilem");
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
