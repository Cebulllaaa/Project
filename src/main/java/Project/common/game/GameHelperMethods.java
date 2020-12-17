package Project.common.game;

import Project.common.exceptions.WrongGameTypeException;

public class GameHelperMethods {

	public static int getGameCode(GameType gt) throws WrongGameTypeException {
		switch (gt) {
		case STANDART:
			return 1;

		default:
			throw new WrongGameTypeException("Error: Given game is undefined");
		}
	}

	public static GameType getGameType(int gc) throws WrongGameTypeException {
		switch (gc) {
		case 1:
			return GameType.STANDART;

		default:
			throw new WrongGameTypeException("Error: There is no game with the given code");
		}
	}

	public static int getNumberOfPieces(GameType gt) throws WrongGameTypeException {
		switch (gt) {
		case STANDART:
			return 10;

		default :
			throw new WrongGameTypeException("Error: Given game is undefined");
		}
	}

}
