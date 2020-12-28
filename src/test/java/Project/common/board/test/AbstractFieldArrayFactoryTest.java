package Project.common.board.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Project.common.board.AbstractFieldArrayFactory;
import Project.common.board.Field;
import Project.common.exceptions.FieldNotFoundException;
import Project.common.exceptions.TooManyNeighboursException;

public abstract class AbstractFieldArrayFactoryTest {

	AbstractFieldArrayFactory afaf;
	Field[] fields = null;

	@Before
	public void getNewFields() throws TooManyNeighboursException {
		fields = afaf.getNewFieldArray();
	}

	@Test
	public void createNewArrayOfFields() throws TooManyNeighboursException {
		assertEquals(afaf.getBoardSize(), fields.length);
	}

	@Test
	public void connectionsInFields() throws TooManyNeighboursException {
		int e = afaf.getEdgeLength();
		int s = 1;
		for (int i=1; i < e-1; i++) {
			int cir = 6*i;

			for (int j=0; j<cir; j++) {
				boolean isConnected = false;
				int neigh = s + (j+1)%cir;
				Field f = fields[s + j];
				Field f1 = fields[neigh];
				for (Field f2 : f.getNeighbours()) {
					if (f1 == f2) {
						isConnected = true;
					}
				}
				assertTrue(isConnected);

				neigh = s + (cir + j - 1)%cir;
				f1 = fields[neigh];
				for (Field f2 : f.getNeighbours()) {
					if (f1 == f2) {
						isConnected = true;
					}
				}
				assertTrue(isConnected);
			}

			s += cir;

		}

	}

	@Test
	public void numberOfNeighbours() {
		checkNumInHexagon();

		for (int i=0; i<6; i++) {
			checkNumInTriangle(i);
		}

	}

	@Ignore
	protected void checkNumInHexagon() {
		int e = afaf.getEdgeLength();
		int h = afaf.getHexagonSize();

		for (int i=0; i<h; i++) {
			int neighCount = fields[i].getNeigboursCount();
			if ((h - i) % (e - 1) == 0 && h-i <= 6 * (e - 1)) {
				assertEquals(5, neighCount);
			}
			else {
				assertEquals(6, neighCount);
			}

		}

	}

	@Ignore
	protected void checkNumInTriangle(int triangleNumber) {
		int e = afaf.getEdgeLength();
		int sumOfRows = 1;

		Field[] triangle = getTriangle(triangleNumber);

		assertEquals(2, triangle[0].getNeigboursCount());

		for (int i=2; i <= e-1; i++) {
			assertEquals(4, triangle[sumOfRows].getNeigboursCount());

			for (int j=1; j < i-1; j++) {
				assertEquals(6, triangle[sumOfRows + j].getNeigboursCount());
			}

			sumOfRows += i;
			assertEquals(4, triangle[sumOfRows - 1].getNeigboursCount());

		}

	}

	@Ignore
	protected Field[] getTriangle(int triNum) {
		int h = afaf.getHexagonSize();
		int t = afaf.getTriangleSize();

		Field[] triangle = new Field[t];

		for (int i=0; i<t; i++) {
			triangle[i] = fields[h + triNum * t + i];
		}

		return triangle;

	}

	@Test
	public void testNeighboursOrder() throws FieldNotFoundException {
		for (int i=0; i<6; i++) {
			testStartingInTriangle(i);
		}

	}

	@Ignore
	protected void testStartingInTriangle(int triNum) throws FieldNotFoundException {
		int e = afaf.getEdgeLength();
		int t = afaf.getTriangleSize();
		int h = afaf.getHexagonSize();
		int pathLength = 1;
		Field prevField = fields[h + t * triNum];
		Field currField = prevField.getNeighbours()[1];

		while (currField != null) {
			Field tempField = currField;
			currField = currField.oppositeField(prevField);
			prevField = tempField;
			pathLength++;

		}

		assertEquals(3*e - 2, pathLength);
		assertEquals(fields[h + t * ((triNum + 2) % 6)], prevField);
		// sprawdzenie, czy koniec jest wierzcholkiem nastepnego trojkata

	}

}
