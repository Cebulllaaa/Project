package Project.client.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import Project.client.GUI.BoardFrame;
import Project.client.connection.ClientConnection;
import Project.client.connection.ClientTemporaryConnection;
import Project.client.exceptions.GameEndedException;
import Project.client.exceptions.PlayerNotAllowedException;
import Project.common.board.StandartBoard;
import Project.common.exceptions.ApplicationErrorException;
import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameType;

public class Client {

	private static ClientTemporaryConnection connectionToServer;
	private static int id;
	private static GameType gameType;
	private static int[][] pieces;
	private static BoardFrame frame;

	public static void main(String[] args) throws InterruptedException, ApplicationErrorException {
		if (args.length != 2) {
			System.err.println("Pass the server ip and socket as the command line argument");
			System.exit(1);
		}

		connectionToServer = new ClientTemporaryConnection(args[0], Integer.parseInt(args[1])); //this);
		frame = new BoardFrame(new StandartBoard());

		connectionToServer.setListener(frame);
		frame.setConnection(connectionToServer);

		connectionToServer.start();

		frame.setTitle("Chinese Checkers");
		frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
