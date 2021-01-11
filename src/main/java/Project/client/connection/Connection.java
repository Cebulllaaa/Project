package Project.client.connection;

import java.io.IOException;

import Project.client.GUI.BoardFrame;
import Project.client.GUI.ConnectionListener;
import Project.common.game.GameType;

public interface Connection {

	public int getID();
	public GameType getGameType();
	public int getNumOfPlayerPieces();
	public int getNumOfPlayers();
	public int getTotalNumOfPieces();
	public int[][] getPieces();
	public boolean isMyTurn();
	public void setChange(int chPiece, int newPiecePos);
	public void makeMove();
	public void setListener(ConnectionListener list);

	public void start(); // ma byc wywolywalny jako watek

}
