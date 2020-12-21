package Project.client.GUI;

import java.awt.Color;

import javax.swing.JButton;

import Project.common.board.Field;
import Project.common.board.Piece;
import Project.common.exceptions.FieldNotFoundException;

public class FieldButton extends JButton {

	private static final long serialVersionUID = 1;

	private Field myField;
	private int myID;

	/*public FieldButton(String str) {
		super(str);
	}*/

	public void setField(Field field, int id) {
		myField = field;
		myID = id;
		chooseColor();
		choosePiece();

	}

	public void setPiece(Piece piece) {
		myField.setPieceOnField(piece);
	}

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
			setBackground(Color.BLUE.darker());
			break;

		case GREEN:
//			setForeground(Color.GREEN);
			//setLabel("G");
			setBackground(Color.GREEN.darker());
			break;

		case ORANGE:
//			setForeground(Color.ORANGE);
			//setLabel("O");
			setBackground(Color.ORANGE.darker());
			break;

		case RED:
//			setForeground(Color.RED);
			//setLabel("R");
			setBackground(Color.RED.darker());
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

	public Field getField() {
		return myField;
	}

	public Field[] getNeighbours() {
		return myField.getNeighbours();
	}

	public int getFieldID() {
		return myField.getFieldId();
	}

	public int getID() {
		return myID;
	}

	public Piece getPiece() {
		return myField.getPiece();
	}

	public Piece getHomeType() {
		return myField.getHomeType();
	}

	public Field oppositeField(int id) throws FieldNotFoundException {
		return myField.oppositeField(id);
	}

	public Field oppositeField(Field field) throws FieldNotFoundException {
		return myField.oppositeField(field);
	}

}
