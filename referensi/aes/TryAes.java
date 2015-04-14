/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author user
 */
public class TryAes {
    public static void main(String[] args) throws UnsupportedEncodingException {
        
        String input = "Dalam kriptografi, Advanced Encryption Standard (AES) merupakan standar enkripsi dengan kunci-simetris yang diadopsi oleh pemerintah Amerika Serikat. Standar ini terdiri atas 3 blok cipher, yaitu AES-128, AES-192 and AES-256, yang diadopsi dari koleksi yang lebih besar yang awalnya diterbitkan sebagai Rijndael. Masing-masing cipher memiliki ukuran 128-bit, dengan ukuran kunci masing-masing 128, 192, dan 256 bit. AES telah dianalisis secara luas dan sekarang digunakan di seluruh dunia, seperti halnya dengan pendahulunya, Data Encryption Standard (DES).";
                //"Wewwwwwww...... wkwkwkwk :v";
        
        System.out.println("AES");
        
        System.out.println(input);
        System.out.println(AesEncrypt.StringToHexString(input));
        String temp = AesEncrypt.Encrypt(input, AesEncrypt.initKey, 10);
        System.out.println(temp);
        
        
        System.out.println(temp);
        String temp2 = AesDecrypt.Decrypt(temp, AesDecrypt.initKey, 10);
        System.out.println(temp2);        
        System.out.println(AesDecrypt.HexStringToString(temp2));
        
        
        System.out.println("");
        System.out.println("AES OFB");
        
        System.out.println(input);
        System.out.println(AesEncryptOfbMode.StringToHexString(input));
        String temp3 = AesEncryptOfbMode.Encrypt(input, AesEncryptOfbMode.initKey, 10);
        System.out.println(temp3);
        
        
        System.out.println(temp3);
        String temp4 = AesDecryptOfbMode.Decrypt(temp3, AesDecryptOfbMode.initKey, 10);
        System.out.println(temp4);
        System.out.println(AesDecryptOfbMode.HexStringToString(temp4));
        
        
    
    }
}
