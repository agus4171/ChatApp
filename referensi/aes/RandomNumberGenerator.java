/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author user
 */
public class RandomNumberGenerator {
    public static byte[] Random(int x) {
        byte[] b = new byte[16];
        new Random().nextBytes(b);
        return b;   
    }
    
    public static String StringToHexString(String string) {
        return String.format("%x", new BigInteger(1, string.getBytes()));
    }
    
    public static byte[] HexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        int i;
        for(i=0; i<len; i+=2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
    
    public static String ByteArrayToHexString(byte[] hexArray) {
        String hexString = new String();
        for(byte hex:hexArray) {
            String temp = Integer.toString(hex & 0xFF, 16);
            if(temp.length() == 1)
                temp = "0" + temp;
            hexString += temp;
        }
        return hexString;
    }
}
