package Project.Server.test;

import Project.server.main.Server;

public class ThreadListen extends Thread {
	public Server serwer;
	@Override
	public void run() {
		serwer.listen2();
	}
}
