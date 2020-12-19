package Project.Server.test;

import Project.server.main.Server;

public class ThreadConnectionServer extends Thread{
	public Server serwer;
	@Override
	public void run() {
		serwer.estabilish_connection();
	}

}
