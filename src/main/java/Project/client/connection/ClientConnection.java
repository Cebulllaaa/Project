package Project.client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import Project.client.GUI.BoardFrame;
import Project.client.GUI.ConnectionListener;
import Project.client.exceptions.GameEndedException;
import Project.client.exceptions.PlayerNotAllowedException;
import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

/**
 * klasa odpowiadajaca za komunikacje z serwerem
 * @version 1.0
 *
 */
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
	private final int clientNotAllowedCode = 7;
	private final String regexDelim = ";";
	private ConnectionListener listener;
	private boolean isMoveMade = true;
	private String host;
	private int port;

	/**
	 * jedyny konsturktor klasy
	 * @param host adres serwera
	 * @param port numer portu
	 */
	public ClientConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * metoda do obslugi polaczenia w nowym watku
	 */
	@Override
	public void run() {
		try {
			try {
				connect(host, port);

				while (true) {
					read();
					write();
				}
			}
			finally {
				endConnection();
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
	/**
	 * metoda laczaca klienta z serwerem
	 * wykonuje inicjowanie tych pol, ktore sa zalezne od rodzaju gry
	 * @param host adres serwera
	 * @param port numer portu
	 * @throws IOException wyrzucany, gdy wydarzy sie blad w polaczeniu
	 * @throws PlayerNotAllowedException wyrzucany, gdy gra juz wystarczajaco duzo graczy
	 * @throws WrongGameTypeException wyrzucany, gdy serwer przesle neprawidlowy kod gry
	 * @throws GameEndedException wyrzucany, gdy ktorys z graczy osiagnie cel gry
	 */
	public void connect(String host, int port) throws IOException, PlayerNotAllowedException, WrongGameTypeException, GameEndedException {
		clientSocket = new Socket(host, port);

		in = new Scanner(clientSocket.getInputStream());
		out = new PrintWriter(clientSocket.getOutputStream());

		out.println("-1");
		out.flush();

		serverMsg = in.nextLine().split(regexDelim); // setID: 1;idGracza
		for (int i=0; i<serverMsg.length; i++) {
			System.out.println(serverMsg[i]);
		}

		clientId = Integer.parseInt(serverMsg[1]);

		if (clientId == clientNotAllowedCode) {
			throw new PlayerNotAllowedException("There are enough many players in this game");
		}

		serverMsg = in.nextLine().split(regexDelim); // daneGry: 2;liczbaGraczy;gameType
		for (int i=0; i<serverMsg.length; i++) {
			System.out.println(serverMsg[i]);
		}

		try {
			gameType = GameHelperMethods.getGameType( Integer.parseInt(serverMsg[2]) );
			numOfPlayers = Integer.parseInt(serverMsg[1]);

			numOfPlayerPieces = numberOfMyPieces();

			serverMsg = in.nextLine().split(regexDelim); // startGame: 3;
			for (int i=0; i<serverMsg.length; i++) {
				System.out.println(serverMsg[i]);
			}

			pieces = new int[numOfPlayers][numOfPlayerPieces];

		}
		catch (WrongGameTypeException wgtx) {
			throw new WrongGameTypeException("Error: Server sent wrong type of game");
		}

	}

	/**
	 * konczenie polaczenia
	 * @throws IOException
	 */
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
	/**
	 * metoda wysylajaca dane gry do serwera
	 */
	public void write() {
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
			out.print("-2");
System.out.print("-2");
		}

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
	/**
	 * metoda odbierajaca dane od serwera
	 * @throws GameEndedException
	 */
	public void read() throws GameEndedException {
		serverMsg = in.nextLine().split(regexDelim); // newBoard: 4;[lista_pozycji_pionkow]
		for (int i=0; i<serverMsg.length; i++) {
			System.out.println(serverMsg[i]);
		}
		setPieces();

		serverMsg = in.nextLine().split(regexDelim); // endGame: 6;idZwyciezcy lub whoseTurn: 5;idWykonujacegoRuch
		for (int i=0; i<serverMsg.length; i++) {
			System.out.println(serverMsg[i]);
		}

		boolean gameEnded = (Integer.parseInt(serverMsg[0]) == 6);

		if (!gameEnded) {

			myTurn = (Integer.parseInt(serverMsg[1]) == clientId);

			if (myTurn) {
				listener.informAboutTurn();
			}

			isMoveMade = false;

		}
		else if (gameEnded) {
System.out.println("game-Ended:");
System.out.println(serverMsg[0]);
System.out.println(serverMsg[1]);
System.out.println(serverMsg[2]);
			int winnerID = Integer.parseInt(serverMsg[1]);//
			int remainingPlayers = Integer.parseInt(serverMsg[2]);//

			if (remainingPlayers <= 1) { // jesli zostal 1 gracz to gra skonczona
				throw new GameEndedException("The game has ended.\n");//serverMsg[1]);
			}
			else {
				if (winnerID == clientId) {
					listener.informAboutWinning(numOfPlayers - remainingPlayers);
				}

				read();

			}

		}

	}

	/**
	 * metoda ustawiajaca pionki na planszy wedlug odebranych informacji
	 */
	private void setPieces() {
		for (int i=0; i < pieces.length; i++) {
			for (int j=0; j < pieces[i].length; j++) {
				pieces[i][j] = Integer.parseInt(serverMsg[i * numOfPlayerPieces + j + servMsgPiecesStart]);
			}
		}

		listener.setNewPieces();

	}

	/**
	 * podaje liczbe pionkow gracza
	 * @return liczba pionkow gracza
	 * @throws WrongGameTypeException wyrzucany, gdy typ gry nie jest rozpoznawany (nie powinno byc tak nigdy_
	 */
	private int numberOfMyPieces() throws WrongGameTypeException {
		return GameHelperMethods.getNumberOfPieces(gameType);
	}

	/**
	 * getter clientID
	 * @return clientID
	 */
	public int getID() {
		return clientId;
	}

	/**
	 * getter gameType
	 * @return gameType
	 */
	public GameType getGameType() {
		return gameType;
	}

	/**
	 * metoda ustawiajaca informacje o zmianie na planszy, ktore maja byc wyslane
	 */
	public void setChange(int chPiece, int newPiecePos) {
		changedPiece = chPiece;
		newPosOfChangedPiece = newPiecePos;
	}

	/**
	 * getter pieces
	 * @return pieces
	 */
	public int[][] getPieces() {
		return pieces;
	}

	/**
	 * zwraca calkowita liczbe pionkow na planszy
	 * @return calkowita liczba pionkow na planszy
	 */
	public int getTotalNumOfPieces() {
		return numOfPlayers * numOfPlayerPieces;
	}

	/**
	 * getter numOfPlayerPieces
	 * @return numOfPlayerPieces
	 */
	public int getNumOfPlayerPieces() {
		return numOfPlayerPieces;
	}

	/**
	 * getter numOfPlayers
	 * @return numOfPlayers
	 */
	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	/**
	 * informuje, czy teraz rusza sie uzytkownik
	 * @return myTurn
	 */
	public boolean isMyTurn() {
		return myTurn;
	}

	/**
	 * tymczasowo wlacza wysylanie informacji do serwera
	 */
	public void makeMove() {
		isMoveMade = true;
	}

	/**
	 * ustawia obiekt sluchajacy informacji od serwera
	 */
	public void setListener(ConnectionListener listeningFrame) {
		listener = listeningFrame;
	}

}
