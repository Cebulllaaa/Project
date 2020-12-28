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
		try {
		server = new Server(port);
		server.create_Server();
		stc = new StartServerConnection(server);
		stc.start();
		System.out.println("Czekam na graczy ");
		while(server.command_ms.game.Players.size() < players) {
			stc.join(1);
		}
		System.out.println("Dodalem " + server.command_ms.game.Players.size() + "graczy");
		server.command_ms.activiti =ServerActivities.SEND_GAME_INFORMATION;
		stc.join(100);

		System.out.println("Wysylam informacje o starcie gry");
		server.command_ms.game.create_Queue();
		server.command_ms.activiti =ServerActivities.SEND_START_GAME;
		stc.join(100);
		
		this.ingame();
		
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void ingame() {
		
		try {
//			while (true) {
			System.out.println("Wysylam tablice");
			server.command_ms.activiti =ServerActivities.SEND_BOARD;
			stc.join(100);
			
			System.out.println("Wysylam Czyja kolej");
			server.command_ms.activiti =ServerActivities.SEND_WHOSE_TURN;
			stc.join(100);
			System.out.println(server.command_ms.getCommand());

			System.out.println(server.command_ms.game.getMove());
//			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
