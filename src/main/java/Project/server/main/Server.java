package Project.server.main;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**G³ówna klasa odpowiadjaca za dzia³anie serwera*/
public class Server {
	
	public ServerSocket socket;
	public BufferedReader reader;
	public InputStreamReader inputreader;
	public PrintWriter writer;
	public Socket client_socket;
	public static int port;
	public int connection_iterator;
	/** Skladnia komendy podczas odbioru powinna zawierac informacje , od ktorego klienta pochodzi, jak funkcje
	 * nalezy wywolac oraz argumenty odzdzielone ; . Natomiast podczas wysylania powinna zawierac informacje
	 * ktorego klienta dotyczy , jaka funkcje powinien wykonac klient oraz argumenty oddzielone ;
	 */
	public CommandMaster command_ms;
	
	public int type_Game;
	SendActivities com_activiti;
	
	public Server(int x) {
		this.port=x;
		type_Game = choose_Type();
		com_activiti = SendActivities.SEND_ID;
		command_ms = new  CommandMaster(type_Game,com_activiti);
		connection_iterator=0;
		
	}
	public void create_Server() {
		try {
			socket = new ServerSocket(this.port);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void listen() {
		command_ms.setCommand("");
		int c;
		while(command_ms.getCommand().isEmpty()) {
			try {
				try {
					client_socket = socket.accept();
					}
				catch (IOException e) {
					System.out.println(e);
				}
				inputreader = new InputStreamReader(client_socket.getInputStream());
				reader = new BufferedReader(inputreader);
				command_ms.setCommand(reader.readLine());
				System.out.println(command_ms.getCommand());
			}
			catch(IOException e) {
				System.out.println(e);
			}
		}
	}
	public int choose_Type() {
		//Na chwile obecna wymagany jest tylko wariant gry "chiñskie warcaby "
		return 1;
	}
	public void write() {
		while(connection_iterator <1) {
			try {
				client_socket = socket.accept();
				command_ms.CommandMenu();
				ThreadServerWrite write = new ThreadServerWrite(client_socket,command_ms.getCommand());
				write.run();
				}
			catch(IOException e) {
				System.out.println(e);
			}
		}
	}
}
