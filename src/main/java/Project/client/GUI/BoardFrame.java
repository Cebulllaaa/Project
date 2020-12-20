package Project.client.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import Project.client.connection.ClientTemporaryConnection;
import Project.common.board.AbstractBoard;

public class BoardFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1;

	private AbstractBoard board;
	private ClientTemporaryConnection connection;
	private boolean more = true;
	private JDialog infoDialog;
	private String authors;
	private String howToUse;
	private JTextArea dialogText;

	public BoardFrame() {
		setLayout(new GridLayout(5, 5));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		initMenuBar();
		initHelpDialog();

	}

	private void initMenuBar() {
		JMenuBar mBar = new JMenuBar();
		JMenu mGame = new JMenu("Game");
		JMenuItem helpMenu = new JMenuItem("Help");
		JMenuItem moveMenu = new JMenuItem("Make move");

		mBar.add(mGame);

		mGame.add(helpMenu);
		mGame.add(moveMenu);

		helpMenu.addActionListener(new LabelListener());
		moveMenu.addActionListener(new LabelListener());

		setJMenuBar(mBar);

	}

	private void initHelpDialog() {
		JButton dialogButton = new JButton("Ok");
		authors = "auhtors:\nBartosz Cybulski\nBartlomiej Krolikowski\n";
		howToUse = "When it's your turn you can click on a field containing your peg\n"
				+ "and next click on empty field.\nThis will move the peg on that field.\n"
				+ "You should than press \"Make move\" item in the menu to upload your move.\n"
				+ "You can also make no move and then you should only press \"Make move\"\n";
		dialogText = new JTextArea(howToUse + authors, 6, 1);

		dialogButton.addActionListener(new DialogButtonListener());

		dialogText.setEditable(false);

		infoDialog = new JDialog(this, "Informations about the program", true);
		infoDialog.setSize(500, 200);
		infoDialog.setResizable(false);
		infoDialog.setLayout(new BorderLayout());
		infoDialog.add(dialogText, BorderLayout.CENTER);
		infoDialog.add(dialogButton, BorderLayout.SOUTH);
		infoDialog.setLocationRelativeTo(null);

	}

	public void run() {
		while (more) {
			try {
				Thread.sleep(1000);					
				print();
			}
			catch (InterruptedException ix) {
				more = false;
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

	private class LabelListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand() == "Help") {
				showHelpDialog();
			}
			else if (arg0.getActionCommand() == "Make move") {
				makeMove();
			}

		}

		private void showHelpDialog() {
			infoDialog.setVisible(true);
		}

		private void makeMove() {
			; // TODO
		}

	}

	private class DialogButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			infoDialog.setVisible(false);
		}
	}

}
