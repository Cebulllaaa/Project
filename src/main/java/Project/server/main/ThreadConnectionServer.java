package Project.server.main;

import java.net.Socket;

/*Watek odpowiadajacy za polaczenia serwera */
public class ThreadConnectionServer extends Thread{
	public Server serwer;
	public Socket client_socket;
	public ThreadConnectionServer (Server x, Socket y) {
		this.serwer=x;
		this.client_socket = y;
	}
	@Override
	public void run() {
		System.out.println("dzialam");
	}

}
