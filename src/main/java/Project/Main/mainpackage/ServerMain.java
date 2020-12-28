package Project.Main.mainpackage;

import Project.server.main.Server;
import Project.server.main.ServerActivities;
import Project.server.main.StartServerConnection;

public class ServerMain {
	static int port;
	static int players;
	static ServerMain mainclass;
	private Server server;
	private StartServerConnection stc;
	public static void main(String[] args) {
		try {
			port = Integer.parseInt(args[0]);
			players = Integer.parseInt(args[1]);
			mainclass = new ServerMain();
			mainclass.start();
			
		}
		catch(NumberFormatException e) {
			System.out.println("Podano niewlasciwy typ argumentu");
		}
	}
	public void start() {
		server = new Server(port);
		server.create_Server();
		stc = new StartServerConnection(server);
		stc.start();
		while(server.command_ms.game.Players.size() < players) {
			
		}
		server.command_ms.activiti =ServerActivities.SEND_GAME_INFORMATION;
		try {
			stc.join(100);
		}
		catch(InterruptedException e) {
			System.out.println(e);
		}
	}
}
