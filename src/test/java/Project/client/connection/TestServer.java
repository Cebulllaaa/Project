package Project.client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TestServer extends Thread {

	private Socket clientSocket;
	private ServerSocket serverSocket;
	private Scanner in;
	private PrintWriter out;

	public TestServer() {
		serverSocket = new ServerSocket(8080);
	}

	@Override
	public void run() {
		try {
			listen();
		}
		catch (IOException iox) {
			System.exit(1);
		}
	}

	private void listen() throws IOException {
		try {
			clientSocket = serverSocket.accept();
			in = new Scanner(clientSocket.getInputStream());
			out = new PrintWriter(clientSocket.getOutputStream());
		}
		finally {
			serverSocket.close();
		}
	}

	public testAccept// TODO

}
