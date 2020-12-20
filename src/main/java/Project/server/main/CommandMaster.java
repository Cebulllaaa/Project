package Project.server.main;

import Project.common.game.Game;
import Project.common.game.TrylmaGame;

/**Klasa do zarzadzania komendami*/
/**serwer przesyla po kolei:
	 *   nr instrukcji
	 *   argumenty oddzielone œrednikiem*/
public class CommandMaster {
	
		private String command;
		public  boolean before_start;
		public Game game;
		public boolean success;
		public ServerActivities activiti;
		private int gametype;
	/**Konstruktor dla CommandMaster*/
		public CommandMaster(int x, ServerActivities y) {
			this.command = "";
			this.gametype=x;
			before_start = true;
			if(x ==1) {
			this.game =new TrylmaGame();
			this.activiti = y;
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
				if(success) {
					setIDcommand(command);
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
		private synchronized void setIDcommand(String x) {
				try {
					int id = game.Players.size();
					String new_player_id = Integer.toString(id);
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
			setCommand("4;TODO;");
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
}
