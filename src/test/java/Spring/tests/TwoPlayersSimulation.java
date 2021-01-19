package Spring.tests;

import Project.Database.SpringBoot_starter;
import Project.Database.Procedures.GT_procedures;
import Project.Database.Procedures.MT_procedures;
import Project.Database.Tables.Game_attribute;
import Project.Database.Tables.Move_attribute;
import Project.server.main.Server;
import Project.server.main.ServerActivities;
import Project.server.main.StartServerConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ConstantConnectionTests.ConstantConnectionClient;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = SpringBoot_starter.class)
public class TwoPlayersSimulation {
	@Autowired
    private GT_procedures G_procedures;
	@Autowired
    private MT_procedures M_procedures;
	
	int port;
	Server server;
	StartServerConnection stc;
	ConstantConnectionClient client1;
	ConstantConnectionClient client2;
	int game_id;
	String prev_move;
	String next_move;
	
	Socket client1socket;
	Socket client2socket;
	
	@Test
	public void gametest() {
		try {
			port = 6000;
			server = new Server(port,2);
			server.create_Server();
			stc = new StartServerConnection(server);
			stc.start();
			
			client1socket = new Socket("127.0.0.1",port);
			client1 = new ConstantConnectionClient(port,client1socket);
			client1.start();
			stc.join(100);
			assertEquals("1;1;",client1.command);
			
			client2socket = new Socket("127.0.0.1",port);
			client2 = new ConstantConnectionClient(port,client2socket);
			client2.start();
			stc.join(100);
			assertEquals("1;4;",client2.command);
			
			server.command_ms.activiti =ServerActivities.SEND_GAME_INFORMATION;
			stc.join(100);
			
			assertEquals("2;2;1;",client1.command);
			assertEquals("2;2;1;",client2.command);
			
			server.command_ms.activiti =ServerActivities.SEND_START_GAME;
			stc.join(100);
			/*Czesc dla springa zapisywanie gry */
			Game_attribute game = new Game_attribute(2);
			this.G_procedures.save(game);
			game_id = game.getId();
			System.out.println(game_id);
			
			assertEquals("3;",client1.command);
			assertEquals("3;",client2.command);
			
			server.command_ms.game.create_Board();
			server.command_ms.game.create_Queue();
			for(int i =0 ; i<10; i++) {
				server.command_ms.activiti =ServerActivities.SEND_BOARD;
				stc.join(100);
				server.command_ms.activiti =ServerActivities.SEND_WHOSE_TURN;
				stc.join(100);
				/*Czesc dla Springa zapisywanie ruchu*/
				prev_move =server.command_ms.game.getMove();
				next_move =server.command_ms.game.getMove();
				Move_attribute move = new Move_attribute(game_id, prev_move,next_move);
				this.M_procedures.save(move);
				server.command_ms.game.increase_Queue();
				
			}
			server.command_ms.activiti =ServerActivities.SEND_BOARD;
			stc.join(100);
			
			server.command_ms.game.set_winner(1);
			server.command_ms.activiti =ServerActivities.SEND_END_GAME;
			stc.join(100);
			//System.out.println("odczekalem");
			
			assertEquals("6;1;1;",client1.command);
			assertEquals("6;1;1;",client2.command);
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
