package Project.common.board;

import Project.common.exceptions.FieldNotFoundException;
import Project.common.exceptions.TooManyNeighboursException;

/**
 * class storing the information about the fields
 *  of the board and their neighbours
 * @version 1.0
 *
 */
public class Field {

	private Field[] neighbours = new Field[6];
	private int neighboursCounter = 0;
	private final int fieldId;
	private Piece pieceOnField;
	private Piece homeType;
	private static final int TOTAL_NUMBER_OF_NEIGHBOURS = 6;

	/**
	 * default constructor
	 * @param id identifier of the field
	 * @param playerPiece information about a piece standing on the field (if there is any)
	 */
	public Field(int id, Piece playerPiece, Piece home) {
		for (int i=0; i < neighbours.length; i++) {
			neighbours[i] = null;
		}

		fieldId = id;
		pieceOnField = playerPiece;
		homeType = home;

	}

	/**
	 * adds each field as a neighbour to the other
	 * @param field1 field to connect
	 * @param field2 other field to connect
	 * @throws TooManyNeighboursException thrown if there are already 6 neighbours
	 *         at one of the fields
	 */
	public static void connectFields(Field field1, Field field2) throws TooManyNeighboursException {
		field1.setNeighbour(field2);
		field2.setNeighbour(field1);
	}

	/**
	 * adds each field as a neighbour to the other, setting the second field at the specified index in the first
	 * @param fieldAppended the first field to connect
	 * @param fieldInserted the second field to connect
	 * @param insertingIndex index of the second field in the first field's neighbours
	 * @throws TooManyNeighboursException thrown if there are already 6 neighbours
	 *         at one of the fields or insertIndex is to big
	 */
	public static void connectFieldsBefore(Field fieldAppended, Field fieldInserted, int insertingIndex)
			throws TooManyNeighboursException {
		fieldAppended.setNeighbourBefore(fieldInserted, insertingIndex);
		fieldInserted.setNeighbour(fieldAppended);
	}

	/**
	 * adds each field as a neighbour to the other at the specified indices
	 * @param field1 the first field to connect
	 * @param field2 the second field to connect
	 * @param index1 index of the first field in the second field's neighbours
	 * @param index2 index of the second field in the first field's neighbours
	 * @throws TooManyNeighboursException thrown if there are already 6 neighbours
	 *         at one of the fields or any of the indices is to big
	 */
	public static void connectFieldsBefore(Field field1, Field field2, int index1, int index2)
			throws TooManyNeighboursException {
		field1.setNeighbourBefore(field2, index2);
		field2.setNeighbourBefore(field1, index1);
	}

	/**
	 * changes information about which piece stands on a field
	 * @param piece new information about a stored piece
	 */
	public void setPieceOnField(Piece piece) {
		pieceOnField = piece;
	}

	/**
	 * adds a neighbour to the field
	 * @param neighbour the new neighbour
	 * @throws TooManyNeighboursException thrown if there are already 6 neighbours
	 */
	public void setNeighbour(Field neighbour) throws TooManyNeighboursException {
		if (neighboursCounter < 6) {
			neighbours[neighboursCounter] = neighbour;
			neighboursCounter ++;

		}
		else {
			throw new TooManyNeighboursException(
					"Error: you are trying to set more than 6 neighbours of the field."
					);
		}

	}

	/**
	 * adds a neighbour to the field at a given index
	 * @param neighbour the new neighbour
	 * @param neighbourIndex index at which to add the neighbour
	 * @throws TooManyNeighboursException thrown if there are already 6 neighbours or index is to big
	 */
	public void setNeighbourBefore(Field neighbour, int neighbourIndex) throws TooManyNeighboursException {
		if (neighbourIndex >= 6) {
			throw new TooManyNeighboursException("Error: there can be only 6 neighbours");
		}
		else if (neighboursCounter >= 6) {
			throw new TooManyNeighboursException(
					"Error: you are trying to set more than 6 neighbours of the field."
					);
		}
		else {
			moveNeighbours(neighbourIndex);
			neighbours[ neighbourIndex ] = neighbour;
			neighboursCounter++;

		}

	}

	/**
	 * increments indices of every neigbour which index is greater or equal to variable index
	 * @param index lowest of the indices of the fields to move
	 */
	private void moveNeighbours(int index) {
		for (int i = neighbours.length - 1; i > index; i--) {
			neighbours[i] = neighbours[i-1];
		}

	}

	/**
	 * gets the truncated array {@link Field#neighbours neighbours}
	 * @return truncated {@link Field#neighbours neighbours}
	 */
	public Field[] getNeighbours() {
		Field[] allNeighbours = new Field[neighboursCounter];

		for (int i=0; i < neighboursCounter; i++) {
			allNeighbours[i] = neighbours[i];
		}

		return allNeighbours;

	}

	/**
	 * getter of {@link Field#fieldId pieceOnField}
	 * @return {@link Field#fieldId pieceOnField}
	 */
	public int getFieldId() {
		return fieldId;
	}

	/**
	 * getter of {@link Field#pieceOnField pieceOnField}
	 * @return {@link Field#pieceOnField pieceOnField}
	 */
	public Piece getPiece() {
		return pieceOnField;
	}

	/**
	 * getter of {@link Field#homeType homeType}
	 * @return {@link Field#homeType homeType}
	 */
	public Piece getHomeType() {
		return homeType;
	}

	/**
	 * get the neighbour that is on the opposite side
	 *    to the field of a given id
	 * @param id id of the field that is the base of the search 
	 * @return field on the opposite side
	 * @throws FieldNotFoundException thrown if there is no neighbour with the given id
	 */
	public Field oppositeField(int id) throws FieldNotFoundException {
		return neighbours[(findField(id) + (TOTAL_NUMBER_OF_NEIGHBOURS / 2)) % TOTAL_NUMBER_OF_NEIGHBOURS];
	}

	/**
	 * get the neighbour that is on the opposite side
	 *    to the given field
	 * @param field the field that is the base of the search 
	 * @return field on the opposite side
	 * @throws FieldNotFoundException thrown if the given field is not a neighbour
	 */
	public Field oppositeField(Field field) throws FieldNotFoundException {
		return neighbours[(findField(field) + (TOTAL_NUMBER_OF_NEIGHBOURS / 2)) % TOTAL_NUMBER_OF_NEIGHBOURS];
	}

	public int getNeigboursCount() {
		return neighboursCounter;
	}

	/**
	 * finds the index of the neighbour with a given id in the {@link Field#neighbours neighbours} array
	 * @param id id of the neighbour we are searching for
	 * @return index of the neighbour in the array
	 * @throws FieldNotFoundException thrown if there is no neighbour with the given id
	 */
	private int findField(int id) throws FieldNotFoundException {
		for (int i=0; i < neighboursCounter; i++) {
			if (neighbours[i].getFieldId() == id) {
				return i;
			}
		}

		throw new FieldNotFoundException("Neighbour not found.");

	}

	/**
	 * finds the index of the neighbour in the {@link Field#neighbours neighbours} array
	 * @param field the neighbour we are searching for
	 * @return index of the neighbour in the array
	 * @throws FieldNotFoundException thrown if the given field is not a neighbour
	 */
	private int findField(Field field) throws FieldNotFoundException {
		for (int i=0; i < neighboursCounter; i++) {
			if (neighbours[i].equals(field)) {
				return i;
			}
		}

		throw new FieldNotFoundException("Neighbour not found.");

	}

}
