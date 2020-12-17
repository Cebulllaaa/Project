package Project.Server.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadConnection extends Thread{
	public Socket client_socket;
	public int port_serwer;
	@Override
	public void run() {
		try {
			client_socket = new Socket("127.0.0.1",port_serwer);
			}
			catch(Exception e) {
				System.out.println(e);
			}
	}
}
