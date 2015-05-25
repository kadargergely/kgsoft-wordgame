package hu.unideb.kgsoft.scrabble.utils;

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

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringJoiner;

public class Utils {
    private static MessageDigest md;

    public static String processPassword(String passwd) {
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = passwd.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO log nincs ilyen algoritmus
            e.printStackTrace();
        }
        return null;
    }
    
    public static Integer[] toIntegerArray(int[] primitiveArray) {
        Integer[] newArray = new Integer[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            newArray[i] = Integer.valueOf(primitiveArray[i]);
        }
        return newArray;
    }
    
    public static int[] toIntArray(Integer[] integerArray) {
        int[] newArray = new int[integerArray.length];
        for (int i = 0; i < integerArray.length; i++) {
            newArray[i] = integerArray[i];
        }
        return newArray;
    }
    
    public static int[] toIntArray(BigDecimal[] bigDecimalArray) {
        int[] newArray = new int[bigDecimalArray.length];
        for (int i = 0; i < bigDecimalArray.length; i++) {
            newArray[i] = bigDecimalArray[i].intValue();
        }
        return newArray;
    }
    
    public static String stringArrayToString(String[] strArray) {
        StringJoiner sj = new StringJoiner("', '", "'", "'");
        for (String str : strArray) {
            sj.add(str);
        }
        return sj.toString();
    }
}
