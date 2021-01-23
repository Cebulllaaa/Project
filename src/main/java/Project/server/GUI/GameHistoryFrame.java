package Project.server.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Project.Database.Procedures.GT_procedures;
import Project.Database.Procedures.MT_procedures;
import Project.Database.Tables.Move_attribute;
import Project.client.GUI.FieldButton;
import Project.client.GUI.FieldOrganizer;
import Project.common.board.AbstractBoard;
import Project.common.board.Piece;
import Project.common.board.PieceHelperMethods;
import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;

public class GameHistoryFrame extends JFrame {

	private static final long serialVersionUID = 1;

	private MT_procedures mtp;
	private Move_attribute move_attr;
	private AbstractBoard board;
	private boolean more = true;
	private JDialog infoDialog;
	private String authors;
	private String howToUse;
	private JTextArea dialogText;
	private FieldButton[] buttons;
	private int whereFrom = 0;
	private int whereTo = 0;
	private int[][] positions;
	private int numOfPlayers;
	private int numOfPlayerPieces;
	private JPanel boardPanel;
	private JPanel commandPanel;
	private int gameID;
	private int moveID;
	private boolean firstMove = true;

	/**
	 * jedyny konstruktor klasy
	 * @param board - plansza zgodna z wyswietlanym typem gry
	 */
	public GameHistoryFrame(AbstractBoard board, GT_procedures g_procedures,
			MT_procedures m_procedures, int game) {
		this.board = board;
		mtp = m_procedures;
		gameID = game;
		move_attr = null;
		moveID = 0;

		int[] allPieces = board.getPiecesPositions();

		try {
			numOfPlayerPieces = GameHelperMethods.getNumberOfPieces( board.getGameType() );
		} catch (WrongGameTypeException wgtx) {
			System.out.println(wgtx);
			System.exit(1);
		}

		// allPieces.length == numOfPlayers * numOfPlayerPositions
		numOfPlayers = allPieces.length / numOfPlayerPieces;
		positions = new int[numOfPlayers][numOfPlayerPieces];

		int n = board.getEdgeLength();

		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		boardPanel = new JPanel();
		commandPanel = new JPanel();

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
		initCommandButtons();
		initButtons();

	}

	private void initMenuBar() {
		JMenuBar mBar = new JMenuBar();
		JMenu mGame = new JMenu("Game");
		JMenuItem helpMenu = new JMenuItem("Help");
		JMenuItem moveMenu = new JMenuItem("Next Move");
		JMenuItem playerMenu = new JMenuItem("Previous Move");

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

	private void initCommandButtons() {
		JButton nextMove = new JButton("Next Move");
		JButton previousMove = new JButton("Previous Move");

		previousMove.addActionListener(new LabelListener());
		nextMove.addActionListener(new LabelListener());

		commandPanel.add(previousMove);
		commandPanel.add(nextMove);
	}

	private void initButtons() {
		buttons = new FieldButton[board.fields.length];

		/*int n = board.getEdgeLength();
				for (int i=buttons.length; i<(3*n-2)*(3*n-2); i++) {
					add(new JPanel());
				}*/
		for (int i=0; i < buttons.length; i++) {
			buttons[i] = new FieldButton();
			buttons[i].setField(board.fields[i], i);

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
				boardPanel.add(new JPanel());
				boardPanel.add(new JPanel());
			}

			for (int j=0; j < oneRow.length; j++) {
				if (i % 2 == 0) {
					boardPanel.add(oneRow[j]);
				}

				boardPanel.add(new JPanel());

				if (i % 2 != 0) {
					boardPanel.add(oneRow[j]);
				}

			}

			for (int j=0; j < halfEmptyCount + width; j++) {
				boardPanel.add(new JPanel());
				boardPanel.add(new JPanel());
			}

		}

	}

	/**
	 * metoda ustawiajaca pionki na planszy kozystajac z {@link ClientConnection#getPieces() ClientConnection.getPieces()}
	 */
	public void setNewPieces() {
		decodePieces();

		for (int i=0; i < buttons.length; i++) {
			buttons[i].setPiece( Piece.NONE );
			buttons[i].choosePiece();

		}

		for (int i=0; i < positions.length; i++) {
			for (int j=0; j < positions[i].length; j++) {
				int pieceColor = GameHelperMethods.positionInArrayToId(i, numOfPlayers);
				Piece piece = PieceHelperMethods.idToPiece(pieceColor);
				FieldButton but = buttons[positions[i][j]];

				but.setPiece(piece);
				but.choosePiece();

			}

		}

	}

	private void decodePieces() {
		int[] allPieces = board.getPiecesPositions();

		for (int i=0; i < positions.length; i++) {
			for (int j=0; j < positions[i].length; j++) {
				positions[i][j] = allPieces[i * numOfPlayerPieces + j];
			}
		}

	}

	private class LabelListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().contentEquals("Help")) {
				showHelpDialog();
			}
			else if (arg0.getActionCommand().contentEquals("Next Move")) {
				showNextMove();
			}
			else if (arg0.getActionCommand().contentEquals("Previous Move")) {
				showPreviousMove();
			}

		}

		private void showHelpDialog() {
			dialogText.setText(howToUse + "\n" + authors);
			infoDialog.setVisible(true);
		}

		private void showNextMove() {
			boolean notFoundYet = true;
			int savedMoveID = moveID;

			do {
				moveID++;
				Optional<Move_attribute> oma = mtp.findById(moveID);

				if ( oma.equals(Optional.empty()) ) {
					dialogText.setText("This is the last move.");
					infoDialog.setVisible(true);
					moveID = savedMoveID;
					break;

				}
				else {
					move_attr = oma.get();

					if (move_attr.get_gameid() == gameID) {
						notFoundYet = false;
						firstMove = false;
						whereFrom = Integer.parseInt( move_attr.get_previous_position() );
						whereTo = Integer.parseInt( move_attr.get_next_position() );
						board.makeMove(whereFrom, whereTo);
						setNewPieces();

					}

				}

			} while (notFoundYet);

		}

		private void showPreviousMove() {
			boolean notFoundYet = true;
			int savedMoveID = moveID;

			if (firstMove) {
				dialogText.setText("This is the first move.");
				infoDialog.setVisible(true);
				return;

			}

			// wykonanie ruchu w odwrotnej kolejnosci
			whereFrom = Integer.parseInt( move_attr.get_next_position() );
			whereTo = Integer.parseInt( move_attr.get_previous_position() );
			board.makeMove(whereFrom, whereTo);
			setNewPieces();

			do {
				moveID--;
				Optional<Move_attribute> oma = mtp.findById(moveID);

				if ( oma.equals(Optional.empty()) ) {
					firstMove = true;
					dialogText.setText("This is the first move.");
					infoDialog.setVisible(true);
					moveID = savedMoveID - 1;
					break;

				}
				else {
					move_attr = oma.get();

					if (move_attr.get_gameid() == gameID) {
						notFoundYet = false;
					}

				}

			} while (notFoundYet);

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

}
