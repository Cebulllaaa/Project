package Project.common.game;

import Project.client.connection.ClientConnection;
import Project.common.exceptions.WrongGameTypeException;

public class GameHelperMethods {

	/**
	 * zamienia typ gry na kod typu gry
	 * @param gt typ gry
	 * @return kod typu gry
	 * @throws WrongGameTypeException wyrzucany gdy podano nieznany typ gry
	 */
	public static int getGameCode(GameType gt) throws WrongGameTypeException {
		switch (gt) {
		case STANDART:
			return 1;

		default:
			throw new WrongGameTypeException("Error: Given game is undefined");
		}
	}

	/**
	 * zamienia kod typu gry na typ gry
	 * @param gc kod typu gry
	 * @return typ gry
	 * @throws WrongGameTypeException wyrzucany jesli podano nieprawidlowy kod
	 */
	public static GameType getGameType(int gc) throws WrongGameTypeException {
		switch (gc) {
		case 1:
			return GameType.STANDART;

		default:
			throw new WrongGameTypeException("Error: There is no game with the given code");
		}
	}

	/**
	 * podaje liczbe pionkow, ktore ma jeden gracz w danym rodzaju gry
	 * @param gt typ gry
	 * @return liczba pionkow jednego gracza
	 * @throws WrongGameTypeException wyrzucany, gdy podano nieznany typ gry
	 */
	public static int getNumberOfPieces(GameType gt) throws WrongGameTypeException {
		switch (gt) {
		case STANDART:
			return 10;

		default :
			throw new WrongGameTypeException("Error: Given game is undefined");
		}
	}

	/**
	 * zamienia id gracza (ktore odpowiada kolorowi jego pionkow)
	 *    na jego numer w kolejnosci zgloszenia
	 *    (odpowiadajcy indeksowi jego pionkow w tablicy
	 *    {@link ClientConnection#getPieces() ClientConnection.getPieces()})
	 * @param playerID id gracza
	 * @param numberOfPlayers liczba graczy w grze
	 * @return numer gracza w kolejnosci zgloszenia do gry
	 */
	public static int idToPositionInArray(int playerID, int numberOfPlayers) {
		switch (numberOfPlayers) {
		case 2:
			return playerID / 3; // wynik: 1 -> 0, 4 -> 1 (wiecej id nie ma)

		case 3:
			return playerID / 2; // wynik: 1 -> 0, 3 -> 1, 5 -> 2 (wiecej id nie ma)

		case 4:
			return playerID - ((playerID >= 4) ? 2 : 1); // wynik: 1 -> 0, 2 -> 1, 4 -> 2, 5 -> 3 (wiecej id nie ma)

		case 6:
			return playerID - 1; // wynik: 1 -> 0, 2 -> 1, 3 -> 2, 4 -> 3, 5 -> 4, 6 -> 5 (wiecej id nie ma)

		default:
			return -1;

		}

	}

	/**
	 * zamienia numer gracza w kolejnosci zgloszenia
	 *    (odpowiadajcy indeksowi jego pionkow w tablicy
	 *    {@link ClientConnection#getPieces() ClientConnection.getPieces()})
	 *    na jego id (ktore odpowiada kolorowi jego pionkow)
	 * @param posInArr numer gracza w kolejnosci zgloszenia do gry
	 * @param numberOfPlayers liczba gracz w grze
	 * @return id gracza
	 */
	public static int positionInArrayToId(int posInArr, int numberOfPlayers) {
		switch (numberOfPlayers) {
		case 2:
			return posInArr * 3 + 1; // wynik: 0 -> 1, 1 -> 4

		case 3:
			return posInArr * 2 + 1; // wynik: 0 -> 1, 1 -> 3, 2 -> 5

		case 4:
			return posInArr + ((posInArr >= 2) ? 2 : 1); // wynik: 0 -> 1, 1 -> 2, 2 -> 4, 3 -> 5

		case 6:
			return posInArr + 1; // wynik: 0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 4 -> 5, 5 -> 6

		default:
			return -1;
		}
	}

}
