package com.backend.inventaris.security;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class Crypto {

    private static final String defaultKey = "a591c8fd7ffbc187dc09c5b52d423965f2a8ad0772df9523c0fbdcc0f1e52258";
//    private static final String defaultKey = "244db739f6e6b33c8dbe968f4a4ef1b95d328d6ec5470c1a0c7c725b9dd79799";

    public static String performEncrypt(String keyText, String plainText) {
        try{
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] ptBytes = plainText.getBytes();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(true, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
            int oLen = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(Hex.encode(rv));
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performEncrypt(String cryptoText) {
        return performEncrypt(defaultKey, cryptoText);
    }

    public static String performDecrypt(String keyText, String cryptoText) {
        try {
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] cipherText = Hex.decode(cryptoText.getBytes());
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(false, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(cipherText.length)];
            int oLen = cipher.processBytes(cipherText, 0, cipherText.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(rv).trim();
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performDecrypt(String cryptoText) {
        return performDecrypt(defaultKey, cryptoText);
    }

    public static void main(String[] args) {
        String strToEncrypt = "jdbc:sqlserver://host.docker.internal;databaseName=BEB24;schema=dbproject;trustServerCertificate=true";//put text to encrypt in here
        System.out.println("Encryption Result : "+performEncrypt(strToEncrypt));

//        jdbc:sqlserver://java-be-1:3377;databaseName=BEB24;schema=dbproject;trustServerCertificate=true
//        String strToDecrypt = "0d3f28a8cb0577e4fd42fbfdf2f72eac";//put text to decrypt in here
        String strToDecrypt = "a4bf7d61703b44d20737ee237dfa665e2b0545d0ea4504fa4cf1a2155137946b6bc0dbed57e3f4f4edf1894b1c5db397f0598fe452c5ce615409fb5de5fbb13842603964fcc0db3ebff8c7ebe9576a34e68b21549fd71a1d10328a78c69397ac";//put text to decrypt in here
        System.out.println("Decryption Result : "+performDecrypt(strToDecrypt));
    }
}