package Project.server.main;
/*Watek odpowiadajacy za polaczenia serwera */
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
