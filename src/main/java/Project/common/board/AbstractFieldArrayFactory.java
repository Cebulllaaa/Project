package Project.common.board;

import Project.common.exceptions.TooManyNeighboursException;

/**
 * klasa do tworzenia listy pol wykorzystywanych w planszy
 * @version 1.0
 */
public abstract class AbstractFieldArrayFactory {

	private Field[][] outerTriangles;
	private Field[] wholeBoard;
	private int triangleCounter;
	private int fieldCounter;

	public Field[] getNewFieldArray() throws TooManyNeighboursException {
		triangleCounter = 0;
		fieldCounter = 0;

		outerTriangles = new Field[6][];
		wholeBoard = new Field[getBoardSize()];

		for (int i=0; i<6; i++) {
			addTriangle();
		}

		makeHexagon();
		joinTriangHexag();
		putTriangles();

		return wholeBoard;

	}

	/**
	 * tworzy kolejny trojkat (ramie gwiazdy) z pustymi polami
	 * ostatnie {@link AbstractFieldArrayFactory#getEdgeLength() getEdgeLength()} - 1 pol
	 * musi sie jeszcze polaczyc z wewnetrznym szesciokatem
	 * @throws TooManyNeighboursException wyrzucany, tylko w przypadku bledu w implementacji
	 */
	protected void addTriangle() throws TooManyNeighboursException {
		int n = getEdgeLength();
		Piece typeOfHome = PieceHelperMethods.idToPiece(triangleCounter + 1);
		Field[] triangle = new Field[getTriangleSize()];

		if (n > 1) {
			triangle[0] = new Field(fieldCounter, Piece.NONE, typeOfHome);
			fieldCounter++;
		}

		int prevTriangSize = 1;
		// wypelniam kolejne wiersze trojkata
		for (int i=2; i<n; i++) {
			triangle[prevTriangSize] = new Field(fieldCounter, Piece.NONE, typeOfHome);
			fieldCounter++;

			/* 
			 * roznica miedzy adresem pola na poczatku poprzedniego wiersza w trojkacie
			 * a obecnym wynosi i-1
			 */
			Field.connectFields(triangle[prevTriangSize - i + 1], triangle[prevTriangSize]);

			// dodaje srodkowe pola w wierszu
			for (int j=1; j<i-1; j++) {
				makeRowInTriangle(triangle, prevTriangSize, typeOfHome, i, j);
			}

			prevTriangSize += i; // zaraz bedzie dodane i pol

			triangle[prevTriangSize - 1] = new Field(fieldCounter, Piece.NONE, typeOfHome);
			fieldCounter++;

			/* 
			 * roznica miedzy adresem pola na koncu poprzedniego wiersza w trojkacie
			 * a obecnym wynosi i
			 */
			Field.connectFields(triangle[prevTriangSize - 1 - i], triangle[prevTriangSize - 1]);

			if (i <= 2) {
				Field.connectFields(triangle[prevTriangSize - 2], triangle[prevTriangSize - 1]);
			}
			else {
				Field.connectFieldsBefore(triangle[prevTriangSize - 2], triangle[prevTriangSize - 1], 0);
			}

		}

		outerTriangles[triangleCounter] = triangle;
		triangleCounter++;

	}

	private void makeRowInTriangle(Field[] triangle, int prevTriangSize,
			Piece typeOfHome, int i, int j) throws TooManyNeighboursException {		
		triangle[prevTriangSize + j] = new Field(fieldCounter, Piece.NONE, typeOfHome);
		fieldCounter++;

		/* 
		 * roznica miedzy adresemi pol po prawej i po lewej w poprzednim wierszu w trojkacie
		 * a obecnym wynosi odpowiednio i-1 oraz i
		 */
		Field.connectFields(triangle[prevTriangSize + j - i + 1], triangle[prevTriangSize + j]);

		if (j == 1) {
			Field.connectFieldsBefore(triangle[prevTriangSize + j - i], triangle[prevTriangSize + j], 2);
			Field.connectFields(triangle[prevTriangSize + j], triangle[prevTriangSize + j - 1]);
		}
		else {
			Field.connectFields(triangle[prevTriangSize + j - i], triangle[prevTriangSize + j]);
			Field.connectFieldsBefore(triangle[prevTriangSize + j - 1], triangle[prevTriangSize + j], 0);
		}
//		Field.connectFields(triangle[prevTriangSize + j], triangle[prevTriangSize + j + 1]);

	}

	protected void makeHexagon() throws TooManyNeighboursException {
		int n = getEdgeLength();

		if (n > 0) {
			wholeBoard[0] = new Field(fieldCounter, Piece.NONE, Piece.NONE);
			fieldCounter++;
		}

		int prevHexagon = 1; // miejsce gdzie zaczyna sie obecne okrazenie (o 1 dalej niz koniec starego)

		Field[] prevToConnect = new Field[6];
		int[] indexToConnect = new int[6];

		for (int i=0; i<6; i++) {
			prevToConnect[i] = wholeBoard[0];
			indexToConnect[i] = 0;
		}

		int firstIndexInRow = prevHexagon;

		for (int i=1; i<n; i++) {
			for (int j=0; j<6; j++) {
				makeSingleRowInHexagon(prevToConnect, indexToConnect, prevHexagon, i, j);
			}

			prevHexagon += 6*i; // tyle pol bedzie dodane wliczajac ostatnie

			if (i > 1) {
				Field.connectFieldsBefore(wholeBoard[firstIndexInRow], wholeBoard[prevHexagon - 1], 1, 3);
				firstIndexInRow += 6 * (i-1);
			}
			else {
				Field.connectFieldsBefore(wholeBoard[prevHexagon - 1], wholeBoard[firstIndexInRow], 0);
			}

		}

	}

	private void makeSingleRowInHexagon(Field[] prevToConnect, int[] indexToConnect,
			int prevHexagon, int i, int j) throws TooManyNeighboursException {
		int index = prevHexagon + i*j;
		Field tempField = new Field(fieldCounter, Piece.NONE, Piece.NONE);
		fieldCounter++;

		Field.connectFields(tempField, prevToConnect[j]);

		if (j!=0) { // j!=0 -> to nie jest poczatek okrazenia
			Field.connectFieldsBefore(wholeBoard[index - 1], tempField, 0);
			// dolozenie nowego sasiada na samym poczatku
		}

		wholeBoard[index] = tempField;

		for (int k=1; k<i; k++) {
			tempField = new Field(fieldCounter, Piece.NONE, Piece.NONE);
			fieldCounter++;

			Field.connectFields(tempField, wholeBoard[indexToConnect[j] + k]);
			Field.connectFields(tempField, wholeBoard[indexToConnect[j] + k - 1]);
			Field.connectFieldsBefore(wholeBoard[index + k - 1], tempField, 0);

			wholeBoard[index + k] = tempField;

		}

		prevToConnect[j] = wholeBoard[index];
		indexToConnect[j] = index;

	}

	protected void joinTriangHexag() throws TooManyNeighboursException {
		Field[] fieldsToJoin = getFieldsToJoin();

		if (fieldsToJoin.length > 1) {
			fieldCounter = 0; // teraz przechowuje liczbe polaczonych pol brzegowych

			for (int i=0; i<6; i++) {
				joinSingleTriagToHexag(i, fieldsToJoin);
			}

		}

	}

	private void joinSingleTriagToHexag(int i, Field[] fieldsToJoin) throws TooManyNeighboursException {
		int n = getEdgeLength() - 1; // dlugosc boku zewn. trojkata jest o 1 mniejsza
		int lastRow = ((n-1)*n)/2;

		for (int j=0; j<n; j++) {
			if (j == 0) {
				Field.connectFields(fieldsToJoin[fieldCounter + 1], outerTriangles[i][lastRow + j]);
				Field.connectFieldsBefore(fieldsToJoin[fieldCounter], outerTriangles[i][lastRow + j], 0);
				fieldCounter++;

			}
			else {
				Field.connectFields(fieldsToJoin[fieldCounter], outerTriangles[i][lastRow + j]);
				fieldCounter++;
				Field.connectFields(fieldsToJoin[fieldCounter], outerTriangles[i][lastRow + j]);

			}

		}

	}

	private Field[] getFieldsToJoin() {
		int n = getEdgeLength();
		int numOfFieldsToJoin = (n>1) ? (6*(n-1)) : 0;
		int hexagonSize = getHexagonSize();
		Field[] fieldsToJoin = new Field[numOfFieldsToJoin + 1];
		// jesli n==1 to i tak nie bedzie zadnego laczenia
		// +1, bo na koncu robimy polaczenie z poczatkiem

		if (numOfFieldsToJoin > 0) {
			// ostatnie 6*(n-1) pol w wholeBoard jest brzegowych
			for (int i=1; i <= numOfFieldsToJoin; i++) {
				fieldsToJoin[numOfFieldsToJoin - i] = wholeBoard[hexagonSize - i];
			}

			// konczymy tym czym zaczelismy
			fieldsToJoin[numOfFieldsToJoin] = fieldsToJoin[0];

		}

		return fieldsToJoin;

	}

	protected void putTriangles() {
		int n = getHexagonSize();
		int t = getTriangleSize();
		int start = n;

		for (int i=0; i<6; i++) {
			putSingleTriangle(start, i);
			start += t;
		}

	}

	private void putSingleTriangle(int start, int i) {
		int t = getTriangleSize();

		for (int j=0; j<t; j++) {
			wholeBoard[start + j] = outerTriangles[i][j];
		}

	}

	/*
	 * plansza ma boki dlugosci n
	 * dziele plansze na zewnetrzne trojkaty o bokach n-1
	 * i wewnetrzny szesciokat o bokach n
	 */

	abstract public int getEdgeLength();

	public int getTriangleSize() {
		int n = getEdgeLength();
		return ((n-1)*n)/2;
	}

	public int getHexagonSize() {
		return 6 * getTriangleSize() + 1;
	}

	public int getBoardSize() {
		return 6 * getTriangleSize() + getHexagonSize();
	}

}
