package hu.unideb.kgsoft.scrabble;

import com.esotericsoftware.kryonet.Server;

import hu.unideb.kgsoft.scrabble.networking.Network;

public class MultiplayerServer implements GameServer {
	
	private Server server;
	
	public MultiplayerServer() {
		server = new Server();
		
		Network.register(server);
	}
	
	@Override
	public void clickedOnGameBoard(int row, int col, int tileInHand) {
		// TODO Auto-generated method stub

	}

}
