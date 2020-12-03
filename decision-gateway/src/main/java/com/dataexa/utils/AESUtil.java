package com.dataexa.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 加解密工具类 加密方式：AES128(CBC/PKCS5Padding) + Base64, 私钥：risk-decision
 * @Author 胡志成
 * @Date 2020/7/20
 */
@Slf4j
public class AESUtil {

    /**
     * 私钥
     */
    private static final String AES_KEY = "risk-decision";
    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 密钥长度
     */
    private static final int KEY_SIZE = 128;

    /**
     * ECB加密内容,密钥必须为16字节的倍数位,否则抛异常,需要字节补全再进行加密
     * CBC机密内容需要初始化偏移量
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";


    public static Key getKey() throws Exception {
        // 密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        //SecureRandom 是生成安全随机数序列，password.getBytes() 是种子，只要种子相同，序列就一样，密钥也一样
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(AES_KEY.getBytes());
        //初始化密钥生成器
        keyGenerator.init(KEY_SIZE, random);
        //生成密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //生产字节码数据
        byte[] byteKey = secretKey.getEncoded();
        //通过字节码数据key 恢复密钥
        return new SecretKeySpec(byteKey, KEY_ALGORITHM);
    }

    /**
     * 加密
     * @param cleartext 明文
     * @return  返回密文
     */
    public static String encrypt(String cleartext) throws Exception {
        Key key = getKey();
        //实例化加密类，参数为加密方式，PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); //
        //加密时使用:ENCRYPT_MODE，解密时使用:DECRYPT_MODE;CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
        cipher.init(Cipher.ENCRYPT_MODE, key); //
        //加密操作,返回加密后的字节数组，编码
        byte[] encryptedData = cipher.doFinal(cleartext.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(encryptedData));
    }

    /**
     * 解密
     * @param encrypted 密文
     * @return 返回明文
     */
    public static String decrypt(String encrypted) throws Exception {
        byte[] decode = Base64.getDecoder().decode(encrypted);
        Key key = getKey();
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //与加密时不同MODE:Cipher.DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(decode);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}
