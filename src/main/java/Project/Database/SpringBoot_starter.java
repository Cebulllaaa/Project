package Project.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import Project.Database.Procedures.GT_procedures;
import Project.Database.Procedures.MT_procedures;
import Project.Database.Tables.Game_attribute;
import Project.Database.Tables.Move_attribute;

@SpringBootApplication
public class SpringBoot_starter {
	@Autowired
	private GT_procedures game_procedures;
	@Autowired
	private MT_procedures move_procedures;
	public int game_id;
	public Game_attribute game;
	public Move_attribute move;
	
	public static void main(String[] args) {
        SpringApplication.run(SpringBoot_starter.class, args);
    }
	public void add_game(int x) {
		game = new Game_attribute(x);
		this.game_procedures.save(game);
		game_id = game.getId();
	}
	public void add_move(int x, String prv, String next) {
		move = new Move_attribute(game_id,prv,next);
		this.move_procedures.save(move);
	}
}
