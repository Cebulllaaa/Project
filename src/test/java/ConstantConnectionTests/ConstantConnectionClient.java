package ConstantConnectionTests;

import java.net.Socket;

public class ConstantConnectionClient extends Thread{
	int port;
	Socket socket;
	public String command;
	public ConstantConnectionClient(int x) {
		this.port =x;
	}
}
