package Project.common.game;

import java.util.ArrayList;

public class TrylmaWaitingRoom extends WaitingRoom{
	public TrylmaWaitingRoom() {
		
	}

	@Override
	public void createWaitingRoom(int x) {
		free_slots = new ArrayList<Integer>();
		if(x==2) {
			addtoWaitingRoom(1);
			addtoWaitingRoom(4);
		}
		else if(x==3) {
			addtoWaitingRoom(1);
			addtoWaitingRoom(3);
			addtoWaitingRoom(5);
		}
		else if(x==4){
			addtoWaitingRoom(2);
			addtoWaitingRoom(3);
			addtoWaitingRoom(5);
			addtoWaitingRoom(6);
		}
		else if(x==6) {
			addtoWaitingRoom(1);
			addtoWaitingRoom(2);
			addtoWaitingRoom(3);
			addtoWaitingRoom(4);
			addtoWaitingRoom(5);
			addtoWaitingRoom(6);
		}
		
	}

	@Override
	public void deletefromWaitingRoom(int x) {
		free_slots.remove(x);
		
	}

	@Override
	public void addtoWaitingRoom(int x) {
		free_slots.add(x);
		
	}

}
