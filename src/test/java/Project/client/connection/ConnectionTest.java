package Project.client.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;

import Project.client.exceptions.GameEndedException;
import Project.client.exceptions.PlayerNotAllowedException;
import Project.common.exceptions.WrongGameTypeException;
import Project.common.game.GameType;

public class ConnectionTest {

	private TestServer server;
	private ClientConnection client;
	private Thread testing;

	@Test(timeout = 1000, expected = IOException.class)
	public void testNotConnected() throws Exception {
		client = new ClientConnection();
		client.connect("localhost", 8080);
	}

	@Test
	public void testGameReadOnce() throws Exception {
		int[] pieces = new int[60];

		for (int p=0; p<10; p++) {
			for (int i=0; i<60; i++) {
				pieces[i] = i + p;
			}

			communication(p, pieces, 0);
		}

	}

	@Test
	public void testGameReadManyTimes() throws Exception {
		int[] pieces = new int[60];

		for (int p=0; p < 10; p++) {
			for (int i=0; i<60; i++) {
				pieces[i] = i;
			}

			communication(p, pieces, (p%3 == 0)?2*p:p+1);

		}

	}

	@Test(expected = WrongGameTypeException.class)
	public void testWrongGameType() throws Exception {
		int[] pieces = new int[60];
		for (int i=0; i<60; i++) {
			pieces[i] = i;
		}

		server = new TestServer(1, 6, 22, pieces, 0);

		try {
			initConnection();

			while (true) {
				client.read();
			}
		}
		finally {
			disconnect();
		}

	}

	@Test
	public void testGameManyTimes() throws Exception {
		int[] pieces = new int[60];

		for (int p=0; p < 10; p++) {
			for (int i=0; i<60; i++) {
				pieces[i] = i;
			}

			communicationWrite(p, pieces, (p%3 == 0)?2*p:p+1);

		}

	}

	private void communication(int numOfPlay, int[] pieces, int runs) throws Exception {
		try {
			int[] copyPieces = new int[pieces.length];

			for (int i=0; i<copyPieces.length; i++) {
				copyPieces[i] = pieces[i];
			}

			testReading(numOfPlay, 6, GameType.STANDART, copyPieces, runs);

			for (int i=0; i < runs; i++) {
				pieces[i % pieces.length]++;
			}

			boolean expectedIsMyTurn = (runs - 1) % 6 == numOfPlay;

			assertEquals(numOfPlay, client.getId());
			assertEquals(10, client.getNumOfPlayerPieces());
			assertEquals(60, client.getTotalNumOfPieces());
			assertEquals(GameType.STANDART, client.getGameType());
			assertEquals(expectedIsMyTurn, client.isMyTurn());

			for (int i=0; i<6; i++) {
				for (int j=0; j<10; j++) {
					assertEquals(pieces[10*i + j], client.getPieces()[i][j]);
				}
			}
		}
		catch (PlayerNotAllowedException pnax) {
			;
		}

	}

	private void communicationWrite(int numOfPlay, int[] pieces, int runs) throws Exception {
		try {
			int[] copyPieces = new int[pieces.length];

			for (int i=0; i<copyPieces.length; i++) {
				copyPieces[i] = pieces[i];
			}

			testWriting(numOfPlay, 6, GameType.STANDART, copyPieces, runs);

			for (int i=0; i < runs; i++) {
				pieces[i % pieces.length]++;
			}

			boolean expectedIsMyTurn = (runs - 1) % 6 == numOfPlay;

			assertEquals(numOfPlay, client.getId());
			assertEquals(10, client.getNumOfPlayerPieces());
			assertEquals(60, client.getTotalNumOfPieces());
			assertEquals(GameType.STANDART, client.getGameType());
			assertEquals(expectedIsMyTurn, client.isMyTurn());

			for (int i=0; i<6; i++) {
				for (int j=0; j<10; j++) {
					assertEquals(pieces[10*i + j], client.getPieces()[i][j]);
				}
			}
		}
		catch (PlayerNotAllowedException pnax) {
			;
		}

	}

	private void testReading(int id, int players, GameType gt, int[] pieces, int runs) throws Exception {
		server = new TestServer(id, players, GameType.STANDART, pieces, runs);

		try {
			initConnection();

			while (true) {
				client.read();
			}
		}
		catch (GameEndedException gex) {
			assertEquals(gex.getMessage(), Integer.toString(id));
		}
		catch (PlayerNotAllowedException pnax) {
			assertEquals(7, id); // id == 7 oznacza: brak miejsc
			throw pnax;
		}
		finally {
			disconnect();
		}

	}

	private void testWriting(int id, int players, GameType gt, int[] pieces, int runs) throws Exception {
		TestServerReading readingServer = new TestServerReading(id, players, GameType.STANDART, pieces, runs);

		LinkedList<String> sentLines = new LinkedList<String>();

		try {
			client = new ClientConnection();
			testing = new Thread(readingServer);

			testing.start();
			Thread.sleep(100);
			client.connect("localhost", 8080);

			//in = server.getIn();
			int i=0;
			int j=0;

			while (true) {
				client.setChange(i, i-j);

				client.read();
				client.write();

				sentLines.add( "1;" + Integer.toString(i) + ";" + Integer.toString(i-j) );

				j++;

				if (j >= i) {
					j=0;
					i++;
				}

			}

		}
		catch (GameEndedException gex) {
			assertEquals(gex.getMessage(), Integer.toString(id));
		}
		catch (PlayerNotAllowedException pnax) {
			assertEquals(7, id); // id == 7 oznacza: brak miejsc
			throw pnax;
		}
		finally {
			LinkedList<String> received = readingServer.getReceivedMessages();
			while (! sentLines.isEmpty()) {
				assertEquals(sentLines.remove(), received.remove());
			}

			client.endConnection();
			readingServer.closeConnection();
		}

	}

	private void disconnect() throws Exception {
		client.endConnection();
		server.closeConnection();
	}

	@Ignore
	protected void initConnection() throws Exception {
		client = new ClientConnection();
		testing = new Thread(server);

		testing.start();
		Thread.sleep(100);
		client.connect("localhost", 8080);

	}
}
