package Project.common.board;

public class StandartBoard extends AbstractBoard {

	/**
	 * calkowita liczba pol
	 */
	public static final int TOTAL_NUMBER_OF_FIELDS = 121;

	public StandartBoard() {
		fields = new Field[TOTAL_NUMBER_OF_FIELDS];
	}

}
