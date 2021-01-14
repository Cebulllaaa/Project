package Project.Database.Tables;
import javax.persistence.*;

@Entity
public class Game_table {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private int number_of_players;
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
