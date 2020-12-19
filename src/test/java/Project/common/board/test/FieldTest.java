package Project.common.board.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Project.common.board.Field;
import Project.common.board.Piece;
import Project.common.board.PieceHelperMethods;

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
			}
		}
	}

	@Test
	public void connectTest() {
		for (int i=0; i)
	}

}
