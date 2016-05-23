package hu.unideb.kgsoft.scrabble;

import static org.junit.Assert.*;

import org.junit.Test;

import hu.unideb.kgsoft.scrabble.model.Tray;

public class TrayTest {
	
	@Test
	public void testGetLettersAsString() {
		Tray tray = new Tray();
		for (int i = 0; i < Tray.TRAY_SIZE; i++) {
			tray.setTile(i, i);
		}
		assertEquals("AÁBCCSDE", tray.getLettersAsString());
	}
	
	@Test
	public void testSetTile() {
		Tray tray = new Tray();
		assertTrue(tray.setTile(0, 0));
		assertEquals(0, tray.getTileCodeAt(0));
		assertFalse(tray.setTile(1, 0));
		assertEquals(0, tray.getTileCodeAt(0));
	}
	
	@Test
	public void testSetLetters() {
		Tray tray = new Tray();
		int[] letters = new int[Tray.TRAY_SIZE];
		for (int i = 0; i < letters.length; i++) {
			letters[i] = i;
		}
		tray.setLetters(letters);
		for (int i = 0; i < Tray.TRAY_SIZE; i++) {
			assertEquals(letters[i], tray.getTileCodeAt(i));
		}
	}
	
	@Test
	public void testRemoveTile() {
		Tray tray = new Tray();
		tray.setTile(1, 0);
		tray.setTile(0, 1);
		tray.setTile(0, 2);
		assertTrue(tray.removeTile(0));
		assertEquals(-1, tray.getTileCodeAt(1));
		assertEquals(0, tray.getTileCodeAt(2));
		assertFalse(tray.removeTile(12));
	}
}
