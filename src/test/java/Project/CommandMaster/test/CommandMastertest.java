package Project.CommandMaster.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import Project.Game.Game;
import Project.Game.TrylmaGame;
import Project.server.main.CommandMaster;



public class CommandMastertest {
	public Game game;
	
	@Test
	public void ConstructorTest() {
		game = new TrylmaGame();
		CommandMaster command_ms = new CommandMaster(1);
		assertEquals(true,command_ms.before_start);
		assertEquals(game.getClass(),command_ms.game.getClass());
		assertTrue(command_ms.getCommand().equals(""));
	}
	@Test
	public void setgetTest() {
		game = new TrylmaGame();
		CommandMaster command_ms = new CommandMaster(1);
		String test = "TEST";
		String test2 = "New test";
		
		command_ms.setCommand(test);
		assertEquals(test,command_ms.getCommand());
		
		command_ms.setCommand(test2);
		assertEquals(test2,command_ms.getCommand());
	}
	
	@Test
	public void setIDcommandTest() {
		CommandMaster command_ms = new CommandMaster(1);
		command_ms.CommandMenu();
		
		assertTrue(command_ms.getCommand().equals("1;TODO"));
		assertEquals(1,command_ms.game.Players.size());
		
		command_ms.CommandMenu();
		assertEquals(2,command_ms.game.Players.size());
		System.out.println(command_ms.getCommand());
		assertTrue(command_ms.getCommand().equals("2;TODO"));
		
		command_ms.CommandMenu();
		assertEquals(3,command_ms.game.Players.size());
		System.out.println(command_ms.getCommand());
		assertTrue(command_ms.getCommand().equals("3;TODO"));
		
		command_ms.CommandMenu();
		assertEquals(4,command_ms.game.Players.size());
		System.out.println(command_ms.getCommand());
		assertTrue(command_ms.getCommand().equals("4;TODO"));
		
		command_ms.CommandMenu();
		assertEquals(5,command_ms.game.Players.size());
		System.out.println(command_ms.getCommand());
		assertTrue(command_ms.getCommand().equals("5;TODO"));
		
		command_ms.CommandMenu();
		assertEquals(6,command_ms.game.Players.size());
		System.out.println(command_ms.getCommand());
		assertTrue(command_ms.getCommand().equals("6;TODO"));
		
		command_ms.CommandMenu();
		assertEquals(6,command_ms.game.Players.size());
		System.out.println(command_ms.getCommand());
		assertTrue(command_ms.getCommand().equals("-1;TODO"));
	}

}
