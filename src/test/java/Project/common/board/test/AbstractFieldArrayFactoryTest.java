package Project.common.board.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Project.common.board.AbstractFieldArrayFactory;
import Project.common.board.Field;
import Project.common.exceptions.TooManyNeighboursException;

public abstract class AbstractFieldArrayFactoryTest {

	AbstractFieldArrayFactory afaf;

	@Test
	public void createNewArrayOfFields() throws TooManyNeighboursException {
		Field[] fields = afaf.getNewFieldArray();
		assertEquals(afaf.getBoardSize(), fields.length);
	}

}
