package Project.common.board;

/**Klasa abstrakcyjna jej rozszerzenia to plansze do konkretnych gier */
public abstract class AbstractBoard {

	/**Lista pol */
	public Field[] fields;
	/**
	 * fabryka do inicjowania pola {@link AbstractBoard#fields fields}
	 */
	public AbstractFieldArrayFactory fieldArrayFactory;

	//public Fields

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
			}
		}

		return triangle;

	}

	public int getEdgeLength() {
		return fieldArrayFactory.getEdgeLength();
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

}
