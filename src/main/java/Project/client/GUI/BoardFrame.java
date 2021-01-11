package Project.client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Project.client.connection.Connection;
import Project.client.exceptions.GameEndedException;
import Project.common.board.AbstractBoard;
import Project.common.board.Piece;
import Project.common.board.PieceHelperMethods;
import Project.common.game.GameHelperMethods;

/**
 * klasa odpowiadajaca za interfejs uzytkownika
 * @version 1.0
 *
 */
public class BoardFrame extends JFrame /*implements Runnable*/ {

	private static final long serialVersionUID = 1;

	private AbstractBoard board;
	private Connection connection;
	private boolean more = true;
	private JDialog infoDialog;
	private String authors;
	private String howToUse;
	private JTextArea dialogText;
	private FieldButton[] buttons;
	private int whereFrom = 0;
	private int whereTo = 0;
	private int pressed = -1;

	/**
	 * jedyny konstruktor klasy
	 * @param ab klasa planszy odpoiadajaca planowanemu typowi gry
	 */
	public BoardFrame(AbstractBoard ab) {
		board = ab;

		int n = board.getEdgeLength();

		/*
		 * (4*(n-1) * 2 + 1, (3*n - 2) * 2)
		 *    == (4 * bokTrojkata * 2 + polowa przesuniecia, (2 * bokTrojkata + bokSzesciokata) * 2)
		 */
		setLayout(new GridLayout(8 * n - 5, 6 * n - 4));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		initMenuBar();
		initHelpDialog();
		initButtons();

	}

	private void initMenuBar() {
		JMenuBar mBar = new JMenuBar();
		JMenu mGame = new JMenu("Game");
		JMenuItem helpMenu = new JMenuItem("Help");
		JMenuItem moveMenu = new JMenuItem("Make move");
		JMenuItem playerMenu = new JMenuItem("Player info");

		mBar.add(mGame);

		mGame.add(helpMenu);
		mGame.add(moveMenu);
		mGame.add(playerMenu);

		LabelListener labListener = new LabelListener();

		helpMenu.addActionListener(labListener);
		moveMenu.addActionListener(labListener);
		playerMenu.addActionListener(labListener);

		setJMenuBar(mBar);

	}

	private void initHelpDialog() {
		JButton dialogButton = new JButton("Ok");
		authors = "auhtors:\nBartosz Cybulski\nBartlomiej Krolikowski\n";
		howToUse = "When it's your turn you can click on a space containing your peg\n"
				+ "and next click on empty space.\nThis will move the peg on that space.\n"
				+ "You should then press \"Make move\" item in the menu to upload your move.\n"
				+ "You can also make no move and then you should only press \"Make move\"\n";
		dialogText = new JTextArea(howToUse + "\n" + authors, 6, 1);

		dialogButton.addActionListener(new DialogButtonListener());

		dialogText.setEditable(false);

		infoDialog = new JDialog(this, "Informations about the game", true);
		infoDialog.setSize(500, 200);
		infoDialog.setResizable(false);
		infoDialog.setLayout(new BorderLayout());
		infoDialog.add(dialogText, BorderLayout.CENTER);
		infoDialog.add(dialogButton, BorderLayout.SOUTH);
		infoDialog.setLocationRelativeTo(null);

	}

	private void initButtons() {
		buttons = new FieldButton[board.fields.length];

		/*int n = board.getEdgeLength();
				for (int i=buttons.length; i<(3*n-2)*(3*n-2); i++) {
					add(new JPanel());
				}*/
		for (int i=0; i < buttons.length; i++) {
			buttons[i] = new FieldButton();
			buttons[i].setForeground(Color.BLACK);
			buttons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 0));
			buttons[i].setField(board.fields[i], i);
			buttons[i].addActionListener(new FieldsListener());

		}

		FieldOrganizer fo = new FieldOrganizer(buttons, board.getEdgeLength());
		fo.organize();

		addOrganized( fo.getOrganizedFields() );

	}

	private void addOrganized(FieldButton[][] orgFB) {
		int width = (3 * board.getEdgeLength());
		for (int i=0; i < orgFB.length; i++) {
			FieldButton[] oneRow = orgFB[i];
			int halfEmptyCount = (width - (i % 2) - oneRow.length) / 2;

			for (int j=0; j < halfEmptyCount - (i % 2); j++) {
				add(new JPanel());
				add(new JPanel());
			}

			for (int j=0; j < oneRow.length; j++) {
				if (i % 2 == 0) {
					add(oneRow[j]);
				//System.out.print(PieceHelperMethods.pieceToID(oneRow[j].getHomeType()));
				}

				add(new JPanel());

				if (i % 2 != 0) {
					add(oneRow[j]);
				//System.out.print(PieceHelperMethods.pieceToID(oneRow[j].getPiece()));
				}

			}

			for (int j=0; j < halfEmptyCount + width; j++) {
				add(new JPanel());
				add(new JPanel());
			}

		}

	}

	/**
	 * metoda ustawiajaca pionki na planszy kozystajac z {@link ClientConnection#getPieces() ClientConnection.getPieces()}
	 */
	public void setNewPieces() {
		int[][] positions = connection.getPieces();
		//int myID = connection.getID();
		int numberOfPlayers = connection.getNumOfPlayers();

		for (int i=0; i < buttons.length; i++) {
			buttons[i].setPiece( Piece.NONE );
			buttons[i].choosePiece();
System.out.println(buttons[i].getPiece());
		}

		for (int i=0; i < positions.length; i++) {
			for (int j=0; j < positions[i].length; j++) {
//System.out.println(myID);
				int pieceColor = GameHelperMethods.positionInArrayToId(i, numberOfPlayers);
				Piece piece = PieceHelperMethods.idToPiece(pieceColor);
				FieldButton but = buttons[positions[i][j]];

				but.setPiece(piece);
System.out.println(but.getPiece());
				but.choosePiece();
System.out.println(Integer.toString(i) + "," + Integer.toString(j));

			}

		}

	}

	/**
	 * metoda informujaca uzytkownika o problemach uniemozliwiajacych dalsza gre
	 * informuje tez o sytuacji w ktorej ktorys z graczy konczy rozgrywke,
	 * mimo ze nie musi to konczyc gry
	 * @param x wyjatek przekazujacy informacje o problemie lub zwyciezcy
	 */
	public void signalise(Exception x) {
		more = false;

		if (x instanceof GameEndedException) {
			dialogText.setText("The game has ended.\nPlayer " + x.getMessage() + " won.");
		}
		else {
			dialogText.setText("An error has occured:\n" + x.getMessage());
		}

		infoDialog.setVisible(true);

	}

	/**
	 * ustawia pole {@link BoardFrame#connection connection}
	 * @param ctc obiekt odpowiedzialny za odbieranie i wysylanie informacji od i do serwera
	 */
	public void setConnection(Connection ctc) {
		connection = ctc;
	}

	/**
	 * informuje gracza, ze teraz jego kolej
	 */
	public void informAboutTurn() {
		dialogText.setText("It's your turn!");
		infoDialog.setVisible(true);

	}

	private class LabelListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().contentEquals("Help")) {
				showHelpDialog();
			}
			else if (arg0.getActionCommand().contentEquals("Make move")) {
				if (connection.isMyTurn()) {
					makeMove();
				}
				else {
					dialogText.setText("It's not your turn.");
					infoDialog.setVisible(true);
				}
			}
			else if (arg0.getActionCommand().contentEquals("Player info")) {
				showPlayerInfo();
			}

		}

		private void showHelpDialog() {
			dialogText.setText(howToUse + "\n" + authors);
			infoDialog.setVisible(true);
		}

		private void showPlayerInfo() {
			String text;

			text = "Player ID: " + Integer.toString(connection.getID())
			+ "\nPlayer color: " + PieceHelperMethods.idToPiece(connection.getID()).toString()
			+ "\nIs it your turn: " + ((connection.isMyTurn())?"yes":"no");

			dialogText.setText(text);
			infoDialog.setVisible(true);

		}

		private void makeMove() {
			connection.setChange(whereFrom, whereTo);

			whereFrom = 0;
			whereTo = 0;

			connection.makeMove();

		}

	}

	private class DialogButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			infoDialog.setVisible(false);

			if (!more) {
				System.exit(0);
			}

		}

	}

	private class FieldsListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			FieldButton fb = (FieldButton)arg0.getSource();
			int index = fb.getID();
			if (pressed == -1) {
				if (fb.getPiece() == PieceHelperMethods.idToPiece(connection.getID())) {
					pressed = index;
					buttons[whereFrom].choosePiece();
					buttons[whereTo].choosePiece();
					whereFrom = index;
					fb.setBackground(Color.BLACK);
				}
				else {
					String text;

					if (fb.getPiece() == Piece.NONE) {
						text = "The space is empty!";
					}
					else {
						text = "Wrong peg.\nYour color is "
								+ PieceHelperMethods.idToPiece(connection.getID()).toString();
					}

					dialogText.setText(text);
					infoDialog.setVisible(true);
				}
			}
			else {
				if (fb.getPiece() == Piece.NONE) {
					buttons[pressed].chooseColor();
					pressed = -1;
					whereTo = index;
					fb.setBackground(Color.PINK);
					repaint();
				}
				else {
					dialogText.setText("There is already a peg on that space");
					infoDialog.setVisible(true);
				}
			}

		}

	}

}
