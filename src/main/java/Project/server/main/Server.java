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
	public void write() {
		try {
			client_socket = socket.accept();
			writer = new PrintWriter(client_socket.getOutputStream());
			writer.print(command);
			writer.flush();
			writer.close();
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}
	public int choose_Type() {
		//Na chwile obecna wymagany jest tylko wariant gry "chiñskie warcaby "
		return 1;
	}
	public void estabilishConnection() {
		try {
			client_socket = socket.accept();
			
		}
		catch(IOException e) {
			
		}
	}
}
