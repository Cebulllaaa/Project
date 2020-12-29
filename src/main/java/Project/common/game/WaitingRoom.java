package Project.common.game;

import java.util.ArrayList;

public abstract class WaitingRoom {
	public int number_of_players;
	public ArrayList<Integer>free_slots;
	public abstract void createWaitingRoom(int x);
	public abstract void deletefromWaitingRoom(int x);
	public abstract void addtoWaitingRoom(int x);
}
