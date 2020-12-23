package Project.common.board.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Project.common.board.Field;
import Project.common.board.Piece;
import Project.common.board.PieceHelperMethods;
import Project.common.exceptions.FieldNotFoundException;
import Project.common.exceptions.TooManyNeighboursException;

public class FieldTest {

	public Field testedField;

	@Test
	public void constructorTest() {
		for (int i=1; i<=6; i++) {
			for (int j=1; j<=6; j++) {
				Piece piece1 = PieceHelperMethods.idToPiece(i);
				Piece piece2 = PieceHelperMethods.idToPiece(j);
				testedField = new Field(i+j, piece1, piece2);
				assertEquals(i+j, testedField.getFieldId());
				assertEquals(piece1, testedField.getPiece());
				assertEquals(piece2, testedField.getHomeType());
			}
		}
	}

	@Test
	public void setPieceTest() {
		testedField = new Field(0, Piece.NONE, Piece.NONE);
		assertEquals(Piece.NONE, testedField.getPiece());

		testedField.setPieceOnField(Piece.BLUE);
		assertEquals(Piece.BLUE, testedField.getPiece());

	}

	@Test
	public void connectTest() throws TooManyNeighboursException, FieldNotFoundException {
		Field[] fields = new Field[7];
		for (int i=0; i < fields.length; i++) {
			fields[i] = new Field(i, Piece.NONE, Piece.NONE);
			for (int j=0; j<i; j++) {
				Field.connectFields(fields[i], fields[j]);
			}
		}

		for (int i=0; i < fields.length; i++) {
			for (int j=0; j<i; j++) {
				assertEquals(fields[i].getNeighbours()[j], fields[j]);
			}
			for (int j=i+1; j < fields.length; j++) {
				assertEquals(fields[i].getNeighbours()[j-1], fields[j]);
			}
		}

		for (int i=0; i < fields.length; i++) {
			for (int j=0; j<i; j++) {

				int neigh = j + 3;
				neigh += (neigh >= i) ? 1 : 0;
				neigh %= fields.length;

				assertEquals(fields[neigh], fields[i].oppositeField( fields[j] ));
				assertEquals(fields[neigh], fields[i].oppositeField( fields[j].getFieldId() ));

			}

			for (int j= i+1; j < fields.length; j++) {

				int neigh = j + 3;
				neigh += (neigh >= fields.length + i) ? 1 : 0;
				neigh %= fields.length;

				assertEquals(fields[neigh], fields[i].oppositeField( fields[j] ));
				assertEquals(fields[neigh], fields[i].oppositeField( fields[j].getFieldId() ));

			}


		}

	}

	@Test(expected = TooManyNeighboursException.class)
	public void connectTestException() throws TooManyNeighboursException {
		Field[] fields = new Field[8];
		for (int i=0; i < fields.length; i++) {
			fields[i] = new Field(i, Piece.NONE, Piece.NONE);
			for (int j=0; j<i; j++) {
				Field.connectFields(fields[i], fields[j]);
			}
		}

	}

	@Test(expected = FieldNotFoundException.class)
	public void oppositeFieldFieldTestException() throws TooManyNeighboursException, FieldNotFoundException {
		Field[] fields = new Field[3];
		testedField = new Field(10, Piece.BLUE, Piece.GREEN);

		for (int i=0; i < fields.length; i++) {
			fields[i] = new Field(i, Piece.NONE, Piece.NONE);
			for (int j=0; j<i; j++) {
				Field.connectFields(fields[i], fields[j]);
			}
		}

		fields[0].oppositeField(testedField);

	}

	@Test(expected = FieldNotFoundException.class)
	public void oppositeFieldIntTestException() throws TooManyNeighboursException, FieldNotFoundException {
		Field[] fields = new Field[3];
		for (int i=0; i < fields.length; i++) {
			fields[i] = new Field(i, Piece.NONE, Piece.NONE);
			for (int j=0; j<i; j++) {
				Field.connectFields(fields[i], fields[j]);
			}
		}

		fields[0].oppositeField(10);

	}

}
