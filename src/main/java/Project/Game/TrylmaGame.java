package Project.Game;

import java.util.ArrayList;
import java.util.Random;
import Project.common.board.StandartBoard;

/**Klasa dla konkretnej gry Trylma ("chiñskie warcaby") */
public class TrylmaGame extends Game{
	/**Zmienna okreslajaca ktore miejsce w kolejce wykona nastepny ruch*/
	public int in_Queue;
	
	public StandartBoard board;
	
	public TrylmaGame() {
		possibilities = new ArrayList<Integer>();
		possibilities.add(2);
		possibilities.add(3);
		possibilities.add(4);
		possibilities.add(6);
		max_Players = 6;
		min_Players = 2;
		Players = new ArrayList<Integer>();
		this.create_Board();
		in_Queue=0;
	}
	@Override
	public void create_Board() {
		board = new StandartBoard();	
	}

	@Override
	public void start_Game() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end_Game() {
		// TODO Auto-generated method stub
		
	}
/**Funkcja zwraca id gracza ktorego jest kolej */
	@Override
	public int decide_Turn() {
		int x = Queue.get(in_Queue);
		in_Queue = in_Queue +1;
		if(in_Queue == Queue.size()) {
			in_Queue = 0;
		}
		return x;
		
	}

	@Override
	public void update_Board() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open_Waitingroom() {
		// TODO 
		
	}

	@Override
	public void close_Waitingroom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add_to_Waitingroom() {
		// TODO Auto-generated method stub
		
	}
/**Funkcja na podstawie listy graczy tworzy losowa kolejke ruchow */
	@Override
	public void create_Queue() {
		Queue = new ArrayList<Integer>();
		ArrayList<Integer>not_in_Queue = (ArrayList<Integer>) Players.clone();
		int to_Queue;
		Random rand = new Random();
		while(not_in_Queue.size() >0 ) {
			to_Queue = rand.nextInt(not_in_Queue.size());
			Queue.add(not_in_Queue.get(to_Queue));
			not_in_Queue.remove(to_Queue);
		}
		
	}
/**Funkcja dodaje graczy do listy graczy */
	@Override
	public void add_Player() {
		if(Players.size() < max_Players) {
		Players.add(Players.size()+1);
		}
		else {
			System.out.println("osiagnieta maksymalna liczbe graczy");
		}
	}

}
