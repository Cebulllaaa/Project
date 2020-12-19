package Project.server.main;

public class ThreadConnectionServer extends Thread{
	public Server serwer;
	@Override
	public void run() {
		serwer.estabilish_connection();
	}

}
