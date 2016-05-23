package hu.unideb.kgsoft.scrabble;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;

import hu.unideb.kgsoft.scrabble.model.Computer;
import hu.unideb.kgsoft.scrabble.model.Dictionary;
import hu.unideb.kgsoft.scrabble.model.Gameboard;

public class ComputerTest {

	@Test
	public void testPlay() throws FileNotFoundException,
			UnsupportedEncodingException {
		Gameboard board = new Gameboard();
		Dictionary dict = new Dictionary(new InputStreamReader(this.getClass()
				.getResourceAsStream("/hu_HU.dic"), "UTF-8"));
		Computer computer = new Computer();
		
		// ALMA
		board.setTile(7, 7, 0);
		board.setTile(7, 8, 16);
		board.setTile(7, 9, 18);
		board.setTile(7, 10, 0);
		board.finalizeFields();
		
		computer.getTray().addTile(0);
		computer.getTray().addTile(16);
		computer.getTray().addTile(18);
		computer.getTray().addTile(0);
		computer.getTray().addTile(12);
		computer.getTray().addTile(21);
		computer.getTray().addTile(9);
		
		computer.play(board, dict);
		List<String> playedWords = board.getPlayedWords();
		assertNotNull(playedWords);
	}
}
