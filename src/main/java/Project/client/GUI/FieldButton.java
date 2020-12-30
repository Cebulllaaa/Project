package Project.client.GUI;

import java.awt.Color;

import javax.swing.JButton;

import Project.common.board.Field;
import Project.common.board.Piece;
import Project.common.exceptions.FieldNotFoundException;

/**
 * class for allowing modifications of the state of game by user
 * @version 1.0
 *
 */
public class FieldButton extends JButton {

	private static final long serialVersionUID = 1;

	/**
	 * pole odpowiadajace przyciskowi
	 */
	private Field myField;
	/**
	 * indeks przycisku w tablicy przyciskow
	 */
	private int myID;

	/*public FieldButton(String str) {
		super(str);
	}*/

	/**
	 * metoda nadajaca przyciskowi odpowiadajace mu pole
	 * @param field odpowiadajace przyciskowi pole
	 * @param id inteks przycisku w tablicy
	 */
	public void setField(Field field, int id) {
		myField = field;
		myID = id;
		chooseColor();
		choosePiece();

	}

	/**
	 * ustawienie lub usuniecie pionka z pola
	 * @param piece rodzaj pionka do postawienia
	 */
	public void setPiece(Piece piece) {
		myField.setPieceOnField(piece);
	}

	/**
	 * ustawienie koloru tla dla pustego pola
	 */
	public void chooseColor() {
		switch (getHomeType()) {
		case NONE:
			setBackground(Color.GRAY);
			break;

		case BLUE:
			setBackground(Color.BLUE);
			break;

		case GREEN:
			setBackground(Color.GREEN);
			break;

		case ORANGE:
			setBackground(Color.ORANGE);
			break;

		case RED:
			setBackground(Color.RED);
			break;

		case WHITE:
			setBackground(Color.WHITE);
			break;

		case YELLOW:
			setBackground(Color.YELLOW);
			break;

		default:
			break;
		}

	}

	/**
	 * ustawienie koloru tla na podstawie stojacych pionkow
	 */
	public void choosePiece() {
		switch (getPiece()) {
		case NONE:
//			setForeground(Color.GRAY);
			//setLabel(" ");
			chooseColor();
			break;

		case BLUE:
//			setForeground(Color.BLUE);
			//setLabel("B");
			setBackground(Color.BLUE.darker().darker());
			break;

		case GREEN:
//			setForeground(Color.GREEN);
			//setLabel("G");
			setBackground(Color.GREEN.darker().darker());
			break;

		case ORANGE:
//			setForeground(Color.ORANGE);
			//setLabel("O");
			setBackground(Color.ORANGE.darker().darker());
			break;

		case RED:
//			setForeground(Color.RED);
			//setLabel("R");
			setBackground(Color.RED.darker().darker());
			break;

		case WHITE:
//			setForeground(Color.WHITE);
			//setLabel("W");
			setBackground(Color.WHITE.darker());
			break;

		case YELLOW:
//			setForeground(Color.YELLOW);
			//setLabel("Y");
			setBackground(Color.YELLOW.darker());
			break;

		default:
			break;
		}
	}

	/**
	 * getter myField
	 * @return myField
	 */
	public Field getField() {
		return myField;
	}

	/**
	 * getter sasidow pola
	 * @return tablica sasiadow pola
	 */
	public Field[] getNeighbours() {
		return myField.getNeighbours();
	}

	/**
	 * getter id pola
	 * @return id pola
	 */
	public int getFieldID() {
		return myField.getFieldId();
	}

	/**
	 * getter myID
	 * @return myID
	 */
	public int getID() {
		return myID;
	}

	/**
	 * getter pieceOnField z myField
	 * @return pieceOnField z myField
	 */
	public Piece getPiece() {
		return myField.getPiece();
	}

	/**
	 * getter homeType z myField
	 * @return homeType z myField
	 */
	public Piece getHomeType() {
		return myField.getHomeType();
	}

	/**
	 * podaje pole lezace naprzeciwko wzgledem innego pola
	 * @param id id pola wzgledem ktorego szukamy pole lezacego naprzeciwko naszego
	 * @return pole lezace naprzeciwko
	 * @throws FieldNotFoundException wyrzucany gdy podane pole nie jest sasiadem
	 */
	public Field oppositeField(int id) throws FieldNotFoundException {
		return myField.oppositeField(id);
	}

	/**
	 * podaje pole lezace naprzeciwko wzgledem innego pola
	 * @param field pole wzgledem ktorego szukamy pole lezacego naprzeciwko naszego
	 * @return pole lezace naprzeciwko
	 * @throws FieldNotFoundException wyrzucany gdy podane pole nie jest sasiadem
	 */
	public Field oppositeField(Field field) throws FieldNotFoundException {
		return myField.oppositeField(field);
	}

}
