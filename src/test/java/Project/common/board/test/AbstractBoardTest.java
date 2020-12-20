package Project.common.board.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Project.common.board.AbstractBoard;
import Project.common.board.Field;
import Project.common.board.PieceHelperMethods;

public class AbstractBoardTest {

	protected AbstractBoard ab;

	@Test
	public void testInitPieces() {
		ab.initPieces(6);

		Field[] fields = ab.fields;
		for (int i=0; i < fields.length; i++) {
			assertEquals(fields[i].getHomeType(), fields[i].getPiece());
		}

		int[] pieces = ab.getPiecesPositions();
		for (int i=0; i < pieces.length; i++) {
			assertEquals(fields[pieces[i]].getPiece(),
					PieceHelperMethods.idToPiece((i / ab.fieldArrayFactory.getTriangleSize()) + 1));
		}

	}

}
