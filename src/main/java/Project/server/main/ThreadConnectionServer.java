package Project.server.main;

public class ThreadConnectionServer extends Thread{
	public Server serwer;
	public ThreadConnectionServer (Server x) {
		this.serwer=x;
	}
	@Override
	public void run() {
		serwer.estabilish_connection();
	}

}
