package hu.unideb.kgsoft.scrabble.model;

/*
 * #%L
 * kgsoft-scrabble
 * %%
 * Copyright (C) 2015 kgsoft
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static hu.unideb.kgsoft.scrabble.Main.logger;

/**
 * The <code>Computer</code> class represents the computer as a player. The
 * <code>Computer</code> object stores its game score and its letters on the
 * tray. The class includes methods that based on the tray, the state of the
 * game board and the dictionary containing the correct words, can place a word
 * on the board, thus making the computer able to play against a human opponent.
 * 
 * @author gergo
 *
 */
public class Computer {

    /**
     * Represents a sequence of letters that can be played in a turn. An
     * instance of this class stores the letters to play, specifies where to
     * play them and how many points can be gained in the turn.
     * 
     * @author kadar_000
     *
     */
    private class WordToPlay {
        /**
         * The letters of the word.
         */
        public final List<String> letters;
        /**
         * The number of points the word gives.
         */
        public final int value;
        /**
         * The row index of the first letter of the sequence.
         */
        public final int startRow;
        /**
         * The column index of the first letter of the sequence.
         */
        public final int startCol;
        /**
         * The orientation of the word. 0 means horizontal and 1 is vertical.
         */
        public final int orientation;

        /**
         * Constructs a new {@code WordToPlay} object, initializing it with the
         * given values.
         * 
         * @param letters
         *            the letters of the word
         * @param value
         *            the number of points the word gives
         * @param startRow
         *            the row index of the first letter of the sequence
         * @param startCol
         *            the column index of the first letter of the sequence
         * @param orientation
         *            the orientation of the word
         */
        public WordToPlay(List<String> letters, int value, int startRow,
                int startCol, int orientation) {
            super();
            this.letters = letters;
            this.value = value;
            this.startRow = startRow;
            this.startCol = startCol;
            this.orientation = orientation;
        }
    }

    /**
     * The number of points the computer currently has.
     */
    private int score;
    /**
     * The {@code Tray} object representing the tray of the computer.
     */
    private Tray tray;

    /**
     * Constructs a new <code>Computer</code> object. Initializes it with an
     * empty tray and no points.
     */
    public Computer() {
        tray = new Tray();
        score = 0;
    }

    /**
     * Places a sequence of letters on the board given, forming a word, if it is
     * possible. The starting position of the word is given as row and column
     * indices, and its orientation. The orientation is 0 for horizontal and 1
     * for vertical. The letters of the word are given as a list of strings.
     * Placing the word on the board can fail because of the position of the
     * letters that were already on the board, or because the new word doesn't
     * connect to the old ones on the board. If the word could be placed on the
     * board, {@code true} is returned. This method is called by the public
     * {@code play} method.
     * 
     * @param word
     *            the letters of the word that needs to be placed on the board
     * @param gboard
     *            the board on which the new word should be placed
     * @param row
     *            the row index of the starting position of the word
     * @param col
     *            the column index of the starting position of the word
     * @param orientation
     *            the orientation of the word
     * @return {@code true} on success, {@code false} if the word couldn't be
     *         placed on the board
     */
    private boolean placeWord(List<String> word, Gameboard gboard, int row,
            int col, int orientation) {
        List<Integer> jokerTileIndices = new ArrayList<Integer>();

        boolean placeable = true;
        boolean connected = false;

        byte[] lettersFromTray = DictWord.newWord(
                tray.getLettersAsString().toLowerCase()).getNumOfLetters();

        int i = row;
        int j = col;
        for (int k = 0; k < word.size(); k++) {
            int actLetterCode = Letters.getC(word.get(k));

            switch (gboard.getFields()[i][j].getStatus()) {
            case EMPTY:
                if (lettersFromTray[actLetterCode] == 0) {
                    if (lettersFromTray[Letters.JOKER_CODE] == 0) {
                        placeable = false;
                    } else {
                        jokerTileIndices.add(k);
                        lettersFromTray[Letters.JOKER_CODE] -= 1;
                    }
                } else {
                    lettersFromTray[actLetterCode] -= 1;
                }
                break;
            case FIXED:
                if (gboard.getFields()[i][j].getTileCode() != actLetterCode) {
                    placeable = false;
                }
                connected = true;
                break;
            default:
                break;
            }

            if (!placeable) {
                break;
            }

            if (!connected) {
                if (orientation == 0) {
                    if ((row > 0 && gboard.getFields()[row - 1][j].getStatus() == Field.Status.FIXED)
                            || (row < Gameboard.BOARD_SIZE - 1 && gboard
                                    .getFields()[row + 1][j].getStatus() == Field.Status.FIXED)) {
                        connected = true;
                    }
                } else {
                    if ((col > 0 && gboard.getFields()[i][col - 1].getStatus() == Field.Status.FIXED)
                            || (col < Gameboard.BOARD_SIZE - 1 && gboard
                                    .getFields()[i][col + 1].getStatus() == Field.Status.FIXED)) {
                        connected = true;
                    }
                }
            }

            if (orientation == 0) {
                j++;
            } else {
                i++;
            }
        }

        if (!placeable || !connected) {
            return false;
        }

        i = row;
        j = col;
        for (int k = 0; k < word.size(); k++) {
            int actLetterCode = Letters.getC(word.get(k));

            if (gboard.getFields()[i][j].getStatus() == Field.Status.EMPTY) {
                if (jokerTileIndices.contains(k)) {
                    gboard.setJokerTile(i, j, actLetterCode);
                    tray.removeTile(Letters.JOKER_CODE);
                } else {
                    gboard.setTile(i, j, actLetterCode);
                    tray.removeTile(actLetterCode);
                }
            }
            if (orientation == 0) {
                j++;
            } else {
                i++;
            }
        }

        return true;
    }

    /**
     * Picks up all movable tiles from a row or column. The index of the word or
     * column is given along with the orientation. The orientation is 0 for
     * horizontal and 1 for vertical. The picked up tiles are put back on the
     * tray.
     * 
     * @param index
     *            the index of the row or column from which the movable tiles
     *            should be picked up
     * @param gboard
     *            the game board from which the tiles will be picked up
     * @param orientation
     *            the orientation which is 0 for horizontal and 1 for vertical
     *            removing
     */
    private void pickUpWord(int index, Gameboard gboard, int orientation) {
        for (int i = 0; i < Gameboard.BOARD_SIZE; i++) {
            // Row or column...
            int row, col;
            if (orientation == 0) {
                row = index;
                col = i;
            } else {
                row = i;
                col = index;
            }
            // Pick up tile.
            int tileCode = gboard.pickUpTile(row, col);
            if (tileCode != Field.NOTILE) {
                tray.addTile(tileCode);
            }
        }
    }

    /**
     * Places new tiles on the board from the tray, forming words from the
     * dictionary. The computer will get the maximal number of points obtainable
     * in the current turn. The new tiles are directly written to the
     * <code>Gameboard</code> object provided as parameter. Only forms words
     * contained by the dictionary provided, represented by a
     * <code>Dictionary</code> object, and places tiles correctly on the board.
     * 
     * @param gboard
     *            the board on which the new tiles are placed
     * @param dict
     *            the dictionary which contains the words that can be played
     */
    public void play(Gameboard gboard, Dictionary dict) {

        List<WordToPlay> goodWords = new ArrayList<Computer.WordToPlay>();
        WordToPlay bestWord = null;

        // Search possible words for all rows and columns.
        // Checking the rows...
        for (int i = 0; i < Gameboard.BOARD_SIZE; i++) {
            // Only working with lines where tiles can be placed.
            boolean lineActive = false;
            for (int j = 0; j < Gameboard.BOARD_SIZE; j++) {
                if ((i > 0 && gboard.getFields()[i - 1][j].getStatus() == Field.Status.FIXED)
                        || (i < Gameboard.BOARD_SIZE - 1 && gboard.getFields()[i + 1][j]
                                .getStatus() == Field.Status.FIXED)
                        || (gboard.getFields()[i][j].getStatus() == Field.Status.FIXED)) {
                    lineActive = true;
                    break;
                }
            }

            if (lineActive) {
                // Parsing the letters from the board and the tray.
                StringBuffer sb = new StringBuffer();

                for (int j = 0; j < Gameboard.BOARD_SIZE; j++) {
                    String letter = Letters.getL(gboard.getFields()[i][j]
                            .getTileCode());
                    if (letter != null) {
                        sb.append(letter);
                    }
                }
                sb.append(tray.getLettersAsString());
                DictWord allLetters = DictWord.newWord(sb.toString()
                        .toLowerCase());
                List<List<String>> words = dict.getPlayableWords(allLetters);

                // All words must be checked.
                for (List<String> word : words) {
                    // Trying to fit the word to all possible positions.
                    for (int j = 0; j < Gameboard.BOARD_SIZE - word.size(); j++) {
                        if (placeWord(word, gboard, i, j, 0)) {
                            if (gboard.wordsAreCorrect(dict)) {
                                goodWords.add(new WordToPlay(word, gboard
                                        .calcPoints(), i, j, 0));
                            }
                            pickUpWord(i, gboard, 0);
                        }
                    }
                }
            }
        }
        // Checking the columns...
        for (int j = 0; j < Gameboard.BOARD_SIZE; j++) {

            boolean colActive = false;
            for (int i = 0; i < Gameboard.BOARD_SIZE; i++) {
                if ((j > 0 && gboard.getFields()[i][j - 1].getStatus() == Field.Status.FIXED)
                        || (j < Gameboard.BOARD_SIZE - 1 && gboard.getFields()[i][j + 1]
                                .getStatus() == Field.Status.FIXED)
                        || (gboard.getFields()[i][j].getStatus() == Field.Status.FIXED)) {
                    colActive = true;
                    break;
                }
            }

            if (colActive) {
                // Parsing letters...
                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < Gameboard.BOARD_SIZE; i++) {
                    String letter = Letters.getL(gboard.getFields()[i][j]
                            .getTileCode());
                    if (letter != null) {
                        sb.append(letter);
                    }
                }
                sb.append(tray.getLettersAsString());
                DictWord allLetters = DictWord.newWord(sb.toString()
                        .toLowerCase());
                List<List<String>> words = dict.getPlayableWords(allLetters);

                // Checking words...
                for (List<String> word : words) {
                    // Trying to fit the word to all possible positions.
                    for (int i = 0; i < Gameboard.BOARD_SIZE - word.size(); i++) {
                        if (placeWord(word, gboard, i, j, 1)) {
                            if (gboard.wordsAreCorrect(dict)) {
                                goodWords.add(new WordToPlay(word, gboard
                                        .calcPoints(), i, j, 1));
                            }
                            pickUpWord(j, gboard, 1);
                        }
                    }
                }
            }
        }

        // Choosing the best word and placing it on the board.
        if (goodWords.size() > 0) {
            bestWord = Collections.max(goodWords, new Comparator<WordToPlay>() {
                @Override
                public int compare(WordToPlay o1, WordToPlay o2) {
                    return Integer.compare(o1.value, o2.value);
                }
            });
            if (placeWord(bestWord.letters, gboard, bestWord.startRow,
                    bestWord.startCol, bestWord.orientation)) {
                logger.info(String
                        .format("Computer played letters %s starting from (%d, %d), %s",
                                bestWord.letters.toString(), bestWord.startRow,
                                bestWord.startCol,
                                bestWord.orientation == 0 ? "horizontally"
                                        : "vertically"));
            } else {
                logger.warn("Computer didn't play any words.");
            }
        } else {
            logger.info("Computer can't play any words.");
        }
    }

    /**
     * Returns the current score of the computer.
     * 
     * @return the score of the computer
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the new score of the computer.
     * 
     * @param score
     *            the new score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns a reference to the <code>Tray</code> object representing the tray
     * of the computer.
     * 
     * @return the tray of the computer
     */
    public Tray getTray() {
        return tray;
    }

}
