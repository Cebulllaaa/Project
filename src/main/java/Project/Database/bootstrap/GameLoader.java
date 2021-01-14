package Project.Database.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import Project.Database.Procedures.GT_procedures;
import Project.Database.Tables.Game_table;

@Component
public class GameLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	private GT_procedures procedures;
	private Logger log = Logger.getLogger(GameLoader.class);
	
	@Autowired
	public void set_GT_procedures(GT_procedures x) {
		this.procedures =x;
	}
	
	public void onApplicationEvent(ContextRefreshedEvent event) {

        Game_table game1 = new Game_table();
        game1.setnmb_players(6);
        procedures.save(game1);

        log.info("Saved game1");

        Game_table game2 = new Game_table();
        game1.setnmb_players(2);
        procedures.save(game2);

        log.info("Saved game2" );
    }

}
