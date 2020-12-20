package Project.common.board.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Project.common.board.AbstractFieldArrayFactory;
import Project.common.board.Field;
import Project.common.exceptions.TooManyNeighboursException;

public abstract class AbstractFieldArrayFactoryTest {

	AbstractFieldArrayFactory afaf;
	Field[] fields = null;

	@Test
	public void createNewArrayOfFields() throws TooManyNeighboursException {
		fields = afaf.getNewFieldArray();
		assertEquals(afaf.getBoardSize(), fields.length);
	}

	@Test
	public void connectionsInFields() throws TooManyNeighboursException {
		Field[] fields = afaf.getNewFieldArray();

		int n = afaf.getHexagonSize();
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

}
