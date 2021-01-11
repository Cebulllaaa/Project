package Project.client.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import Project.client.GUI.BoardFrame;
import Project.client.GUI.ConnectionListener;

public class TestConnectionListener implements ConnectionListener {

	private static final long serialVersionUID = 1;

	private Connection testedConnection;
	private Exception expectedException;
	public boolean myTurn;

	public void setNewPieces() {
		//
		
	}

	public void signalise(Exception x) {
		assertEquals(expectedException.getClass(), x.getClass());
		
	}

	public void setConnection(Connection ctc) {
		testedConnection = ctc;
		
	}

	public void informAboutTurn() {
		myTurn = testedConnection.isMyTurn();
		
	}

	public void setException(Exception x) {
		expectedException = x;
	}

	public void informAboutWinning(int positionInRanking) {
		// TODO Auto-generated method stub
		
	}

}
