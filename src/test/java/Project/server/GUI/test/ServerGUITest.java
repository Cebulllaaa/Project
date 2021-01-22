package Project.server.GUI.test;

import javax.swing.JFrame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import Project.Database.SpringBoot_starter;
import Project.Database.Procedures.GT_procedures;
import Project.Database.Procedures.MT_procedures;
import Project.Database.Tables.Game_attribute;
import Project.common.board.AbstractBoard;
import Project.common.board.StandartBoard;
import Project.common.exceptions.ApplicationErrorException;
import Project.server.GUI.GameHistoryFrame;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SpringBoot_starter.class)
public class ServerGUITest {

//	@Autowired
//	private GT_procedures gtp;
//	@Autowired
//	private MT_procedures mtp;

	@Test
	public void WindowTest() throws ApplicationErrorException {
		//Game_attribute ga = gtp.findById(1);
		AbstractBoard ab = new StandartBoard();
		ab.initPieces(2);
		GameHistoryFrame ghf = new GameHistoryFrame(ab); //, null);
		ghf.setSize(800, 800);
		ghf.setLocationRelativeTo(null);
		ghf.setTitle("Game History");
		ghf.setVisible(true);
		try {
			Thread.sleep(10000);
		}
		catch (InterruptedException ix) {
			System.out.println(ix);
		}
	}

}
