package Project.Database.Tables;
import javax.persistence.*;

@Entity
@Table (name = "games_table")
public class Game_attribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // <zmiana
	private Integer id;
	private int number_of_players;
	public Game_attribute(){
		
	}
	public Game_attribute(int x) {
		this.number_of_players =x;
	}
	
	public void	setId(Integer x) {
		this.id =x;
	}
	public Integer getId() {
		return id;
	}
	public void setnmb_players(int x) {
		this.number_of_players = x;
	}
	public int getnmb_players() {
		return number_of_players;
	}
}
