package Project.Main.mainpackage;

import java.util.Scanner;

import Project.server.main.Server;
import Project.server.main.ServerActivities;
import Project.server.main.ThreadConnectionServer;

/**
 * Hello world!
 *
 */
public class ServerMain 
{	
	Server server;
	static int port_server;
	ThreadConnectionServer connection;
	boolean end =false;
	boolean correct_move = true;
    public static void main( String[] args )
    {
        port_server = Integer.parseInt(args[0]);
       System.out.println("Rozpoczynanie tworzenia serwera");
       ServerMain main = new ServerMain();
       main.create_server();
        
    }
    public void create_server() {
    	server = new Server(port_server);
    	server.create_Server();
    	System.out.println("Utworzono serwer przechodze do interfejsu serwera");
    	this.interaction();
    }
    public void interaction() {
    	String cmd;
    	Scanner scan;
    	System.out.println("Aby otworzyc serwer i zaczac rozsylac ID wcisnij cokolwiek");
    	System.out.println("Aby skonczyc wpuszczanie graczy i wyslac im informacje wcisnij cokolwiek");
    	scan = new Scanner(System.in);
    	cmd = scan.nextLine();
    	connection = new ThreadConnectionServer(server);
    	connection.start();
    	
    	cmd = scan.nextLine();
    	server.command_ms.activiti = ServerActivities.SEND_GAME_INFORMATION;
    	server.command_ms.activiti=ServerActivities.SEND_START_GAME;
    	server.command_ms.game.create_Queue();
    	in_game();
    	server.command_ms.activiti=ServerActivities.SEND_BOARD;
    	server.command_ms.game.create_Queue();
    	server.command_ms.activiti=ServerActivities.SEND_WHOSE_TURN;
    	
    	
   
    }
    public void in_game() {
    	while(true) {
    		server.command_ms.activiti=ServerActivities.SEND_BOARD;
    		server.command_ms.activiti=ServerActivities.SEND_WHOSE_TURN;
    		server.command_ms.activiti=ServerActivities.LISTEN;
    		while(server.command_ms.getCommand().isEmpty()) {
    			
    		}
    		// sprawdzamy czy poprawny ruch TODO
    		if(correct_move) {
    			server.command_ms.game.increase_Queue();
    		}
    		//sprawdzamy czy ktos skonczyl
    		if(end) {
    			end_game();
    		}
    		
    	}
    }
    public void end_game() {
    	server.command_ms.activiti=ServerActivities.SEND_END_GAME;
    }
}
