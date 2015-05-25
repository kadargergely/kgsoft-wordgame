package hu.unideb.kgsoft.scrabble;

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
import java.util.List;

/**
 * Note: this class has a natural ordering that is inconsistent with equals.
 */
public class DictWord implements Comparable<DictWord> {

    private final String word;
    private final byte[] auxData;

    public static DictWord newWord(String word) {

        boolean legal = true;
        for (int i = 0; i < word.length(); i++) {
            if (Letters.getC(String.valueOf(word.charAt(i))) == -1) {
                if (!(word.charAt(i) == 'y' && i > 0 && (word.charAt(i - 1) == 'g'
                        || word.charAt(i - 1) == 'l'
                        || word.charAt(i - 1) == 'n' || word.charAt(i - 1) == 't'))) {
                    legal = false;
                }
            }
        }

        if (legal) {
            return new DictWord(word);
        } else {
            return null;
        }
    }

    private DictWord(String word) {

        this.word = word;
        auxData = new byte[Letters.getNum()];

        for (int i = 0; i < word.length(); i++) {
            switch (word.charAt(i)) {
            case 'c':
            case 'z':
                if (i < word.length() - 1 && word.charAt(i + 1) == 's') {
                    auxData[Letters.getC(String.valueOf(word.charAt(i))
                            + String.valueOf(word.charAt(i + 1)))] += 1;
                    i++;
                } else {
                    auxData[Letters.getC(String.valueOf(word.charAt(i)))] += 1;
                }
                break;
            case 'g':
            case 'l':
            case 'n':
            case 't':
                if (i < word.length() - 1 && word.charAt(i + 1) == 'y') {
                    auxData[Letters.getC(String.valueOf(word.charAt(i))
                            + String.valueOf(word.charAt(i + 1)))] += 1;
                    i++;
                } else {
                    auxData[Letters.getC(String.valueOf(word.charAt(i)))] += 1;
                }
                break;
            case 's':
                if (i < word.length() - 1 && word.charAt(i + 1) == 'z') {
                    auxData[Letters.getC(String.valueOf(word.charAt(i))
                            + String.valueOf(word.charAt(i + 1)))] += 1;
                    i++;
                } else {
                    auxData[Letters.getC(String.valueOf(word.charAt(i)))] += 1;
                }
                break;
            default:
                int index = Letters.getC(String.valueOf(word.charAt(i)));
                if (index != -1) {
                    auxData[index] += 1;
                }
                break;
            }
        }
    }

    public List<String> toStringList() {
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < word.length(); i++) {
            switch (word.charAt(i)) {
            case 'c':
            case 'z':
                if (i < word.length() - 1 && word.charAt(i + 1) == 's') {
                    list.add(String.valueOf(word.charAt(i))
                            + String.valueOf(word.charAt(i + 1)));
                    i++;
                } else {
                    list.add(String.valueOf(word.charAt(i)));
                }
                break;
            case 'g':
            case 'l':
            case 'n':
            case 't':
                if (i < word.length() - 1 && word.charAt(i + 1) == 'y') {
                    list.add(String.valueOf(word.charAt(i))
                            + String.valueOf(word.charAt(i + 1)));
                    i++;
                } else {
                    list.add(String.valueOf(word.charAt(i)));
                }
                break;
            case 's':
                if (i < word.length() - 1 && word.charAt(i + 1) == 'z') {
                    list.add(String.valueOf(word.charAt(i))
                            + String.valueOf(word.charAt(i + 1)));
                    i++;
                } else {
                    list.add(String.valueOf(word.charAt(i)));
                }
                break;
            default:
                list.add(String.valueOf(word.charAt(i)));
                break;
            }
        }

        return list;
    }

    public boolean equals(DictWord other) {
        if (this.word.equals(other.word)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(String word) {
        if (this.word.equals(word)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean playable(DictWord letters) {
        boolean retVal = true;
        int jokers = letters.getJokers();

        for (int i = 0; i < letters.auxData.length; i++) {
            if (this.auxData[i] > letters.auxData[i]) {
                if (this.auxData[i] - letters.auxData[i] <= jokers) {
                    jokers -= this.auxData[i] - letters.auxData[i];
                } else {
                    retVal = false;
                    break;
                }
            }
        }
        return retVal;
    }

    public int getJokers() {
        return auxData[38];
    }

    public byte[] getAuxData() {
        return auxData;
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public int compareTo(DictWord o) {
        if (this.word.length() <= o.word.length()) {
            if (this.word.length() < o.word.length())
                return -1;
            else
                return 0;
        } else {
            return 1;
        }
    }
}
