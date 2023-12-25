package com.roubao.util;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/4/5
 **/
public class MD5Utils {
    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    private static final String MESSAGE_DIGEST = "MD5";

    private static final String DEFAULT_SALT = "Jia-Store-Key";


    /**
     * 加密字符串 使用默认盐值加密字符串
     *
     * @param clearStr 未加密字符串
     * @return 加密字符
     */
    public static String encrypt(String clearStr) {
        return encrypt(clearStr, DEFAULT_SALT);
    }


    /**
     * 验证字符串
     *
     * @param cypher   加密字符
     * @param clearStr 原字符
     * @return 验证是否正确
     */
    public static boolean verify(String cypher, String clearStr) {
        //使用默认盐值验证字符串
        return verify(cypher, clearStr, DEFAULT_SALT);
    }

    /**
     * 加密
     *
     * @param clearStr 原密码
     * @return 密码密文
     */
    public static String encrypt(String clearStr, String salt) {
        if (StrUtil.isEmpty(clearStr)) {
            logger.error("MD5Util ==> The encryption password cannot be empty.");
            return null;
        }

        // 使用指定的算法名称创建消息摘要。可选项有 MD5  SHA-1  SHA-256
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(MESSAGE_DIGEST);
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5Util ==> Encrypt is error[NoSuchAlgorithmException]. return default cipher is empty. ErrorMessage:{}", e.getMessage());
            return null;
        }

        // 使用指定的字节更新摘要（原密码+盐值）
        md.update((clearStr + salt).getBytes());

        //  产生用于生成的哈希值的字节数组。
        byte[] md5Bytes = md.digest();
        return byteArrayToHex(md5Bytes);
    }

    /**
     * 校验密码是否正确
     *
     * @param cypher   旧密码密文
     * @param clearStr 新密码原文
     * @return 校验结果
     */
    public static boolean verify(String cypher, String clearStr, String salt) {
        String newEncrypt = encrypt(clearStr, salt);
        if (StrUtil.isEmpty(newEncrypt) || StrUtil.isEmpty(clearStr)) {
            return false;
        }
        return newEncrypt.equals(cypher);
    }

    private static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'b', 'd', 'e', 'f'};

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    private MD5Utils() {
    }
}
