package Project.server.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
/*Watek odpowiadajacy za nasluchiwanie przez serwer danych przychodzacych */
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
System.out.println("czytam");
			Cmd = reader.readLine();
			CmdM.setCommand(getCmd());
			System.out.println("ustawilem"); ///////////// <--- to odkomentowalem
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
