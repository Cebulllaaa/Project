package Project.common.board.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Project.common.board.AbstractBoard;
import Project.common.board.Field;
import Project.common.board.Piece;
import Project.common.board.PieceHelperMethods;

public abstract class AbstractBoardTest {

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

		boolean[] hasPiece = new boolean[ab.fields.length];
		for (int i=0; i < hasPiece.length; i++) {
			hasPiece[i] = false;
		}

		for (int i=0; i < pieces.length; i++) {
			hasPiece[pieces[i]] = true;
		}

		for (int i=0; i < hasPiece.length; i++) {
			if (!hasPiece[i]) {
				assertEquals(Piece.NONE, ab.fields[i].getPiece());
			}
		}

	}

	@Test
	public void testGetTriangle() {
		int t = ab.getTriangleSize();
		int h = ab.getHexagonSize();

		for (int i=0; i < 6; i++) {
			Field[] tri = ab.getTriangle(i + 1);

			for (int j=0; j < tri.length; j++) {
				assertEquals(ab.fields[h + t * i + j], tri[j]);
			}

		}

	}

	@Test
	public void sizesTest() {
		int e = ab.getEdgeLength();
		int t = ab.getTriangleSize();
		int h = ab.getHexagonSize();

		assertEquals(e * (e-1) / 2, t);
		assertEquals(6 * t + 1, h);

	}

}
