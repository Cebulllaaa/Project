package Project.server.GUI;

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

import Project.Database.Tables.Game_attribute;
import Project.client.GUI.FieldButton;
import Project.client.GUI.FieldOrganizer;
import Project.common.board.AbstractBoard;
import Project.common.board.Piece;
import Project.common.board.PieceHelperMethods;
import Project.common.board.StandartBoard;
import Project.common.exceptions.ApplicationErrorException;
import Project.common.game.Game;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

public class GameHistoryFrame extends JFrame {

		private static final long serialVersionUID = 1;

		//private Game game;
		private Game_attribute game_attr;
		private AbstractBoard board;
		private boolean more = true;
		private JDialog infoDialog;
		private String authors;
		private String howToUse;
		private JTextArea dialogText;
		private FieldButton[] buttons;
		private int whereFrom = 0;
		private int whereTo = 0;
		private int pressed = -1;
		private int[][] positions;
		private int numOfPlayers;
		private int numOfPlayerPieces;
		private GameType type;

		/**
		 * jedyny konstruktor klasy
		 * @param ab klasa planszy odpoiadajaca planowanemu typowi gry
		 */
		public GameHistoryFrame(AbstractBoard board, Game_attribute gAttr) { //Game currentGame) {
			//game = currentGame;
			this.board = board;
			game_attr = gAttr;
			type = board.getGameType();

			numOfPlayers = game_attr.getnmb_players();
			numOfPlayerPieces = board.getTriangleSize();
			positions = new int[numOfPlayers][numOfPlayerPieces];

			int n = board.getEdgeLength();

			setLayout(new BorderLayout());
			setDefaultCloseOperation(EXIT_ON_CLOSE);

			JPanel boardPanel = new JPanel();
			JPanel commandPanel = new JPanel();

			/*
			 * (4*(n-1) * 2 + 1, (3*n - 2) * 2)
			 *    == (4 * bokTrojkata * 2 + polowa przesuniecia, (2 * bokTrojkata + bokSzesciokata) * 2)
			 */
			boardPanel.setLayout(new GridLayout(8 * n - 5, 6 * n - 4));
			commandPanel.setLayout(new GridLayout(1, 2));

			add(boardPanel, BorderLayout.CENTER);
			add(commandPanel, BorderLayout.SOUTH);

			initMenuBar();
			initHelpDialog();
			initButtons();

		}

		private void initMenuBar() {
			JMenuBar mBar = new JMenuBar();
			JMenu mGame = new JMenu("Game");
			JMenuItem helpMenu = new JMenuItem("Help");
			JMenuItem moveMenu = new JMenuItem("Next move");
			JMenuItem playerMenu = new JMenuItem("Previous move");

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
//				buttons[i].setForeground(Color.BLACK);
//				buttons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 0));
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
			decodePieces(); //connection.getPieces(); // server
			//int myID = connection.getID();

			for (int i=0; i < buttons.length; i++) {
				buttons[i].setPiece( Piece.NONE );
				buttons[i].choosePiece();
	System.out.println(buttons[i].getPiece());
			}

			for (int i=0; i < positions.length; i++) {
				for (int j=0; j < positions[i].length; j++) {
	//System.out.println(myID);
					int pieceColor = GameHelperMethods.positionInArrayToId(i, numOfPlayers);
					Piece piece = PieceHelperMethods.idToPiece(pieceColor);
					FieldButton but = buttons[positions[i][j]];

					but.setPiece(piece);
	System.out.println(but.getPiece());
					but.choosePiece();
	System.out.println(Integer.toString(i) + "," + Integer.toString(j));

				}

			}

		}

		void decodePieces() {
			int[] allPieces = board.getPiecesPositions();

			for (int i=0; i < positions.length; i++) {
				for (int j=0; j < positions[i].length; j++) {
					positions[i][j] = allPieces[i * numOfPlayerPieces + j];
				}
			}


		}

		/**
		 * metoda informujaca uzytkownika o problemach uniemozliwiajacych dalsza gre
		 * informuje tez o sytuacji w ktorej ktorys z graczy konczy rozgrywke,
		 * mimo ze nie musi to konczyc gry
		 * @param x wyjatek przekazujacy informacje o problemie lub zwyciezcy
		 */
/*		public void signalise(Exception x) {
			more = false;

			if (x instanceof GameEndedException) {
				dialogText.setText(x.getMessage());
			}
			else {
				dialogText.setText("An error has occured:\n" + x.getMessage());
			}

			infoDialog.setVisible(true);

		}
*/

		/**
		 * informuje gracza, ze skonczyl gre
		 */
/*		public void informAboutWinning(int positionInRanking) {
			dialogText.setText("Congratulations!\nYou are " + Integer.toString(positionInRanking) + ".\n");
			infoDialog.setVisible(true);

		}
*/
		private class LabelListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getActionCommand().contentEquals("Help")) {
					showHelpDialog();
				}
				else if (arg0.getActionCommand().contentEquals("Next move")) {
					// show next move
				}
				else if (arg0.getActionCommand().contentEquals("Previous move")) {
					// show previous move
				}

			}

			private void showHelpDialog() {
				dialogText.setText(howToUse + "\n" + authors);
				infoDialog.setVisible(true);
			}

			private void showNextMove() {
				// TODO
			}

			private void showPreviousMove() {
				// TODO
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
/*					if (fb.getPiece() == PieceHelperMethods.idToPiece(connection.getID())) {
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
*/				}
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
