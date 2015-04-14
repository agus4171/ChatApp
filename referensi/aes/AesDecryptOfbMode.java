/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

import java.io.UnsupportedEncodingException;
//import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author user
 */
public class AesDecryptOfbMode {
    static byte[] initKey = {
        (byte) 0x0F, (byte) 0x47, (byte) 0x0C, (byte) 0xAF,
        (byte) 0x15, (byte) 0xD9, (byte) 0xB7, (byte) 0x7F,
        (byte) 0x71, (byte) 0xE8, (byte) 0xAD, (byte) 0x67,
        (byte) 0xC9, (byte) 0x59, (byte) 0xD6, (byte) 0x98
    };
    
    static ArrayList<byte[]> keyList = new ArrayList<>();
    
    static byte[] nounce = {
        (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33,
        (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77,
        (byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB,
        (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF
    };
    
    static final char[] s_box = {
        0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
        0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
        0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
        0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
        0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
        0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
        0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
        0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
        0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
        0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
        0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
        0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
        0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
        0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
        0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
        0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
    };
    
    static final char[] rcon = {
        0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 
        0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 
        0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 
        0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 
        0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 
        0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 
        0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 
        0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 
        0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 
        0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 
        0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 
        0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 
        0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 
        0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 
        0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 
        0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
    };
    
    static byte[] KeyExpansion(byte[] key, int round) {
        byte[] temp = new byte[16];
        byte[] x = new byte[4];
        byte[] y = new byte[4];
        byte[] z = new byte[4];
        
        int i;
        int j = 0;
        for(i=3; i<16; i+=4) {
            x[j++] = key[(i + 4) % 16];
//            System.out.println(Integer.toString(x[j-1] & 0xFF, 16));
        }
        for(i=0; i<4; i++) {
            y[i] = (byte) s_box[x[i] & 0xFF];
//            System.out.println(Integer.toString(y[i] & 0xFF, 16));
        }
        for(i=0; i<4; i++) {
            if(i == 0)
                z[i] = (byte) (y[i] ^ (byte) rcon[round-1]);
            else
                z[i] = y[i];
//            System.out.println(Integer.toString(z[i] & 0xFF, 16));
        }
        j = 0;
        for(i=0; i<13; i+=4) {
            temp[i] = (byte) (key[i] ^ z[j++]);
//            System.out.println(Integer.toString(temp[i] & 0xFF, 16));
        }
        for(i=1; i<14; i+=4) {
            temp[i] = (byte) (temp[i-1] ^ key[i]);
//            System.out.println(Integer.toString(temp[i] & 0xFF, 16));
        }
        for(i=2; i<15; i+=4) {
            temp[i] = (byte) (temp[i-1] ^ key[i]);
//            System.out.print(Integer.toString(temp[i] & 0xFF, 16) + " ");
//            System.out.println(Integer.toString((byte)(key[i]) & 0xFF, 16));
        }
        for(i=3; i<16; i+=4) {
            temp[i] = (byte) (temp[i-1] ^ key[i]);
//            System.out.println(Integer.toString(temp[i] & 0xFF, 16));
        }
        return temp;
    }
    
    static byte[][] AddRoundKey(byte[][] state, byte[] expKey) {
        int i;
        int j;
        for(i=0; i<4; i++) {
            for(j=0; j<4; j++) {
                state[j][i] ^= expKey[i * 4 + j];
            }
        }
        return state;
    }
    
    static byte[][] Subtitution(byte[][] state) {
        int i;
        int j;
        for(i=0; i<4; i++) {
            for(j=0; j<4; j++) {
                state[i][j] = (byte) s_box[state[i][j] & 0xFF];
            }
        }
        return state;
    }
    
    static byte[][] ShiftRow(byte[][] state) {
        byte[] temp = new byte[4];
        int i;
        int j;
        for(i=1; i<4; i++) {
            for(j=0; j<4; j++) {
                temp[j] = state[i][(j + i) % 4];
            }
            for(j=0; j<4; j++) {
                state[i][j] = temp[j];
            }
        }
        return state;
    }
    
    static byte GFMultiplication(byte a, byte b) {
        byte result = 0;
        byte temp;
        while(a != 0) {
            if((a & 1) != 0) {
                result = (byte) (result ^ b);
            }
            temp = (byte) (b & 0x80);
            b = (byte) (b << 1);
            if(temp != 0) {
                b = (byte) (b ^ 0x1B);
            }
            a = (byte) ((a & 0xFF) >> 1);
        }
        return result;
    }
    
    static byte[][] MixColumn(byte[][] state) {
        int[] temp = new int[4];
        byte a = (byte) (0x03);
        byte b = (byte) (0x01);
        byte c = (byte) (0x01);
        byte d = (byte) (0x02);
        
        int i;
        int j;
        for(i=0; i<4; i++) {
            temp[0] = GFMultiplication(d, state[0][i]) ^ GFMultiplication(a, state[1][i]) ^ GFMultiplication(b, state[2][i]) ^ GFMultiplication(c, state[3][i]);
            temp[1] = GFMultiplication(c, state[0][i]) ^ GFMultiplication(d, state[1][i]) ^ GFMultiplication(a, state[2][i]) ^ GFMultiplication(b, state[3][i]);
            temp[2] = GFMultiplication(b, state[0][i]) ^ GFMultiplication(c, state[1][i]) ^ GFMultiplication(d, state[2][i]) ^ GFMultiplication(a, state[3][i]);
            temp[3] = GFMultiplication(a, state[0][i]) ^ GFMultiplication(b, state[1][i]) ^ GFMultiplication(c, state[2][i]) ^ GFMultiplication(d, state[3][i]);
            for(j=0; j<4; j++) {
                state[j][i] = (byte) (temp[j]);
            }
        }
        return state;
    }
    
    static byte[][] ArrayToMatrix(byte[] array) {
        byte[][] matrix = new byte[4][4];
        int i;
        int j;
        for(i=0; i<4; i++) {
            for(j=0; j<4; j++) {
                matrix[j][i] = array[i * 4 + j];
            }
        }
        return matrix;
    }
    
    static byte[] MatrixToArray(byte[][] matrix) {
        byte[] array = new byte[16];
        int i;
        int j;
        for(i=0; i<4; i++) {
            for(j=0; j<4; j++) {
                array[i * 4 + j] = matrix[j][i];
            }
        }
        return array;
    }
    
    static String StringToHexString(String string) {
        //return String.format("%x", new BigInteger(1, string.getBytes()));
        byte[] temp = string.getBytes();
        return ByteArrayToHexString(temp);
    }
    
    static String HexStringToString(String string) throws UnsupportedEncodingException {
        string = string.replaceAll("00", "");
        byte[] temp = HexStringToByteArray(string);
        return new String(temp, "UTF-8");
    }
    
    static byte[] HexStringToByteArray(String hexString) {
        int len = hexString.length();
//        System.out.println(len);
        byte[] data = new byte[len / 2];
        int i;
        for(i=0; i<len; i+=2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
    
    static String ByteArrayToHexString(byte[] hexArray) {
        String hexString = new String();
        for(byte hex:hexArray) {
            String temp = Integer.toString(hex & 0xFF, 16);
            if(temp.length() == 1)
                temp = "0" + temp;
            hexString += temp;
//            System.out.println(hexString);
        }
        return hexString;
    }
    
    public static String Decrypt(String plain, byte[] key, int c) {
        int i;
        keyList.add(key);
        for(i=0; i<10; i++) {
            keyList.add(KeyExpansion(keyList.get(i), i+2));
//            System.out.println(ByteArrayToHexString(keyList.get(i+1)));
        }
        
        String temp = plain;
        String plainText;
        String finalPlainText = new String();
        byte[][] state;
        byte[] cipher;
        int j;
        
        for(i=0; i<temp.length(); i+=32) {
            state = ArrayToMatrix(nounce);
            for(j=0; j<c; j++) {
                if(j==0) {
                    state = AddRoundKey(state, keyList.get(j));
                } else if(j==c-1) {
                    state = Subtitution(state);
                    state = ShiftRow(state);
                    state = AddRoundKey(state, keyList.get(j));
                } else {
                    state = Subtitution(state);
                    state = ShiftRow(state);
                    state = MixColumn(state);
                    state = AddRoundKey(state, keyList.get(j));
                }
            }
            nounce = MatrixToArray(state);
            
            cipher = HexStringToByteArray(temp.substring(i, i+32));
            state = AddRoundKey(state, cipher);

            plainText = ByteArrayToHexString(MatrixToArray(state));
            finalPlainText += plainText;
        }
        
        return finalPlainText;
    }
    
    public static void Set(byte[] key, byte[] iv) {
        initKey = key;
        nounce = iv;
    }
    
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println("42924c1e1b373f582be1772c486603e77012a17641d8b9998bbe1f1caf906433");
//        String temp = Decrypt("42924c1e1b373f582be1772c486603e77012a17641d8b9998bbe1f1caf906433", initKey, 10);
//        System.out.println(temp);
//        System.out.println(HexStringToString(temp));
//        
//        byte[] b = new byte[16];
//        new Random().nextBytes(b);
//        System.out.println(ByteArrayToHexString(b));
//        System.out.println(new String(HexStringToByteArray(ByteArrayToHexString(b)), "UTF-8"));
//                
//    }
    
    
}
