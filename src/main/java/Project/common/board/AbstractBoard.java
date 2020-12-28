package Project.common.board;

/**Klasa abstrakcyjna jej rozszerzenia to plansze do konkretnych gier */
public abstract class AbstractBoard {

	/**Lista pol */
	public Field[] fields;
	/**
	 * fabryka do inicjowania pola {@link AbstractBoard#fields fields}
	 */
	public AbstractFieldArrayFactory fieldArrayFactory;

	private int[] pieces = null;

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

	public int[] getPiecesPositions() {
		return pieces;
	}

	public int getEdgeLength() {
		return fieldArrayFactory.getEdgeLength();
	}

	public int getTriangleSize() {
		return fieldArrayFactory.getTriangleSize();
	}

	public int getHexagonSize() {
		return fieldArrayFactory.getHexagonSize();
	}

	public int getBoardSize() {
		return fieldArrayFactory.getBoardSize();
	}

	/**
	 * sprawdzenie, czy zmiana pozycji pionka z field1 na field2
	 * nie narusza zasad
	 * @param field1 poczatkowe pole ruchu
	 * @param field2 koncowe ruchu
	 * @return true gdy ruch jest zgodny z zasadami, false w przeciwnym przypadku
	 */
	public boolean checkRules(int field1, int field2) {
		return checkPosition(field1) && checkNeighbouring(field1, field2);
	}

	/**
	 * sprwdzenie, czy sasiedztwo pol sie zgadza
	 * @param field1
	 * @param field2
	 * @return
	 */
	abstract protected boolean checkNeighbouring(int field1, int field2);

	/**
	 * sprwdzenie, czy sasiedztwo pol sie zgadza
	 * @param field1
	 * @param field2
	 * @return
	 */
	abstract protected boolean checkNeighbouring(Field field1, Field field2);

	/**
	 * sprawdzenie, czy miejsce sie zgadza
	 * @param field1
	 * @return
	 */
	abstract protected boolean checkPosition(int field1);

	/**
	 * sprawdzenie, czy miejsce sie zgadza
	 * @param field1
	 * @return
	 */
	abstract protected boolean checkPosition(Field field1);

	abstract public int checkWinner();

}
