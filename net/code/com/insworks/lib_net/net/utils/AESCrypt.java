package com.insworks.lib_net.net.utils;

import android.util.Base64;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 此工具类可用
 */
public class AESCrypt {
    private final Cipher cipher;
    private final SecretKeySpec key;
    private AlgorithmParameterSpec spec;

    /**
     * @param password 加密密码
     * @throws Exception
     */
    public AESCrypt(String password) throws Exception {
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        key = new SecretKeySpec(password.getBytes("utf-8"), "AES");
//        spec = getIV();
    }


    /**
     *
     * @throws Exception
     */
    public AESCrypt() throws Exception {
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        String password="b9u4seycnpjgo6zvc1586ycexjsiv58w";
        key = new SecretKeySpec(password.getBytes("utf-8"), "AES");
    }

    /**
     * @param password 加密密码
     * @throws Exception
     */
    public AESCrypt(String password, String iv) throws Exception {
        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        key = new SecretKeySpec(password.getBytes("utf-8"), "AES");
        spec = new IvParameterSpec(iv.getBytes());
    }

    public AlgorithmParameterSpec getIV() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        IvParameterSpec
                ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public String encrypt(String plainText) throws Exception {
        if (spec == null) {
            //ECB 模式下不需要iv 向量
            cipher.init(Cipher.ENCRYPT_MODE, key);

        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        }

        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedText = new String(Base64.encode(encrypted,
                Base64.DEFAULT), "UTF-8");
        return encryptedText;
    }

    public String decrypt(String cryptedText) throws Exception {
        if (spec == null) {
            //ECB 模式下不需要iv 向量
            cipher.init(Cipher.ENCRYPT_MODE, key);

        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        }
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(bytes);
        String decryptedText = new String(decrypted, "UTF-8");
        return decryptedText;
    }
}