package Project.server.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*Watek odpowiadajacy za polaczenia serwera */
public class ThreadConnectionServer extends Thread{
	public Server serwer;
	public Socket client_socket;
	public CommandMaster command_ms;
	public BufferedReader reader;
	public InputStreamReader inputreader;

	
	public int ID;
	public ThreadConnectionServer (Server x, Socket y, CommandMaster z) {
		this.serwer=x;
		this.client_socket = y;
		this.command_ms = z;
		this.ID =command_ms.game.Players.size() +1;
	}
	@Override
	public void run() {
		try {
			/*Nasluchiwanie od klienta prosby o ID */
			command_ms.activiti = ServerActivities.LISTEN;
			inputreader = new InputStreamReader(client_socket.getInputStream());
			reader = new BufferedReader(inputreader);
			command_ms.setCommand(reader.readLine());
			
			this.yield();
			
			/*Wysylanie do klienta jego ID*/
			if(command_ms.getCommand().equals("-1")) {
				command_ms.activiti = ServerActivities.SEND_ID;
				command_ms.CommandMenu();
				ThreadServerWrite write = new ThreadServerWrite(client_socket,command_ms.getCommand());
				write.run();
				this.yield();
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		while(true) {
			
		}
	}

}
