package Project.server.main;

import Project.common.board.AbstractBoard;
import Project.common.game.Game;
import Project.common.game.TrylmaGame;

/**Klasa do zarzadzania komendami*/
/**serwer przesyla po kolei:
	 *   nr instrukcji
	 *   argumenty oddzielone �rednikiem*/
public class CommandMaster {
	
		private String command;
		public  boolean before_start;
		public Game game;
		public int success;
		public ServerActivities activiti;
		private int gametype;
		private boolean started ;
	/**Konstruktor dla CommandMaster*/
		public CommandMaster(int x, ServerActivities y,int z) {
			this.command = "";
			this.gametype=x;
			before_start = true;
			if(x ==1) {
			this.game =new TrylmaGame();
			game.open_Waitingroom(z);
			this.activiti = y;
			started = false;
			}
		}
	/**Funkcja pobierajaca komende */
		public synchronized String getCommand() {
			return command;
		}
	/** Funckaj ustawiajaca komende */
		public synchronized void setCommand(String x) {
			this.command = x;
		}
	/**Publiczna funkcja do analizy ktora aktywnosc serwer powinien zrealizowac */	
		public synchronized void CommandMenu() {
			if(activiti.equals(ServerActivities.SEND_ID)){
				success = game.add_Player();
				if(success >-1) {
					setIDcommand(success);
				}
				else {
					setCommand("1;-1;");
				}
			}
			else if(activiti.equals(ServerActivities.SEND_GAME_INFORMATION)) {
				sendGameInfo();
			}
			else if(activiti.equals(ServerActivities.SEND_START_GAME)) {
				sendStartInfo();
				
			}
			else if(activiti.equals(ServerActivities.SEND_BOARD)) {
				sendBoard();
			}
			else if(activiti.equals(ServerActivities.SEND_WHOSE_TURN)) {
				sendTurn();
			}
			else if(activiti.equals(ServerActivities.SEND_END_GAME)) {
				sendEndInfo();
			}
			else if(activiti.equals(ServerActivities.WRITE_TEST)) {
				
			}
			else {
				setCommand("-1;");
				System.out.println("Cos poszlo nie tak");
				System.out.println(activiti);
			}
		}
		private synchronized void setIDcommand(int x) {
				try {
					String new_player_id = Integer.toString(x);
					setCommand("1;"+new_player_id + ";");
				}
				catch(Exception e) {
					
				}
			}
			
		private void sendGameInfo() {
			if(game.getClass().equals(TrylmaGame.class)) {
				setCommand("2;");
				int players = game.Players.size();
				String players_st = Integer.toString(players);
				setCommand(command + players_st +";"+  Integer.toString(gametype)+";");
			}
		}
		private void sendStartInfo() {
			setCommand("3;");
		}
		private void sendBoard() {
			// Teraz dziala
			String arrOfPositions = "4";
			AbstractBoard gameBoard = game.board;
			int[] positions = gameBoard.getPiecesPositions();

			if (positions == null) {
				gameBoard.initPieces(game.Players.size());
				positions = gameBoard.getPiecesPositions();
			}

			for (int i=0; i < positions.length; i++) {
				arrOfPositions += ";" + Integer.toString(positions[i]);
			}

			setCommand(arrOfPositions);
			
			//setCommand("4;1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;");
		}
		
		private void sendTurn() {
			setCommand("5;");
			int id_next = game.decide_Turn();
			String id_s = Integer.toString(id_next);
			setCommand(command + id_s +";");
		}
		private void sendEndInfo() {
			setCommand("6;");
			int winner = game.get_winner();
			String winner_s = Integer.toString(winner);
			setCommand(command + winner_s + ";");
		}
		public void setStarted(boolean x) {
			this.started =x;
		}
		public boolean getStarted() {
			return started;
		}
}
