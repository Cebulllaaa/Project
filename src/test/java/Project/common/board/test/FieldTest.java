package Project.common.board.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Project.common.board.Field;
import Project.common.board.Piece;
import Project.common.board.PieceHelperMethods;
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
	public void connectTest() throws TooManyNeighboursException {
		Field[] fields = new Field[6];
		for (int i=0; i<6; i++) {
			fields[i] = new Field(i, Piece.NONE, Piece.NONE);
			for (int j=0; j<i; j++) {
				Field.connectFields(fields[i], fields[j]);
			}
		}

		for (int i=0; i<6; i++) {
			for (int j=0; j<i; j++) {
				assertEquals(fields[i].getNeighbours()[j], fields[j]);
			}
			for (int j=i+1; j<6; j++) {
				assertEquals(fields[i].getNeighbours()[j-1], fields[j]);
			}
		}

		//

	}

	@Test(expected = TooManyNeighboursException.class)
	public void connectTestException() throws TooManyNeighboursException {
		Field[] fields = new Field[7];
		for (int i=0; i<7; i++) {
			fields[i] = new Field(i, Piece.NONE, Piece.NONE);
			for (int j=0; j<i; j++) {
				Field.connectFields(fields[i], fields[j]);
			}
		}

	}


}
