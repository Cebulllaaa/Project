package Project.server.main;

import Project.Game.Game;
import Project.Game.TrylmaGame;

/**Klasa do zarzadzania komendami*/
/**serwer przesyla po kolei:
	 *   nr instrukcji
	 *   argumenty*/
public class CommandMaster {
	
		private String command;
		public  boolean before_start;
		public Game game;
		public boolean success;
	
		public CommandMaster(int x) {
			this.command = "";
			before_start = true;
			if(x ==1) {
			this.game =new TrylmaGame();
			}
		}
	
		public synchronized String getCommand() {
			return command;
		}
		/** Funkcja wykorzystywana glownie do testow */
		public synchronized void setCommand(String x) {
			this.command = x;
		}
		
		public synchronized void CommandMenu() {
			if(before_start){
				success = game.add_Player();
				if(success) {
					setIDcommand(command);
				}
				else {
					setCommand("1;-1;");
				}
			}
		}
		private synchronized void setIDcommand(String x) {
			if(x.isEmpty()) {
				setCommand("1;1;");
			}
			else {
				int begin = x.indexOf(';');
				int end = x.lastIndexOf(';');
				String last_player_id = x.substring(begin+1, end);
				try {
					int last_player = Integer.parseInt(last_player_id);
					String new_player_id = Integer.toString(last_player+1);
					setCommand("1;"+new_player_id + ";");
				}
				catch(Exception e) {
					
				}
			}
			
		}
	
}
