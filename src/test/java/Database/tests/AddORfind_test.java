package Database.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import Project.Database.SpringBoot_starter;
import Project.Database.Procedures.GT_procedures;
import Project.Database.Tables.Game_attribute;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = SpringBoot_starter.class)
public class AddORfind_test {
	@Autowired
    private GT_procedures procedures;
	 
    @Before
    public void setUp() throws Exception {
        Game_attribute game1= new Game_attribute(6);
        Game_attribute game2= new Game_attribute(2);
        //Przed zapisaniem powinny byc puste
        assertNull(game1.getId());
        assertNull(game2.getId());
      
        this.procedures.save(game1);
        this.procedures.save(game2);
        //Po zapisaniu nie powinny byc puste
        assertNotNull(game1.getId());
        assertNotNull(game2.getId());
    } 

    @Test
    public void testfindData(){
    	Game_attribute game3=procedures.findById(1);
        assertNotNull(game3);
        assertEquals(6, game3.getnmb_players());  
    }
}
