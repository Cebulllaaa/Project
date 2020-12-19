package Project.client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

public class TestServer implements Runnable {

	protected Socket clientSocket;
	protected ServerSocket serverSocket;
	protected Scanner in;
	protected PrintWriter out;
	protected int repeat;
	protected int id;
	protected int playersCount;
	protected int gameCode;
	protected int[] pieces;

	public TestServer(int id, int playersCount, GameType gameType, int[] pieces, int moves) throws IOException, WrongGameTypeException {
		this.id = id;
		this.playersCount = playersCount;
		this.gameCode = GameHelperMethods.getGameCode(gameType);
		this.pieces = pieces;
		repeat = moves;
		serverSocket = new ServerSocket(8080);
	}

	public TestServer(int id, int playersCount, int gameCode, int[] pieces, int moves) throws IOException, WrongGameTypeException {
		this.id = id;
		this.playersCount = playersCount;
		this.gameCode = gameCode;
		this.pieces = pieces;
		repeat = moves;
		serverSocket = new ServerSocket(8080);
	}

	public void run() {
		try {
			listen();
			acceptClient();

			for (int i=0; i < repeat; i++) {
				pieces[i % pieces.length]++;
				continueGame(i%playersCount, pieces);
			}

			endGame(id);

			closeConnection();
		}
		catch (Exception x) {
			System.out.println(x.getMessage());
		}
	}

	public void listen() throws IOException {
		clientSocket = serverSocket.accept();
		in = new Scanner(clientSocket.getInputStream());
		out = new PrintWriter(clientSocket.getOutputStream());
	}

	public void acceptClient() {
		out.print("1;");
		out.print(id);
		out.println();

		out.flush();

		out.print("2;");
		out.print(playersCount);
		out.print(";");
		out.print(gameCode);
		out.println();

		out.flush();

		out.print("3;");
		out.println();

		out.flush();

		out.print("4");
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

		out.print("4");
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

	public void closeConnection() throws IOException {
		serverSocket.close();
		clientSocket.close();
	}

}
