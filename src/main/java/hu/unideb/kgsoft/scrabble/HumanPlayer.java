package hu.unideb.kgsoft.scrabble;

import hu.unideb.kgsoft.scrabble.view.GameWindowController;

public class HumanPlayer implements Player {
	
	private String name;
	private Tray tray;
	private boolean playersTurn;
	private GameWindowController view;
	private GameServer server;

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
	public void tilePlacedOnBoard(GameState gameState) {
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
