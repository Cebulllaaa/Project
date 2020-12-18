package Project.CommandMaster.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import Project.common.game.Game;
import Project.common.game.TrylmaGame;
import Project.server.main.CommandMaster;
import Project.server.main.SendActivities;



public class CommandMastertest {
	public Game game;
	
	@Test
	public void ConstructorTest() {
		game = new TrylmaGame();
		SendActivities activity = SendActivities.SEND_ID;
		CommandMaster command_ms = new CommandMaster(1,activity);
		assertEquals(true,command_ms.before_start);
		assertEquals(game.getClass(),command_ms.game.getClass());
		assertTrue(command_ms.getCommand().equals(""));
		assertEquals(command_ms.activiti,activity);
	}
	@Test
	public void setgetTest() {
		game = new TrylmaGame();
		SendActivities activity = SendActivities.SEND_ID;
		CommandMaster command_ms = new CommandMaster(1,activity);
		String test = "TEST";
		String test2 = "New test";
		
		command_ms.setCommand(test);
		assertEquals(test,command_ms.getCommand());
		
		command_ms.setCommand(test2);
		assertEquals(test2,command_ms.getCommand());
	}
	
	@Test
	public void setIDcommandTest() {
		SendActivities activity = SendActivities.SEND_ID;
		CommandMaster command_ms = new CommandMaster(1, activity);
		command_ms.CommandMenu();
		
		assertTrue(command_ms.getCommand().equals("1;1;"));
		assertEquals(1,command_ms.game.Players.size());
		
		command_ms.CommandMenu();
		assertEquals(2,command_ms.game.Players.size());
		assertTrue(command_ms.getCommand().equals("1;2;"));
		
		command_ms.CommandMenu();
		assertEquals(3,command_ms.game.Players.size());
		assertTrue(command_ms.getCommand().equals("1;3;"));
		
		command_ms.CommandMenu();
		assertEquals(4,command_ms.game.Players.size());
		assertTrue(command_ms.getCommand().equals("1;4;"));
		
		command_ms.CommandMenu();
		assertEquals(5,command_ms.game.Players.size());
		assertTrue(command_ms.getCommand().equals("1;5;"));
		
		command_ms.CommandMenu();
		assertEquals(6,command_ms.game.Players.size());
		assertTrue(command_ms.getCommand().equals("1;6;"));
		
		command_ms.CommandMenu();
		assertEquals(6,command_ms.game.Players.size());
		assertTrue(command_ms.getCommand().equals("1;-1;")); 
	}
	
	@Test
	public void sendGameInfotest() {
		SendActivities activity = SendActivities.SEND_GAME_INFORMATION;
		CommandMaster command_ms = new CommandMaster(1, activity);
		command_ms.CommandMenu();
		
		String cmd1 = "2;0;";
		String cmd2 = "2;1;";
		String cmd3 = "2;2;";
		String cmd4 = "2;3;";
		String cmd5 = "2;4;";
		String cmd6 = "2;5;";
		String cmd7 = "2;6;";
	
		command_ms.CommandMenu();
		assertEquals(cmd1,command_ms.getCommand());
		
		command_ms.game.add_Player();
		command_ms.CommandMenu();
		assertEquals(cmd2,command_ms.getCommand());
		
		command_ms.game.add_Player();
		command_ms.CommandMenu();
		assertEquals(cmd3,command_ms.getCommand());
		
		command_ms.game.add_Player();
		command_ms.CommandMenu();
		assertEquals(cmd4,command_ms.getCommand());
		
		command_ms.game.add_Player();
		command_ms.CommandMenu();
		assertEquals(cmd5,command_ms.getCommand());
		
		command_ms.game.add_Player();
		command_ms.CommandMenu();
		assertEquals(cmd6,command_ms.getCommand());
		
		command_ms.game.add_Player();
		command_ms.CommandMenu();
		assertEquals(cmd7,command_ms.getCommand());
		
		command_ms.game.add_Player();
		command_ms.CommandMenu();
		assertEquals(cmd7,command_ms.getCommand());
	}

}
