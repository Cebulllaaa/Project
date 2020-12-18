package Project.client.connection;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

public class ConnectionTest {

	private TestServer server;
	private ClientConnection client;

	@Test(timeout = 1000, expected = IOException.class)
	public void testNotConnected() throws Exception {
		client = new ClientConnection();
		client.connect("localhost", 8080);
	}
	@Ignore
	public void testAccept() {
		try {
			server = new TestServer();
		}
		catch (IOException iox) {
			;
		}
	}

}
