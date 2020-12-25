package Project.server.main;

import java.net.Socket;

/*Watek odpowiadajacy za polaczenia serwera */
public class ThreadConnectionServer extends Thread{
	public Server serwer;
	public Socket client_socket;
	public CommandMaster command_ms;
	public int ID;
	public ThreadConnectionServer (Server x, Socket y, CommandMaster z) {
		this.serwer=x;
		this.client_socket = y;
		this.command_ms = z;
		this.ID =command_ms.game.Players.size() +1;
	}
	@Override
	public void run() {
		//while(true) {
			System.out.println("dzialam dla ID + " + ID);
	//	}
	}

}
