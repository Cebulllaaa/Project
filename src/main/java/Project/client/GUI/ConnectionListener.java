package Project.client.GUI;

import Project.client.connection.Connection;

public interface ConnectionListener {

	public void setNewPieces();
	public void signalise(Exception x);
	public void setConnection(Connection ctc);
	public void informAboutTurn();
	public void informAboutWinning(int positionInRanking);

//	public boolean isInforming();

}
