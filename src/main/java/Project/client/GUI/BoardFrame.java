package Project.client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.plaf.basic.BasicBorders;

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
	private FieldButton[] buttons;
	private int whereFrom = 0;
	private int whereTo = 0;
	private int pressed = -1;

	public BoardFrame(AbstractBoard ab) {
//		super();
		board = ab;

		int n = board.getEdgeLength();

		setLayout(new GridLayout(3 * n - 2, 3 * n - 2));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
board.initPieces(6);

		initMenuBar();
		initHelpDialog();
		initButtons();

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
		dialogText = new JTextArea(howToUse + "\n" + authors, 6, 1);

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

	private void initButtons() {
		buttons = new FieldButton[board.fields.length];

		int n = board.getEdgeLength();
		JPanel p = new JPanel();
				for (int i=buttons.length; i<(3*n-2)*(3*n-2); i++) {
					add(p);
				}
		for (int i=0; i < buttons.length; i++) {
			buttons[i] = new FieldButton("###");
//			buttons[i].setBorder(new BasicBorders.ButtonBorder(getForeground(), getForeground(), null, null));
			buttons[i].setField(board.fields[i], i);
			buttons[i].addActionListener(new FieldsListener());

			add(buttons[i]);

		}

	}

/*	public void initPanel() {
		for (int i=0; i < buttons.length; i++) {
			add(buttons[i]);
		}
	}*/

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
			connection.setChange(whereFrom, whereTo);
			connection.makeMove();
		}

	}

	private class DialogButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			infoDialog.setVisible(false);
		}
	}

	private class FieldsListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			FieldButton fb = (FieldButton)arg0.getSource();
			int index = fb.getID();
			// if (fb.getPiece() != Piece.NONE)
			if (pressed == -1) {
				pressed = index;
				whereFrom = index;
				fb.setBackground(Color.BLACK);
			}
			else {
				buttons[pressed].chooseColor();
				pressed = -1;
				whereTo = index;
				fb.setForeground(Color.BLACK);
			}

		}

	}

}
