package Project.server.main;

import java.net.Socket;

public class StartServerConnection  extends Thread{

	public Server server;
	public StartServerConnection (Server x) {
		this.server=x;
		
	}
	@Override
	public void run() {
		server.estabilish_connection();
	}

}
