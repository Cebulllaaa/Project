package Project.client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

public class TestServer extends Thread {

	private Socket clientSocket;
	private ServerSocket serverSocket;
	private Scanner in;
	private PrintWriter out;

	public TestServer() throws IOException {
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

	public void listen() throws IOException {
		try {
			clientSocket = serverSocket.accept();
			in = new Scanner(clientSocket.getInputStream());
			out = new PrintWriter(clientSocket.getOutputStream());
		}
		finally {
			serverSocket.close();
		}
	}

	public void acceptClient(int id, int playersCount, GameType gameType, int[] pieces) throws WrongGameTypeException {
		out.print("1;");
		out.print(id);
		out.println();

		out.flush();

		out.print("2;");
		out.print(playersCount);
		out.print(";");
		out.print(GameHelperMethods.getGameCode(gameType));
		out.println();

		out.flush();

		out.print("3;");
		out.println();

		out.flush();

		out.print("4;");
		for (int i=0; i<pieces.length; i++) {
			out.print(";");
			out.print(pieces[i]);
		}
		out.println();

		out.flush();

	}

	public void continueGame(int id, int[] pieces) {
		out.print("5;");
		out.print(id);
		out.println();

		out.flush();

		out.print("4;");
		for (int i=0; i<pieces.length; i++) {
			out.print(";");
			out.print(pieces[i]);
		}
		out.println();

		out.flush();

	}

	public void endGame(int winner) {
		out.print("6;");
		out.print(winner);
		out.println();
		out.flush();

	}

	public Scanner getIn() {
		return in;
	}

}
