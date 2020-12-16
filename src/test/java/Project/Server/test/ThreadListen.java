package Project.Server.test;

import Project.server.main.Server;

public class ThreadListen extends Thread {
	Server serwer;
	@Override
	public void run() {
		serwer.listen();
	}
}
