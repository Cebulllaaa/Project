package Project.Database.Tables;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "moves_table")

public class Move_attribute {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private int game_id;
	private String	previous_position;
	private String next_position;
	public Move_attribute() {
		
	}
	public Move_attribute(int x, String y , String z) {
		this.game_id = x;
		this.previous_position = y;
		this.next_position = z;
	}
	public void set_gameid(int x) {
		this.game_id =x;
	}
	public int get_gameid() {
		return game_id;
	}
	public void set_previous_position(String x) {
		this.previous_position =x;
	}
	public String get_previous_position () {
		return previous_position;
	}
	public void set_next_position(String x) {
		this.next_position = x;
	}
	public String get_next_position() {
		return next_position;
	}
}
