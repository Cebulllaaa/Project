package Project.Game;
import java.util.ArrayList;

/**Klasa abstrakcyjna zawierajaca podstawy do dowolnego trybu gry (w przypadku gdyby nale¿a³o rozszerzy apliakcje o nowe tryby */
public abstract class Game {
	/**Lista Graczy w danej grze */
	public ArrayList<Integer> Players;
	/**Maksymalna liczba graczy potrzebnych do uruchomienia gry */
	public int max_Players;
	/**Minimalna liczba graczy potrzebnych do uruchomienia gry */
	public int min_Players;
	/**Lista wszystkich mozliwych graczy potrzebnych do uruchomienia gry */
	public ArrayList<Integer> possibilities;
	public ArrayList<Integer> Queue;
	/**Funkcja tworzaca plansze */
	public abstract void create_Board();
	/**Funkcja odpowiedzialna za rozpoczecie gry */
	public abstract void start_Game();
	/**Funkcja odpowiedzialna za zakonczenie gry */
	public abstract void end_Game();
	/**Funkcja odpowiedzialna za decyzje czyja jest kolej */
	public abstract int decide_Turn();
	/**Funkcja aktualizujaca plansze */
	public abstract void update_Board();
	/**Funkcja otwierajaca i tworzaca poczekalnie */
	public abstract void open_Waitingroom();
	/**Funkcja zamykajaca poczekalnie (uruchamia sie keidy zabraknie w niej miejca lub wystarczajaca ilosc graczy zdecyduje 
	 * ze chce zaczac gre)*/
	public abstract void close_Waitingroom();
	/**Funkcja dodajaca graczy do poczekalni */
	public abstract void add_to_Waitingroom();
	/**Funkcja tworzaca kolejke graczy */
	public abstract void create_Queue();
	/**Funkcja dodajaca gracza */
	public abstract boolean add_Player();
	
}
