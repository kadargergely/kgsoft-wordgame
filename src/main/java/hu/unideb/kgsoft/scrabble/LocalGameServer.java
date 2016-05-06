package hu.unideb.kgsoft.scrabble;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import hu.unideb.kgsoft.scrabble.networking.Network;
import hu.unideb.kgsoft.scrabble.networking.Packets;

public class LocalGameServer implements GameServer {

	private class RemotePlayer implements Player {

		private String name;
		private Socket socket;

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setName() {
			// TODO Auto-generated method stub

		}

		public RemotePlayer(String name, Socket serverSocket) {
			this.name = name;
			this.socket = serverSocket;
		}

		@Override
		public Tray getTray() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void giveTurn() {
			// TODO Auto-generated method stub

		}

		@Override
		public void tilePlacedOnBoard() {
			// TODO Auto-generated method stub

		}

		@Override
		public void gameStarted(GameState gameState) {
			// TODO Auto-generated method stub

		}

		@Override
		public void gameEnded(GameState gameState) {
			// TODO Auto-generated method stub

		}

		@Override
		public void otherPlayerEndedTurn(GameState gameState) {
			// TODO Auto-generated method stub

		}

	}

	private class Socket {
		private Server server;
		
		private final String ERRMSG_INVALID_USERNAME = "Nem megfelelő felhasználónév.";
		
		public Socket() {
			server = new Server() {
				@Override
				protected Connection newConnection() {
					return new PlayerConnection();
				}
			};
			Network.register(server);
			server.addListener(new Listener() {
				@Override
				public void received(Connection connection, Object o) {
					PlayerConnection playerConnection = (PlayerConnection) connection;
					if (o instanceof Packets.RegisterPlayer) {
						if (playerConnection.playerName == null) {
							String name = ((Packets.RegisterPlayer) o).getName();
							if (name != null) {
								name = name.trim();
								if (name.length() > 0) {
									registerPlayer(new RemotePlayer(name, Socket.this));
									playerConnection.playerName = name;																		
								} else {
									sendErrorMessage(playerConnection, ERRMSG_INVALID_USERNAME);
								}
							} else {
								sendErrorMessage(playerConnection, ERRMSG_INVALID_USERNAME);
							}
						}
					}
				}
			});
		}
		
		private void sendErrorMessage(Connection connection, String message) {
			Packets.ErrorMessage errorMessage = new Packets.ErrorMessage(message);
			server.sendToTCP(connection.getID(), errorMessage);
		}
		
		private PlayerConnection getConnectionByPlayerName(String playerName) {
			Connection[] connections = server.getConnections();
			for (int i = 0; i < connections.length; i++) {
				if (((PlayerConnection) connections[i]).playerName.equals(playerName)) {
					return (PlayerConnection) connections[i];
				}
			}			
			return null;
		}
	}

	private class PlayerConnection extends Connection {
		public String playerName;
	}

	private boolean multiplayer;

	private Socket socket;
	private List<Player> players;
	private int currPlayerIndex;

	private Dictionary dict;
	
	private Gameboard gameBoard;
	private boolean gameBoardLegal;
	
	private Bag bag;

	private boolean firstTurn;
	private int numOfPasses;

	public LocalGameServer(boolean multiplayer) {
		this.multiplayer = multiplayer;
		if (multiplayer) {
			socket = new Socket();
		}
	}

	public void start() {

	}

	@Override
	public void registerPlayer(Player player) {
		players.add(player);	
	}
	
	@Override
	public void setTileOnBoard(int row, int col, int tileCode) {
		gameBoard.setTile(row, col, tileCode);
		gameBoardLegal = gameBoard.isLegal(firstTurn);
		players.forEach(player -> player.tilePlacedOnBoard());
	}
	
	public boolean isGameBoardLegal() {
		return gameBoardLegal;
	}
}
