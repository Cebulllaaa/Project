package Project.Database.Tables;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "moves_table")

public class Move_attribute {
	private int game_id;
	private int	previous_position;
	private int next_position;
	public Move_attribute() {
		
	}
	public void set_gameid(int x) {
		this.game_id =x;
	}
	public int get_gameid() {
		return game_id;
	}
	public void set_previous_position(int x) {
		this.previous_position =x;
	}
	public int get_previous_position () {
		return previous_position;
	}
	public void set_next_position(int x) {
		this.next_position = x;
	}
	public int get_next_position() {
		return next_position;
	}
}
