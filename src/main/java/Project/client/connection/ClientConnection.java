package Project.client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import Project.client.exceptions.GameEndedException;
import Project.client.exceptions.PlayerNotAllowedException;
import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

public class ClientConnection {

	private int clientId;
	private GameType gameType;
//	private int turn;
	private int numOfPlayers;
	private int numOfPlayerPieces;
	private Socket clientSocket;
	private Scanner in;
	private PrintWriter out;
	private String[] serverMsg;
	private int[][] pieces;
	private boolean myTurn = false;
	private int servMsgPiecesStart = 3;
	private final int inGameServMsgPiecStart = 2;
	private final int clientNotAllowedCode = 7;
	private final String regexDelim = ";";

	/* zalozenie: serwer sie nie myli i przekaze zawsze odpowiednia liste wartosci
	 * na poczatku komunikacji:
	 *    serwer przesyla po kolei:
	 *    id klienta, typ gry (int), liczbe wszystkich graczy
	 *    w grze (musi byc znana na poczatku), listy pozycji pionkow kolejnych graczy
	 */
	public void connect(String host, int port) throws IOException, PlayerNotAllowedException, WrongGameTypeException {
		clientSocket = new Socket(host, port);

		in = new Scanner(clientSocket.getInputStream());
		out = new PrintWriter(clientSocket.getOutputStream());

		serverMsg = in.nextLine().split(regexDelim);

		clientId = Integer.parseInt(serverMsg[0]);

		if (clientId == clientNotAllowedCode) {
			throw new PlayerNotAllowedException("There are enough many players in this game");
		}

		try {
			gameType = GameHelperMethods.getGameType( Integer.parseInt(serverMsg[1]) );

			numOfPlayers = Integer.parseInt(serverMsg[2]);

			numOfPlayerPieces = numberOfMyPieces();
			pieces = new int[numOfPlayerPieces][numOfPlayers];
			setPieces();

			servMsgPiecesStart = inGameServMsgPiecStart;

		}
		catch (WrongGameTypeException wgtx) {
			throw new WrongGameTypeException("Error: Server sent wrong type of game");
		}

	}

	public void endConnection() throws IOException {
		clientSocket.close();
	}

	/*
	 * w trakcie komunikacji:
	 * klient przesyla:
	 * informacje czy gra sie skonczyla (boolean), id gracza ktory ma wykonac ruch,
	 * listy pozycji pionkow kolejnych graczy
	 */
	public void write() {
		out.print(false);
		out.print(regexDelim);
		out.print(clientId);

		for (int[] playerPieces : pieces) {
			for (int piece : playerPieces) {
				out.print(regexDelim);
				out.print(piece);

			}
		}

		out.flush();

	}

	public void quitGame() {
		out.print(true);
		out.flush();
	}

	/*
	 * w trakcie komunikacji:
	 * serwer przesyla:
	 * informacje czy gra sie skonczyla (boolean), id gracza ktory ma wykonac ruch,
	 * listy pozycji pionkow kolejnych graczy
	 */
	public void read() throws GameEndedException {
		serverMsg = in.nextLine().split(regexDelim);

		boolean gameEnded = Boolean.parseBoolean(serverMsg[0]);
		if (gameEnded) {
			throw new GameEndedException("The game has ended");
		}

		myTurn = (Integer.parseInt(serverMsg[1]) == clientId);

		setPieces();

	}

	private void setPieces() {
		for (int i=0; i < pieces.length; i++) {
			for (int j=0; j < pieces[i].length; j++) {
				pieces[i][j] = Integer.parseInt(serverMsg[i + servMsgPiecesStart]);
			}
		}
	}

	private int numberOfMyPieces() throws WrongGameTypeException {
		try {
			return GameHelperMethods.getNumberOfPieces(gameType);
		}
		catch (WrongGameTypeException wgtx) {
			throw new WrongGameTypeException("Error: Server sent wrong type of game");
		}

	}

	public int getId() {
		return clientId;
	}

	public GameType getGameType() {
		return gameType;
	}

	/* zbyt niebezpieczne
	public Scanner getIn() {
		return in;
	}
	*/

	/* zbyt niebezpieczne
	public PrintWriter getOut() {
		return out;
	}
	*/

	/* zbyt niebezpieczne
	public String[] getServerMesssage() {
		return serverMsg;
	}
	*/

	public int[][] getPieces() {
		return pieces;
	}

	public int getTotalNumOfPieces() {
		return numOfPlayers * numOfPlayerPieces;
	}

	public int getNumOfPlayerPieces() {
		return numOfPlayerPieces;
	}

	public boolean isMyTurn() {
		return myTurn;
	}

}
