package Project.common.board;

public class PieceHelperMethods {

	public static int pieceToID(Piece piece) {
		switch (piece) {
		case BLUE:
			return 1;

		case GREEN:
			return 2;

		case ORANGE:
			return 3;

		case RED:
			return 4;

		case WHITE:
			return 5;

		case YELLOW:
			return 6;

		default: // NONE
			return 0;
		}

	}

	public static Piece idToPiece(int id) {
		switch (id) {
		case 1:
			return Piece.BLUE;

		case 2:
			return Piece.GREEN;

		case 3:
			return Piece.ORANGE;

		case 4:
			return Piece.RED;

		case 5:
			return Piece.WHITE;

		case 6:
			return Piece.YELLOW;

		default:
			return Piece.NONE;
		}

	}

}
