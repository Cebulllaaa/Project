package Project.common.board;

import java.util.LinkedList;
import java.util.Queue;

import Project.common.exceptions.ApplicationErrorException;
import Project.common.exceptions.FieldNotFoundException;
import Project.common.exceptions.TooManyNeighboursException;
import Project.common.game.GameType;

public class StandartBoard extends AbstractBoard {

	/**
	 * calkowita liczba pol
	 */
	public static final int TOTAL_NUMBER_OF_FIELDS = 121;
	//private boolean[] searchedFields = new boolean[TOTAL_NUMBER_OF_FIELDS];

	public StandartBoard() throws ApplicationErrorException {
		fieldArrayFactory = new StandartFieldArrayFactory();

		try {
			fields = fieldArrayFactory.getNewFieldArray();
		}
		catch (TooManyNeighboursException tmnx) {
			throw new ApplicationErrorException("Error: The application does not work properly");
		}

	}

	/**
	 * sprwdzenie, czy sasiedztwo pol sie zgadza
	 * @param posF1 indeks poczatkowego pola w tablicy {@link AbstractBoard#fields fields}
	 * @param posF2 indeks koncowego pola w tablicy {@link AbstractBoard#fields fields}
	 * @return odpowiedz czy pionek wykonujac podany ruch skoczyl na sasiednie pole lub przeskoczyl inny pionek
	 */
	@Override
	protected boolean checkNeighbouring(int posF1, int posF2) {
		return checkNeighbouring(fields[posF1], fields[posF2]);
	}

	/**
	 * sprwdzenie, czy sasiedztwo pol sie zgadza
	 * @param field1 poczatkowe pole ruchu
	 * @param field1 koncowe pole ruchu
	 * @return odpowiedz czy pionek wykonujac podany ruch skoczyl na sasiednie pole lub przeskoczyl inny pionek
	 */
	@Override
	protected boolean checkNeighbouring(Field field1, Field field2) {
		Field[] field1Neighbours = field1.getNeighbours();

		for (int i=0; i < field1Neighbours.length; i++) {
			if (field1Neighbours[i] == field2) {
				return true;
			}
		}

		Queue<Field> queue = new LinkedList<Field>();
		boolean[] searchedFields = new boolean[TOTAL_NUMBER_OF_FIELDS]; // true w polach, w ktorych nie znaleziono field2

		for (int i=0; i < searchedFields.length; i++) {
			searchedFields[i] = false;
		}

		searchedFields[ field1.getFieldId() ] = true;

		queue.add(field1);

		while (!queue.isEmpty()) { // szukanie BFS po calej planszy pol na ktore wolno przeskoczyc
			Field currentField = queue.remove();
			Field[] neighbours = currentField.getNeighbours();

			try {
				for (Field neighbour : neighbours) {
					Field opposition = neighbour.oppositeField(currentField);

					if (opposition != null && neighbour.getPiece() != Piece.NONE && !searchedFields[ opposition.getFieldId() ]
							&& opposition.getPiece() == Piece.NONE) {
						if (field2 == opposition) {
							return true;
						}
						else {
							queue.add(opposition);
							searchedFields[ opposition.getFieldId() ] = true;
						}

					}

				}

			}
			catch (FieldNotFoundException fnfx) {
				System.out.println("Critical error");
				System.exit(1);

			}

		}

		return false;

	}

	/**
	 * sprawdzenie, czy miejsce sie zgadza
	 * @param f1 index poczatkowego pola w tablicy {@link AbstractBoard#fields fields}
	 * @param f2 index koncowego pola w tablicy {@link AbstractBoard#fields fields}
	 * @return odpowiedz czy pionek po wykonaniu podanego ruchu znajduje sie na dozwolonym polu
	 */
	@Override
	protected boolean checkPosition(int f1, int f2) {
		return checkPosition(fields[f1], fields[f2]);
	}

	/**
	 * sprawdzenie, czy miejsce sie zgadza
	 * @param field1 poczatkowe pole ruchu
	 * @param field1 koncowe pole ruchu
	 * @return odpowiedz czy pionek po wykonaniu podanego ruchu znajduje sie na dozwolonym polu
	 */
	@Override
	protected boolean checkPosition(Field f1, Field f2) {
		Piece pieceOnF1 = f1.getPiece();
		Piece colorOfF1 = f1.getHomeType();

		if (colorOfF1 == PieceHelperMethods.getHome(pieceOnF1)) {
			return f2.getHomeType() == colorOfF1;
		}

		return true;

	}

	@Override
	public GameType getGameType() {
		return GameType.STANDART;
	}

}
