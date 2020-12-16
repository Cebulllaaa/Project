package Project.Server.test;

import Project.server.main.Server;

public class ThreadWrite extends Thread{
	Server serwer;
	
	@Override
	public void run() {
		serwer.write();
	}
}
