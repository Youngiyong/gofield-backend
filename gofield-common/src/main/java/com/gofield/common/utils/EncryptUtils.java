package com.gofield.common.utils;

import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.enums.ErrorAction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptUtils {
    private static String sha256BytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }


    public static String sha256Encrypt(String text) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());

            return sha256BytesToHex(md.digest());
        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, "암호화 오류");
        }
    }

    public static String aes256Encrypt(String key, String data) {
        String alg = "AES/CBC/PKCS5Padding";

        String iv = key.substring(0, 16); //16byte

        try {
            Cipher cipher = Cipher.getInstance(alg);

            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted1 = cipher.doFinal(data.getBytes("UTF-8"));

            return Base64.getUrlEncoder().encodeToString(encrypted1);

        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, "암호화 오류" );
        }
    }

    public static String aes256Decrypt(String key, String data) {
        String alg = "AES/CBC/PKCS5Padding";
        String iv = key.substring(0, 16); //16byte
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");

            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getUrlDecoder().decode(data);
            byte[] decrypted = cipher.doFinal(decodedBytes);

            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, "복호화 오류");
        }
    }


}
