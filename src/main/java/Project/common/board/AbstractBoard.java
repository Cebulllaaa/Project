package Project.common.board;

import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

/**Klasa abstrakcyjna jej rozszerzenia to plansze do konkretnych gier */
public abstract class AbstractBoard {

	/**Lista pol */
	public Field[] fields;
	/**
	 * fabryka do inicjowania pola {@link AbstractBoard#fields fields}
	 */
	public AbstractFieldArrayFactory fieldArrayFactory;

	private boolean[] hasWon;
	private int[] pieces = null;
	//private int numOfPlayers;

	public AbstractBoard() {
		hasWon = new boolean[6];
		for (int i=0; i < hasWon.length; i++) {
			hasWon[i] = false;
		}

	}

	/**
	 * poczatkowe ustawienie pionkow na planszy
	 * @param numOfPlayers liczba graczy zaczynajacych gre
	 */
	public void initPieces(int numOfPlayers) {

		int t = fieldArrayFactory.getTriangleSize();
		int h = fieldArrayFactory.getHexagonSize();

		pieces = new int[numOfPlayers * t];
/*
for (int i=0; i < pieces.length; i++) {
	Field f = fields[h + i];
	pieces[i] = h + i;
	f.setPieceOnField(f.getHomeType());
}
*/
		switch (numOfPlayers) {
			case 2:
				initTwoPlayers();
				break;

			case 3:
				initThreePlayers();
				break;

			case 4:
				initFourPlayers();
				break;

			case 6:
				initSixPlayers();
				break;

		}

	}

	/**
	 * ustawienie pionkow na planszy odpowiednie dla trybu dwoch graczy
	 */
	private void initTwoPlayers() {
		int index = 0;

		index = initPiecesInTriangle(0, index);
		initPiecesInTriangle(3, index);

	}

	/**
	 * ustawienie pionkow na planszy odpowiednie dla trybu trzech graczy
	 */
	private void initThreePlayers() {
		int index = 0;

		index = initPiecesInTriangle(0, index);
		index = initPiecesInTriangle(2, index);
		initPiecesInTriangle(4, index);

	}

	/**
	 * ustawienie pionkow na planszy odpowiednie dla trybu czterech graczy
	 */
	private void initFourPlayers() {
		int index = 0;

		index = initPiecesInTriangle(0, index);
		index = initPiecesInTriangle(1, index);
		index = initPiecesInTriangle(3, index);
		initPiecesInTriangle(4, index);

	}

	/**
	 * ustawienie pionkow na planszy odpowiednie dla trybu szesciu graczy
	 */
	private void initSixPlayers() {
		int index = 0;

		index = initPiecesInTriangle(0, index);
		index = initPiecesInTriangle(1, index);
		index = initPiecesInTriangle(2, index);
		index = initPiecesInTriangle(3, index);
		index = initPiecesInTriangle(4, index);
		initPiecesInTriangle(5, index);

	}

	/**
	 * ustawienie pionkow w podanym trojkacie
	 * @param triangleNumber numer trojkata, w ktorym sa ustawiane pionki
	 * @param piecesIndex najnizszy wolny indeks w tablicy {@link AbstractBoard#pieces pieces}
	 * @return nowy najnizszy wolny indeks w tablicy {@link AbstractBoard#pieces pieces}
	 */
	private int initPiecesInTriangle(int triangleNumber, int piecesIndex) {
		int t = getTriangleSize();
		int h = getHexagonSize();
		int baseIndex = h + t * triangleNumber;

		for (int i=0; i<t; i++) {
			Field field = fields[baseIndex + i];
			pieces[piecesIndex + i] = baseIndex + i;
			field.setPieceOnField( field.getHomeType() );

		}

		return piecesIndex + t;

	}

	/**
	 * zwraca i-ty trojkat
	 * @param i numer trojkata
	 * @return i-ty trojkatny rog planszy
	 */
	public Field[] getTriangle(int i) {

		Field[] triangle = new Field[fieldArrayFactory.getTriangleSize()];
		Piece type = PieceHelperMethods.idToPiece(i);
		int j=0;

		for (Field field : fields) {
			if (field.getHomeType() == type) {
				triangle[j] = field;
				j++;
			}
		}

		return triangle;

	}

	/**
	 * przenosi pionek z pola o indeksie field1 na pole o indeksie field2 (bez sprawdzania)
	 * @param field1 indeks pierwszego pola w {@link AbstractBoard#fields fields}
	 * @param field2 indeks drugiego pola w {@link AbstractBoard#fields fields}
	 */
	public void makeMove(int field1, int field2) {
		makeMove(fields[field1], fields[field2]);
	}

	/**
	 * przenosi pionek z pola field1 na pole field2 (bez sprawdzania)
	 * @param field1 indeks pierwszego pola w {@link AbstractBoard#fields fields}
	 * @param field2 indeks drugiego pola w {@link AbstractBoard#fields fields}
	 */
	public void makeMove(Field field1, Field field2) {
		Piece tempPiece = field1.getPiece();

		field1.setPieceOnField(field2.getPiece());
		field2.setPieceOnField(tempPiece);

		setPiecesPositions();

	}

	/**
	 * uaktualnia dane w {@link AbstractBoard#pieces pieces}
	 */
	public void setPiecesPositions() {
		try {
			int numOfPlayerPieces = GameHelperMethods.getNumberOfPieces( getGameType() );
			int numOfPlayers = pieces.length / numOfPlayerPieces;

			for (int i=0; i < numOfPlayers; i++) {
				int piecesSet = 0;
				int id = GameHelperMethods.positionInArrayToId(i, numOfPlayers);
				Piece playerPiece = PieceHelperMethods.idToPiece(id);

				for (int j=0; j < fields.length; j++) {
					if (playerPiece == fields[j].getPiece()) {
						pieces[i * numOfPlayerPieces + piecesSet] = j;
						piecesSet++;

					}

				}

			}

		}
		catch (WrongGameTypeException wgtx) {
			// unreachable
		}

	}

	/**
	 * getter pola pieces
	 * @return {@link AbstractBoard#pieces pieces}
	 */
	public int[] getPiecesPositions() {
		return pieces;
	}

	/**
	 * podaje dlugosc krawedzi planszy
	 * @return dlugosc krawedzi planszy
	 */
	public int getEdgeLength() {
		return fieldArrayFactory.getEdgeLength();
	}

	/**
	 * podaje liczbe pol w rogu gwiazdy
	 * @return liczba pol w rogu gwiazdy
	 */
	public int getTriangleSize() {
		return fieldArrayFactory.getTriangleSize();
	}

	/**
	 * podaje liczbe pol w wewnetrznym szesciokacie
	 * @return liczba pol w wewnetrznym szesciokacie
	 */
	public int getHexagonSize() {
		return fieldArrayFactory.getHexagonSize();
	}

	/**
	 * podaje liczbe pol na planszy
	 * @return liczba pol na planszy
	 */
	public int getBoardSize() {
		return fieldArrayFactory.getBoardSize();
	}

	/**
	 * sprawdzenie, czy zmiana pozycji pionka z field1 na field2
	 * nie narusza zasad
	 * @param field1 poczatkowe pole ruchu
	 * @param field2 koncowe pole ruchu
	 * @return true gdy ruch jest zgodny z zasadami, false w przeciwnym przypadku
	 */
	public boolean checkRules(int field1, int field2) {
		return field1 == field2 || (checkOccupation(field1, field2) && checkPosition(field1, field2) && checkNeighbouring(field1, field2));
	}

	protected boolean checkOccupation(int field1, int field2) {
		return checkOccupation(fields[field1], fields[field2]);
	}

	protected boolean checkOccupation(Field field1, Field field2) {
		return field1.getPiece() != Piece.NONE && field2.getPiece() == Piece.NONE;
	}

	/**
	 * sprwdzenie, czy sasiedztwo pol sie zgadza
	 * @param field1 index poczatkowego pola w tablicy {@link AbstractBoard#fields fields}
	 * @param field2 index koncowego pola w tablicy {@link AbstractBoard#fields fields}
	 * @return odpowiedz czy pionek wykonujac podany ruch skoczyl na sasiednie pole lub przeskoczyl inny pionek
	 */
	abstract protected boolean checkNeighbouring(int field1, int field2);

	/**
	 * sprwdzenie, czy sasiedztwo pol sie zgadza
	 * @param field1 poczatkowe pole ruchu
	 * @param field1 koncowe pole ruchu
	 * @return odpowiedz czy pionek wykonujac podany ruch skoczyl na sasiednie pole lub przeskoczyl inny pionek
	 */
	abstract protected boolean checkNeighbouring(Field field1, Field field2);

	/**
	 * sprawdzenie, czy miejsce sie zgadza
	 * @param field1 index poczatkowego pola w tablicy {@link AbstractBoard#fields fields}
	 * @param field2 index koncowego pola w tablicy {@link AbstractBoard#fields fields}
	 * @return odpowiedz czy pionek po wykonaniu podanego ruchu znajduje sie na dozwolonym polu
	 */
	abstract protected boolean checkPosition(int field1, int field2);

	/**
	 * sprawdzenie, czy miejsce sie zgadza
	 * @param field1 poczatkowe pole ruchu
	 * @param field1 koncowe pole ruchu
	 * @return odpowiedz czy pionek po wykonaniu podanego ruchu znajduje sie na dozwolonym polu
	 */
	abstract protected boolean checkPosition(Field field1, Field field2);

	/**
	 * sprawdzenie kto wygral
	 * @return id gracza, ktory wygral gre lub 0 gdy jeszcze nikt nie wygral
	 */
	public int checkWinner() {
		int[] piecesAtHome = new int[6];
		GameType gameType = getGameType();
		int numOfPlayerPieces = 0;

		try {
			numOfPlayerPieces = GameHelperMethods.getNumberOfPieces(gameType);
		}
		catch (WrongGameTypeException wgtx) {
			// unreachable
		}

		for (int i=0; i<6; i++) {
			piecesAtHome[i] = 0;
		}

		for (int index : pieces) {
			Field fieldOfPiece = fields[index];
			Piece piece = fieldOfPiece.getPiece();
			int ownerID = PieceHelperMethods.pieceToID(piece);
			Piece home = PieceHelperMethods.getHome(piece);

			if (fieldOfPiece.getHomeType() == home) {
				piecesAtHome[ownerID - 1]++;
			}

		}

		for (int i=0; i<6; i++) {
			if (piecesAtHome[i] == numOfPlayerPieces && !hasWon[i]) {
				hasWon[i] = true;
				return i + 1;
			}
		}

		return 0;

	}

	/**
	 * podaje aktualna wersje gry
	 * @return aktualnie uzywana wersja gry
	 */
	abstract public GameType getGameType();

}
