package Project.common.board;

public class PieceHelperMethods {

	/**
	 * zamienia kolor pionka na id wlasciciela
	 * @param piece kolor pionka
	 * @return id wlasciciela
	 */
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

	/**
	 * zamienia id wlasciciela na kolor pionka
	 * @param id id wlasciciela
	 * @return kolor pionka
	 */
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

	/**
	 * podaje kolor celu pionka o podanym kolorze
	 * @param piece kolor pionka
	 * @return kolor celu
	 */
	public static Piece getHome(Piece piece) {
		switch (piece) {
		case BLUE:
			return Piece.RED;

		case GREEN:
			return Piece.WHITE;

		case ORANGE:
			return Piece.YELLOW;

		case RED:
			return Piece.BLUE;

		case WHITE:
			return Piece.GREEN;

		case YELLOW:
			return Piece.ORANGE;

		default:
			return Piece.NONE;

		}

	}

	/**
	 * podaje id gracza w kolorze celu gracza o podanym id
	 * @param id id gracza
	 * @return id celu
	 */
	public static int getHome(int id) {
		return (id + 2) % 3 + 1;
	}

}
