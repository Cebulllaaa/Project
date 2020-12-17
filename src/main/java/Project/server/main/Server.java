package Project.server.main;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Project.Game.*;
/**G³ówna klasa odpowiadjaca za dzia³anie serwera*/
public class Server {
	
	public ServerSocket socket;
	public BufferedReader reader;
	public InputStreamReader inputreader;
	public PrintWriter writer;
	public Socket client_socket;
	public static int port;
	public int connection_iterator=0;
	public int connected =0;
	public boolean for_connection_test =false;
	/** Skladnia komendy podczas odbioru powinna zawierac informacje , od ktorego klienta pochodzi, jak funkcje
	 * nalezy wywolac oraz argumenty odzdzielone ; . Natomiast podczas wysylania powinna zawierac informacje
	 * ktorego klienta dotyczy , jaka funkcje powinien wykonac klient oraz argumenty oddzielone ;
	 */
	public String command;
	
	public int type_Game;
	public Game game;
	
	public Server(int x) {
		this.port=x;
		type_Game = choose_Type();
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
		command = "";
		int c;
		while(command.isEmpty()) {
			try {
				try {
					client_socket = socket.accept();
					}
				catch (IOException e) {
					System.out.println(e);
				}
				inputreader = new InputStreamReader(client_socket.getInputStream());
				reader = new BufferedReader(inputreader);
				command = reader.readLine();
				
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
	public void estabilishConnection() {
		while(connection_iterator <1) {
			try {
				client_socket = socket.accept();
				connected = connected+1;
				//Od tego miejsca zaczynam pisac writer na ktory beda nowe testy
				if(!for_connection_test) {
				ThreadServerWrite write = new ThreadServerWrite(client_socket,command);
				write.run();
				}
			}
			catch(IOException e) {
				System.out.println(e);
			}
		}
	}
}
