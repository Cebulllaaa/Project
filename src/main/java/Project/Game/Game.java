package Project.Game;
/**Klasa abstrakcyjna zawierajaca podstawy do dowolnego trybu gry (w przypadku gdyby nale¿a³o rozszerzy apliakcje o nowe tryby */
public abstract class Game {
	/**Lista Graczy w danej grze */
	public int[] Players;
	/** Funkcja tworzaca plansze do gry */
	public abstract void create_Board();
	/**Maksymalna liczba graczy potrzebnych do uruchomienia gry */
	public int max_Players;
	/**Minimalna liczba graczy potrzebnych do uruchomienia gry */
	public int min_Players;
	/**Funkcja odpowiedzialna za rozpoczecie gry */
	public abstract void start_Game();
	/**Funkcja odpowiedzialna za zakonczenie gry */
	public abstract void end_Game();
	/**Funkcja odpowiedzialna za decyzje czyja jest kolej */
	public abstract void decide_Turn();
	/**Funkcja aktualizujaca plansze */
	public abstract void update_Board();
}
