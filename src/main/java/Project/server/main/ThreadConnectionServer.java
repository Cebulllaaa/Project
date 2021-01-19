package Project.server.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*Watek odpowiadajacy za polaczenia serwera */
public class ThreadConnectionServer extends Thread{
	public Server serwer;
	public Socket client_socket;
	public CommandMaster command_ms;
	public BufferedReader reader;
	public InputStreamReader inputreader;
	public ThreadServerWrite write;
	
	public String move;
	public int ID;
	public ThreadConnectionServer (Server x, Socket y, CommandMaster z) {
		this.serwer=x;
		this.client_socket = y;
		this.command_ms = z;
		this.ID =command_ms.game.Players.size() +1;
	}
	@Override
	public void run() {
		try {
			/*Nasluchiwanie od klienta prosby o ID */
			command_ms.activiti = ServerActivities.LISTEN;
			inputreader = new InputStreamReader(client_socket.getInputStream());
			reader = new BufferedReader(inputreader);
			command_ms.setCommand(reader.readLine());
			
			this.yield();
			
			/*Wysylanie do klienta jego ID*/
			if(command_ms.getCommand().equals("-1")) {
				command_ms.activiti = ServerActivities.SEND_ID;
				command_ms.CommandMenu();
				write = new ThreadServerWrite(client_socket,command_ms.getCommand());
				write.run();
			}
			/*Wysylanie do klienta informacji o grze */
			while(!command_ms.activiti.equals(ServerActivities.SEND_GAME_INFORMATION) ) {
				this.yield();
			}
			command_ms.CommandMenu();
			write = new ThreadServerWrite(client_socket,command_ms.getCommand());
			write.run();
			
			/*Wysylanie do klienta informacji o starcie gry */
			while(!command_ms.activiti.equals(ServerActivities.SEND_START_GAME) ) {
				this.yield();
			}
			command_ms.CommandMenu();
			write = new ThreadServerWrite(client_socket,command_ms.getCommand());
			write.run(); 
			
			/*Przebieg gry */
			while(command_ms.game.Players.size()>1) {
				
				/*Wyslanie tablicy */
				while(!command_ms.activiti.equals(ServerActivities.SEND_BOARD) ) {
					this.yield();
				}
				command_ms.CommandMenu();
				write = new ThreadServerWrite(client_socket,command_ms.getCommand());
				write.run();
				/*Wyslanie czyja kolej */
//				while(!command_ms.activiti.equals(ServerActivities.SEND_WHOSE_TURN) ) {
				while(!(command_ms.activiti.equals(ServerActivities.SEND_WHOSE_TURN) // <zm
						|| command_ms.activiti.equals(ServerActivities.SEND_END_GAME)) ) { // <zm
/*					if(command_ms.activiti.equals(ServerActivities.SEND_END_GAME)) {
						command_ms.CommandMenu();
						write = new ThreadServerWrite(client_socket,command_ms.getCommand());
						write.run();
						if(command_ms.game.Players.size()==1) {
							break;
						}
					}
*/					this.yield();
				}
				if(command_ms.activiti.equals(ServerActivities.SEND_END_GAME)) { // <zm
					command_ms.CommandMenu(); // <zm
					write = new ThreadServerWrite(client_socket,command_ms.getCommand()); // <zm
					write.run(); // <zm
					if(command_ms.game.Players.size()==1) { // <zm
						break; // <zm
					} // <zm
					else { // <zm
						continue; // <zm
					} // <zm
				} // <zm
				command_ms.CommandMenu();
				write = new ThreadServerWrite(client_socket,command_ms.getCommand());
				write.run();
				this.yield();
				
				/*Oczekiwanie na odpowiedz gracza */
				move = reader.readLine();
				if(!move.equals("-2")) {
					command_ms.game.setMove(move);
				}
			
			}
			System.out.println("SKonczylem ");
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		while(true) {
			
		}
	}

}
