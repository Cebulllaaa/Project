package Project.client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import Project.client.GUI.BoardFrame;
import Project.client.exceptions.GameEndedException;
import Project.client.exceptions.PlayerNotAllowedException;
import Project.client.main.Client;
import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

public class ClientTemporaryConnection extends Thread {

	private int clientId = -1;
//	private int copyId;
	private GameType gameType;
	private int numOfPlayers = 0;
	private int numOfPlayerPieces = 0;
	private Socket clientSocket;
	private Scanner in;
	private PrintWriter out;
	private String[] serverMsg;
	private int[][] pieces;
	private boolean myTurn = false;
	private int changedPiece = 0;
	private int newPosOfChangedPiece = 0;
	private final int servMsgPiecesStart = 1;
	//private Client client;
	//private final int inGameServMsgPiecStart = 2;
	//private final int clientNotAllowedCode = -1;
	private final String regexDelim = ";";
	private String host;
	private int port;
	private BoardFrame listener;
	private boolean isMoveMade = true;//false;

	public ClientTemporaryConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * metoda powtarzajaca laczenie sie z serwerem az do konca gry
	 */
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(2000);
				connect();
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
	 *    w grze (musi byc znana na poczatku),
	 * w trakcie gry:
	 *    listy pozycji pionkow kolejnych graczy, id gracza, ktorego jest kolej
	 * na koniec:
	 *    id gracza ktory wygral
	 */

	/**
	 * metoda wykonujaca pojedyncze polaczenie z serwerem:
	 *    jesli potrzeba, to wysyla dane;
	 *    pobiera komende;
	 *    dekoduje komende;
	 *    jesli potrzeba, to wysyla dane;
	 *    konczy polaczenie
	 * @throws IOException
	 * @throws PlayerNotAllowedException
	 * @throws WrongGameTypeException
	 * @throws GameEndedException
	 * @throws InterruptedException
	 */
	public void connect() throws IOException, PlayerNotAllowedException, WrongGameTypeException,
	GameEndedException, InterruptedException {
		clientSocket = new Socket(host, port);

		try {
			in = new Scanner(clientSocket.getInputStream());
			out = new PrintWriter(clientSocket.getOutputStream());

			if (clientId == -1) {
				out.println(clientId);
				out.flush();
			}

			fetchInstruction();
			decodeInstruction();

			// listener.print();

		}
		finally {
			endConnection();
		}

	}

	private void fetchInstruction() throws InterruptedException {
		while (!in.hasNextLine()) Thread.sleep(50);
		serverMsg = in.nextLine().split(regexDelim); // instrukcja: instructionID;argumenty
/*
		for (String line : serverMsg) {
			System.out.println(line);
		}
*/
	}

	/**
	 * dekoduje i wykonuje instrukcje
	 * @throws PlayerNotAllowedException nie ma juz miejsc w grze
	 * @throws WrongGameTypeException
	 * @throws GameEndedException
	 */
	private void decodeInstruction() throws PlayerNotAllowedException, WrongGameTypeException, GameEndedException {
		switch (Integer.parseInt(serverMsg[0])) {
		case 1: // setID: 1;id
			setID();
			break;

		case 2: // daneGry: 2;liczbaGraczy;gameType
			setGameData();
			break;

		case 3: // startGame: 3;
			startGame();
			break;

		case 4: // updateBoard: 4;[lista polaczen kolejnych pionkow]
			setPieces();
			break;

		case 5: // setTurn: 5;idGraczaKtoregoJestKolej
			setTurn();
			break;

		case 6: // endGame: 6;idZwyciezcy
			endGame();
			break;

		default: // nie ma miejsc: -1;
			throw new PlayerNotAllowedException("There are enough many players in this game");
		}
	}

	private void setID() throws PlayerNotAllowedException {
		int id = Integer.parseInt(serverMsg[1]);
		if (id >= 1) {
			clientId = id;
		}
		else {
			throw new PlayerNotAllowedException("There are enough many players in this game");
		}
	}

	private void setGameData() throws WrongGameTypeException {
		numOfPlayers = Integer.parseInt( serverMsg[1] );
		gameType = GameHelperMethods.getGameType( Integer.parseInt( serverMsg[2] ) );
		numOfPlayerPieces = numberOfMyPieces();
	}

	private void startGame() {
		// --------------------------------------------
	}

	private void setTurn() {
		myTurn = (Integer.parseInt(serverMsg[1]) == clientId);
System.out.println(myTurn);
System.out.println(clientId);
System.out.println(serverMsg[1]);
		isMoveMade = false;
		write();
	}

	public void endConnection() throws IOException {
		if (clientSocket != null) {
			clientSocket.close();
		}
	}

	public void endGame() throws GameEndedException {
		throw new GameEndedException(serverMsg[1]);
	}

	/*
	 * w trakcie komunikacji:
	 * klient przesyla:
	 * id gracza ktory ma wykonac ruch,
	 * roznice pozycji jego pionkow
	 */
	/**
	 * wysylanie informacji o ruchu do serwera
	 */
	public void write() {
//		out.print(false);
//		out.print(regexDelim);
try {
	while (!isMoveMade) Thread.sleep(100);
}
catch (InterruptedException ix) {
	;
}
		isMoveMade = false;
		myTurn = false;
//out = new PrintWriter(System.out);
		out.print(1); // wyslanie 1;pozycja_poczatkowa;pozycja_koncowa
System.out.print(1);
		out.print(regexDelim);
System.out.print(regexDelim);

		out.print(clientId);
		out.print(regexDelim);
System.out.print(clientId);
System.out.print(regexDelim);

		out.print(changedPiece);
		out.print(regexDelim);
System.out.print(changedPiece);
System.out.print(regexDelim);

		out.print(newPosOfChangedPiece);
System.out.print(newPosOfChangedPiece);

		out.println();
		out.flush();
System.out.println();

	}

/*	public void quitGame() {
		out.print(true);
		out.flush();
	}
*/
	/*
	 * w trakcie komunikacji:
	 * serwer przesyla:
	 * informacje czy gra sie skonczyla (boolean), id gracza ktory ma wykonac ruch,
	 * listy pozycji pionkow kolejnych graczy
	 */
/*	public void read() throws GameEndedException {
		while (!in.hasNextLine()) ;
		serverMsg = in.nextLine().split(regexDelim); // endGame: 6;idZwyciezcy lub whoseTurn: 5;idWykonujacegoRuch

		boolean gameEnded = (Integer.parseInt(serverMsg[0]) == 6);
		if (gameEnded) {
			throw new GameEndedException(serverMsg[1]);
		}

		myTurn = (Integer.parseInt(serverMsg[1]) == clientId);

		while (!in.hasNextLine()) ;
		serverMsg = in.nextLine().split(regexDelim); // newBoard: 4;[lista_pozycji_pionkow]
		for (int i=0; i<serverMsg.length; i++) {
			System.out.println(serverMsg[i]);
		}
		setPieces();

	}*/

	private void setPieces() {
		pieces = new int[numOfPlayers][numOfPlayerPieces];

		for (int i=0; i < pieces.length; i++) {
			for (int j=0; j < pieces[i].length; j++) {
				pieces[i][j] = Integer.parseInt(serverMsg[i * numOfPlayerPieces + j + servMsgPiecesStart]);
System.out.println(pieces[i][j]);
			}
		}

		listener.setNewPieces();

	}

	private int numberOfMyPieces() throws WrongGameTypeException {
		return GameHelperMethods.getNumberOfPieces(gameType);

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

	public void setListener(BoardFrame frame) {
		listener = frame;
	}

	public void makeMove() {
		isMoveMade = true;
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

}
