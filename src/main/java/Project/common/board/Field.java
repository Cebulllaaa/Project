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
	private static final int TOTAL_NUMBER_OF_NEIGHBOURS = 6;

	/**
	 * default constructor
	 * @param id identifier of the field
	 * @param playerPiece information about a piece standing on the field (if there is any)
	 */
	public Field(int id, Piece playerPiece) {
		for (int i=0; i < neighbours.length; i++) {
			neighbours[i] = null;
		}

		fieldId = id;
		pieceOnField = playerPiece;

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
