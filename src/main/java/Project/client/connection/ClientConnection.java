package Project.client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import Project.client.GUI.BoardFrame;
import Project.client.exceptions.GameEndedException;
import Project.client.exceptions.PlayerNotAllowedException;
import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

public class ClientConnection extends Thread implements Connection {

	private int clientId;
	private GameType gameType;
	private int numOfPlayers;
	private int numOfPlayerPieces;
	private Socket clientSocket;
	private Scanner in;
	private PrintWriter out;
	private String[] serverMsg;
	private int[][] pieces;
	private boolean myTurn = false;
	private int changedPiece;
	private int newPosOfChangedPiece;
	private final int servMsgPiecesStart = 1;
	//private Client client;
	//private final int inGameServMsgPiecStart = 2;
	private final int clientNotAllowedCode = 7;
	private final String regexDelim = ";";
	private BoardFrame listener;
	private boolean isMoveMade = true;
	private String host;
	private int port;

	public ClientConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			connect(host, port);

			while (true) {
				read();
				write();
			}

		}
		catch (Exception x) {
			listener.signalise(x);
		}
	}

	/* zalozenie: serwer sie nie myli i przekaze zawsze odpowiednia liste wartosci
	 * na poczatku komunikacji:
	 *    serwer przesyla po kolei:
	 *    id klienta, typ gry (int), liczbe wszystkich graczy
	 *    w grze (musi byc znana na poczatku), listy pozycji pionkow kolejnych graczy
	 */
	public void connect(String host, int port) throws IOException, PlayerNotAllowedException, WrongGameTypeException, GameEndedException {
		clientSocket = new Socket(host, port);

		in = new Scanner(clientSocket.getInputStream());
		out = new PrintWriter(clientSocket.getOutputStream());

		out.println("-1");
		out.flush();
//Thread.sleep(100);
		while (!in.hasNextLine()) ;
//		System.out.println(in.nextLine());
		//throw new GameEndedException("EndGame");
		serverMsg = in.nextLine().split(regexDelim); // setID: 1;idGracza
		for (int i=0; i<serverMsg.length; i++) {
			System.out.println(serverMsg[i]);
		}

		clientId = Integer.parseInt(serverMsg[1]);

		if (clientId == clientNotAllowedCode) {
			throw new PlayerNotAllowedException("There are enough many players in this game");
		}

		while (!in.hasNextLine()) ;
		serverMsg = in.nextLine().split(regexDelim); // daneGry: 2;liczbaGraczy;gameType
		for (int i=0; i<serverMsg.length; i++) {
			System.out.println(serverMsg[i]);
		}

		try {
			gameType = GameHelperMethods.getGameType( Integer.parseInt(serverMsg[2]) );
			numOfPlayers = Integer.parseInt(serverMsg[1]);

			numOfPlayerPieces = numberOfMyPieces();

			while (!in.hasNextLine()) ;
			serverMsg = in.nextLine().split(regexDelim); // startGame: 3;
			for (int i=0; i<serverMsg.length; i++) {
				System.out.println(serverMsg[i]);
			}

			pieces = new int[numOfPlayers][numOfPlayerPieces];
/*
			while (!in.hasNextLine()) ;
			serverMsg = in.nextLine().split(regexDelim); // newBoard: 4;[lista_pozycji_pionkow]
			for (int i=0; i<serverMsg.length; i++) {
				System.out.println(serverMsg[i]);
			}
			pieces = new int[numOfPlayers][numOfPlayerPieces];
			setPieces();

			isMoveMade = false;
*/
		}
		catch (WrongGameTypeException wgtx) {
			throw new WrongGameTypeException("Error: Server sent wrong type of game");
		}

	}

	private void readNextLine() {
		
	}

	public void endConnection() throws IOException {
		if (clientSocket != null) {
			clientSocket.close();
		}
	}

	/*
	 * w trakcie komunikacji:
	 * klient przesyla:
	 * informacje czy gra sie skonczyla (boolean), id gracza ktory ma wykonac ruch,
	 * listy pozycji pionkow kolejnych graczy
	 */
	public void write() {
//		out.print(false);
//		out.print(regexDelim);

		if (myTurn) {
			try {
				while (!isMoveMade) Thread.sleep(100);
			}
			catch (InterruptedException ix) {
				;
			}
			out.print(1); // wyslanie 1;pozycja_poczatkowa;pozycja_koncowa
System.out.print(1); // wyslanie 1;pozycja_poczatkowa;pozycja_koncowa
			out.print(regexDelim);
System.out.print(regexDelim);

			out.print(changedPiece);
System.out.print(changedPiece);
			out.print(regexDelim);
System.out.print(regexDelim);

			out.print(newPosOfChangedPiece);
System.out.print(newPosOfChangedPiece);

		}
		else {
			out.println("-2");
System.out.println("-2");
		}
/*		for (int[] playerPieces : pieces) {
			for (int piece : playerPieces) {
				out.print(regexDelim);
				out.print(piece);

			}
		}
*/
		out.println();
System.out.println();
		out.flush();

	}

	/*
	 * w trakcie komunikacji:
	 * serwer przesyla:
	 * informacje czy gra sie skonczyla (boolean), id gracza ktory ma wykonac ruch,
	 * listy pozycji pionkow kolejnych graczy
	 */
	public void read() throws GameEndedException {
		while (!in.hasNextLine()) ;
		serverMsg = in.nextLine().split(regexDelim); // newBoard: 4;[lista_pozycji_pionkow]
		for (int i=0; i<serverMsg.length; i++) {
			System.out.println(serverMsg[i]);
		}
		setPieces();

		while (!in.hasNextLine()) ;
		serverMsg = in.nextLine().split(regexDelim); // endGame: 6;idZwyciezcy lub whoseTurn: 5;idWykonujacegoRuch
		for (int i=0; i<serverMsg.length; i++) {
			System.out.println(serverMsg[i]);
		}

		boolean gameEnded = (Integer.parseInt(serverMsg[0]) == 6);
		if (gameEnded) {
			throw new GameEndedException(serverMsg[1]);
		}

		myTurn = (Integer.parseInt(serverMsg[1]) == clientId);

		isMoveMade = false;

	}

	private void setPieces() {
		for (int i=0; i < pieces.length; i++) {
			for (int j=0; j < pieces[i].length; j++) {
				pieces[i][j] = Integer.parseInt(serverMsg[i * numOfPlayerPieces + j + servMsgPiecesStart]);
			}
		}

		listener.setNewPieces();

	}

	private int numberOfMyPieces() throws WrongGameTypeException {
//		try {
		return GameHelperMethods.getNumberOfPieces(gameType);
/*		}
		catch (WrongGameTypeException wgtx) { // unreachable
			throw new WrongGameTypeException("Error: Server sent wrong type of game");
		}
*/
	}

	public int getID() {
		return clientId;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setChange(int chPiece, int newPiecePos) {
		changedPiece = chPiece;
		newPosOfChangedPiece = newPiecePos;
	}

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

	public void makeMove() {
		isMoveMade = true;
	}

	public void setListener(BoardFrame listeningFrame) {
		listener = listeningFrame;
	}

}
