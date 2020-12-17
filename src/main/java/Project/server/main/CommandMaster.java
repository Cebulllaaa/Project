package Project.server.main;

import Project.Game.Game;
import Project.Game.TrylmaGame;

/**Klasa do zarzadzania komendami*/
/**serwer przesyla po kolei:
	 *    id klienta, typ gry (int), liczbe wszystkich graczy
	 *    w grze (musi byc znana na poczatku), listy pozycji pionkow kolejnych graczy */
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
					setCommand("-1;TODO");
				}
			}
		}
		private synchronized void setIDcommand(String x) {
			if(x.isEmpty()) {
				setCommand("1;TODO");
			}
			else {
				int i = x.indexOf(';');
				String last_player_id = x.substring(0, i);
				try {
					int last_player = Integer.parseInt(last_player_id);
					String new_player_id = Integer.toString(last_player+1);
					setCommand(new_player_id + ";TODO");
				}
				catch(Exception e) {
					
				}
			}
			
		}
	
}
