package Project.client.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameHelperMethods;
import Project.common.game.GameType;

public class TestServerReading extends TestServer {

	private LinkedList<String> receivedMessages;

	public TestServerReading(int id, int playersCount, GameType gameType, int[] pieces, int moves) throws IOException, WrongGameTypeException {
		super(id, playersCount, gameType, pieces, moves);
		receivedMessages = new LinkedList<String>();
	}

	public TestServerReading(int id, int playersCount, int gameCode, int[] pieces, int moves) throws IOException, WrongGameTypeException {
		super(id, playersCount, gameCode, pieces, moves);
		receivedMessages = new LinkedList<String>();
	}

	@Override
	public void acceptClient() {
		super.acceptClient();
		receivedMessages.add(in.nextLine());
	}
	@Override
	public void continueGame(int id, int[] pieces) {
		out.print("5;");
		out.print(id);
		out.println();

		out.flush();

		try {
			Thread.sleep(10);
		}
		catch (InterruptedException ix) {
			;
		}

		out.print("4");
		for (int i=0; i<pieces.length; i++) {
			out.print(";");
			out.print(pieces[i]);
		}
		out.println();

		out.flush();

		try {
			Thread.sleep(10);
		}
		catch (InterruptedException ix) {
			;
		}

		//for (int i=0; i < playersCount-2; i++ ) {
		receivedMessages.add(in.nextLine());
		//}

	}

	public LinkedList<String> getReceivedMessages() {
		return receivedMessages;
	}
}
