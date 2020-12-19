package Project.client.GUI;

import javax.swing.JFrame;

import Project.client.connection.ClientTemporaryConnection;

public class BoardFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1;

	private ClientTemporaryConnection connection;
	private boolean more = true;

	public void run() {
		while (more) {
			try {
				Thread.sleep(1000);					
				print();
			}
			catch (InterruptedException ix) {
				System.out.println(ix);
			}
		}
	}

	public void signalise(Exception x) {
		System.out.println(x);
		more = false;
	}

	public void setConnection(ClientTemporaryConnection ctc) {
		connection = ctc;
	}

	public void print() {
		System.out.println(connection.getID());
		System.out.println(connection.getGameType());
		System.out.println(connection.getNumOfPlayerPieces());
		System.out.println(connection.getTotalNumOfPieces());
		int[][] pieces = connection.getPieces();
		if (pieces != null) {
			for (int i=0; i<pieces.length; i++) {
				for (int j=0; j < pieces[i].length; j++) {
					System.out.println(pieces[i][j]);
				}
			}
		}
	}

}
