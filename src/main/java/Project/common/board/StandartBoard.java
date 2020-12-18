package Project.common.board;

import java.util.LinkedList;
import java.util.Queue;

import Project.common.exceptions.ApplicationErrorException;
import Project.common.exceptions.TooManyNeighboursException;

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

	@Override
	protected boolean checkNeighbouring(int posF1, int posF2) {
		return checkNeighbouring(fields[posF1], fields[posF2]);
	}

	@Override
	protected boolean checkNeighbouring(Field field1, Field field2) {
		Field[] field1Neighbours = field1.getNeighbours();
		Queue<Field> queue = new LinkedList<Field>();
		boolean[] searchedFields = new boolean[TOTAL_NUMBER_OF_FIELDS];

		for (int i=0; i < searchedFields.length; i++) {
			searchedFields[i] = false;
		}

		queue.add(field1);

		while (!queue.isEmpty()) { // szukanie BFS po calej planszy pol przyleglych
			Field[] neighbours = queue.remove().getNeighbours();
			for (Field neighbour : neighbours) {
				if (field2 == neighbour) {
					return true;
				}
				else if (!searchedFields[ neighbour.getFieldId() ] && neighbour.getPiece() != Piece.NONE) {
					queue.add(neighbour);
				}
			}
		}

		return false;

	}

	@Override
	protected boolean checkPosition(int f1) {
		return true; // TODO
	}

	@Override
	protected boolean checkPosition(Field f1) {
		return true; // TODO
	}

}
