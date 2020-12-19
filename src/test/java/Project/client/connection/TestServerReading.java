package Project.client.connection;

import java.io.IOException;
import java.net.ServerSocket;

import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

public class TestServerReading extends TestServer {

	public TestServerReading(int id, int playersCount, GameType gameType, int[] pieces, int moves) throws IOException, WrongGameTypeException {
		super(id, playersCount, gameType, pieces, moves);
	}

	public TestServerReading(int id, int playersCount, int gameCode, int[] pieces, int moves) throws IOException, WrongGameTypeException {
		super(id, playersCount, gameCode, pieces, moves);
	}

	@Override
	public void continueGame(int id, int[] pieces) {
		out.print("5;");
		out.print(id);
		out.println();

		out.flush();

		out.print("4");
		for (int i=0; i<pieces.length; i++) {
			out.print(";");
			out.print(pieces[i]);
		}
		out.println();

		out.flush();

		in.nextLine();

	}
}
