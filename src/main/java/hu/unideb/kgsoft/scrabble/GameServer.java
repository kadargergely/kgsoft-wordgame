package hu.unideb.kgsoft.scrabble;

public interface GameServer {
	
	public void registerPlayer(Player player);
	
	public void setTileOnBoard(int row, int col, int tileCode);
}
