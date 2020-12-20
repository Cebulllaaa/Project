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

//	private static final String programName = "Client";
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

//		Client client = new Client();

		connectionToServer = new ClientTemporaryConnection(args[0], Integer.parseInt(args[1])); //this);
		frame = new BoardFrame(new StandartBoard());

		connectionToServer.setListener(frame);
		frame.setConnection(connectionToServer);

		//Thread t = new Thread(frame);

		connectionToServer.start();

		frame.setTitle("Chinese Checkers");
		frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//t.start();

		/*try {
			System.out.println(args[0]);
			//connectionToServer.connect(args[0], Integer.parseInt(args[1]));
			id = connectionToServer.getID();
			gameType = connectionToServer.getGameType();
			pieces = connectionToServer.getPieces();

			show();

			//

		}
		catch (WrongGameTypeException wgtx) {
			System.out.println(wgtx.getMessage());
		}
		catch (PlayerNotAllowedException pnax) {
			System.out.println(pnax.getMessage());
		}
		catch (IOException iox) {
			System.out.println(iox.getMessage());
		}
		catch (GameEndedException gex) {
			System.out.println(gex.getMessage());
		}
		finally {
			try {
				connectionToServer.endConnection();
			}
			catch (IOException iox) {
				System.out.println("Couldn't close the socket");
				System.out.println(iox.getMessage());
			}
		}

	}

	public static void show() {
		System.out.println(connectionToServer.getId());
		System.out.println(connectionToServer.getGameType());
		System.out.println(connectionToServer.getNumOfPlayerPieces());
		System.out.println(connectionToServer.getTotalNumOfPieces());
		int[][] pieces = connectionToServer.getPieces();
		for (int i=0; i<pieces.length; i++) {
			for (int j=0; j < pieces[i].length; j++) {
				System.out.println(connectionToServer.getPieces());
			}
		}*/

	}

}
