package Project.Main.mainpackage;

import Project.server.main.Server;
import Project.server.main.ServerActivities;
import Project.server.main.StartServerConnection;

public class ServerMain {
	static int port;
	static int players;
	static ServerMain mainclass;
	public int place =1;
	private Server server;
	private StartServerConnection stc;
	int j = 0;
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
		server = new Server(port,players);
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
			int winnerID = 0;

			do {
				System.out.println("Wysylam tablice");
				server.command_ms.activiti =ServerActivities.SEND_BOARD;
				stc.join(100);
			
				System.out.println("Wysylam Czyja kolej");
				server.command_ms.game.setMove("");
				server.command_ms.activiti =ServerActivities.SEND_WHOSE_TURN;
				stc.join(100);
				System.out.println(server.command_ms.getCommand());
				
				System.out.println("Nasluchuje");
				while(server.command_ms.game.getMove().equals("")) {
					stc.join(1);
				}
				System.out.println(server.command_ms.game.getMove());
			/*
			 * Sprawdzamy czy getMove() jest poprawny
			 * Jezeli jest poprawny to
			 * server.command_ms.game.increase_Queue();
			 * Funkcja sprawdzajaca czy ktos wygral jezeli tak to wyjdz z petli i ustaw zwyciezce
			 * server.command_ms.game.set_winner(int ID_zwyciezcy);
			 */
				System.out.println("Skonczylem nasluchiwa");
				String receivedMove = server.command_ms.game.getMove();
				String[] moveInParts = receivedMove.split(";");
				int fromWhereIndex = Integer.parseInt(moveInParts[1]);
				int whereToIndex = Integer.parseInt(moveInParts[2]);

				if (server.command_ms.game.board.checkRules(fromWhereIndex, whereToIndex)) {
					server.command_ms.game.board.makeMove(fromWhereIndex, whereToIndex);
					winnerID = server.command_ms.game.board.checkWinner();
					if(winnerID != 0) {
						server.command_ms.game.set_winner(winnerID);
						winnerID=0;
						endgame();
					}
					server.command_ms.game.increase_Queue();

				}

			} while (server.command_ms.game.Players.size() >1);

		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void endgame() {
		//server.command_ms.game.set_winner(1);
		System.out.println("Gracz " + server.command_ms.game.get_winner() + " zajal " + place + "miejsce");
		place = place +1;
		server.command_ms.activiti =ServerActivities.SEND_BOARD;
		try {
		stc.join(100);
		}
		catch(InterruptedException e) {
			System.out.println(e);
		}
		server.command_ms.activiti =ServerActivities.SEND_END_GAME;
		try {
			stc.join(100);
		}
		catch(InterruptedException e) {
			System.out.println(e);
		}
		j=1;
		server.command_ms.game.delete_player(server.command_ms.game.get_winner());
		server.command_ms.game.in_Queue =0;
	} 
}
