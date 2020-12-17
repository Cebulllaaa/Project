package Project.Game.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Test;
import java.util.Set;
import Project.Game.TrylmaGame;


public class TrylmaGametest {
	TrylmaGame game;
	
	@Test
	public void testConstructor() {
		game = new TrylmaGame();
		assertEquals(6,game.max_Players);
		assertEquals(2,game.min_Players);	
		ArrayList<Integer> test_possibilities = new ArrayList<Integer>();
		test_possibilities.add(2);
		test_possibilities.add(3);
		test_possibilities.add(4);
		test_possibilities.add(6);
		assertEquals(game.possibilities,test_possibilities);
		assertEquals(0,game.in_Queue);
	}
	@Test
	public void decide_Turn_test() {
		game = new TrylmaGame();
		game.Queue = new ArrayList<Integer>();
		game.Queue.add(4);
		game.Queue.add(2);
		game.Queue.add(5);
		game.Queue.add(1);
		game.Queue.add(6);
		game.Queue.add(3);
		
		int next_turn_test = game.decide_Turn();
		assertEquals(next_turn_test,4);
		assertEquals(game.in_Queue,1);
		
		next_turn_test = game.decide_Turn();
		assertEquals(next_turn_test,2);
		assertEquals(game.in_Queue,2);
		
		next_turn_test = game.decide_Turn();
		assertEquals(next_turn_test,5);
		assertEquals(game.in_Queue,3);
		
		next_turn_test = game.decide_Turn();
		assertEquals(next_turn_test,1);
		assertEquals(game.in_Queue,4);
		
		next_turn_test = game.decide_Turn();
		assertEquals(next_turn_test,6);
		assertEquals(game.in_Queue,5);
		
		next_turn_test = game.decide_Turn();
		assertEquals(next_turn_test,3);
		assertEquals(game.in_Queue,0);
		
		next_turn_test = game.decide_Turn();
		assertEquals(next_turn_test,4);
		assertEquals(game.in_Queue,1);
	}
	
	@Test
	public void create_Queue_test() {
		game = new TrylmaGame();
		game.Players.add(1);
		game.Players.add(2);
		game.Players.add(3);
		game.Players.add(4);
		game.Players.add(5);
		game.Players.add(6);
		
		game.create_Queue();
		
		assertEquals(game.Players.size(),game.Queue.size());
		assertTrue(game.Queue.contains(1));
		assertTrue(game.Queue.contains(2));
		assertTrue(game.Queue.contains(3));
		assertTrue(game.Queue.contains(4));
		assertTrue(game.Queue.contains(5));
		assertTrue(game.Queue.contains(6));
		
		/*System.out.println(game.Queue.get(0));
		System.out.println(game.Queue.get(1));
		System.out.println(game.Queue.get(2));
		System.out.println(game.Queue.get(3));
		System.out.println(game.Queue.get(4));
		System.out.println(game.Queue.get(5)); */
	}
	@Test
	public void add_Player_test() {
		boolean success;
		game = new TrylmaGame();
		int x;
		success = game.add_Player();
		assertEquals(game.Players.size(),1);
		x=game.Players.get(0);
		assertEquals(x,1);
		assertTrue(success);
		
		success = game.add_Player();
		assertEquals(game.Players.size(),2);
		x=game.Players.get(1);
		assertEquals(x,2);
		assertTrue(success);
		
		success = game.add_Player();
		assertEquals(game.Players.size(),3);
		x=game.Players.get(2);
		assertEquals(x,3);
		assertTrue(success);
		
		game.add_Player();
		assertEquals(game.Players.size(),4);
		x=game.Players.get(3);
		assertEquals(x,4);
		
		success = game.add_Player();
		assertEquals(game.Players.size(),5);
		x=game.Players.get(4);
		assertEquals(x,5);
		assertTrue(success);
		
		success= game.add_Player();
		assertEquals(game.Players.size(),6);
		x=game.Players.get(5);
		assertEquals(x,6);
		assertTrue(success);
		
		success = game.add_Player();
		assertEquals(game.Players.size(),6);
		x=game.Players.get(5);
		assertEquals(x,6);
		assertFalse(success);
	}
	
}
