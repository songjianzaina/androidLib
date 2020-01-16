package com.insworks.lib_net.net.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import com.zhouyou.http.utils.HttpLog;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * <b>类名称：</b> MD5 <br/>
 * <b>类描述：</b> MD5值计算<br/>
 * <b>创建人：</b> 林肯 <br/>
 * <b>修改人：</b> 编辑人 <br/>
 * <b>修改时间：</b> 2015年08月11日 下午2:41 <br/>
 * <b>修改备注：</b> <br/>
 *
 * @version 1.0.0 <br/>
 */
public class MD5 {
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * 消息摘要.
     */
    private static MessageDigest sDigest;

    static {
        try {
            MD5.sDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            HttpLog.e("获取MD5信息摘要失败" + e);
        }
    }

    /**
     * MD5值计算
     * MD5的算法在RFC1321 中定义:
     * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
     * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
     * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
     * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
     * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
     * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
     *
     * @param res 源字符串
     * @return md5值
     */
    public static String encode(String res) {

        try {
            byte[] strTemp = res.getBytes();
            sDigest.update(strTemp);
            byte[] md = sDigest.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String dd = new String(str);
            return dd;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        if (sDigest == null) {
            HttpLog.e("MD5信息摘要初始化失败");
            return null;
        } else if (TextUtils.isEmpty(inStr)) {
            HttpLog.e("参数strSource不能为空");
            return null;
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = sDigest.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 先使用MD5进行加密，再使用Base64进行编码， 若不支持此类字符集合的加密，返回null.
     *
     * @param strSource 待加密的源字符串
     * @return 加密后的字符串，不支持此类字符集合返回null
     */
    public static String encrypt(final String strSource) {
        if (sDigest == null) {
            HttpLog.e("MD5信息摘要初始化失败");
            return null;
        } else if (TextUtils.isEmpty(strSource)) {
            HttpLog.e("参数strSource不能为空");
            return null;
        }
        try {
            byte[] md5Bytes = sDigest.digest(strSource
                    .getBytes("utf-8"));
            byte[] encryptBytes = Base64.encode(md5Bytes, Base64.DEFAULT);
            String strEncrypt = new String(encryptBytes, "utf-8");
            return strEncrypt.substring(0, strEncrypt.length() - 1); // 截断Base64产生的换行符
        } catch (UnsupportedEncodingException e) {
            HttpLog.e("加密模块暂不支持此字符集合" + e);
        }
        return null;
    }

    public static String encrypt4login(final String strSource, String appSecert) {
        String str = encrypt(strSource) + appSecert;
        return string2MD5(str);
    }



    public static String encodeHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);

        for(int i = 0; i < data.length; ++i) {
            sb.append(hexDigits[(data[i] & 240) >>> 4]);
            sb.append(hexDigits[data[i] & 15]);
        }

        return sb.toString();
    }

    @TargetApi(8)
    public static String encodeBase64String(byte[] data) {
        if (Build.VERSION.SDK_INT < 8) {
            HttpLog.e( "This API is not supported on platforms older than Froyo");
            return null;
        } else {
            return Base64.encodeToString(data, 2);
        }
    }

    public static String computeMd5HexString(byte[] data) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data);
            return encodeHexString(md5.digest());
        } catch (NoSuchAlgorithmException var2) {
            HttpLog.w("Should never happen!", var2);
            return null;
        }
    }

    public static String computeMd5HexString(String data) {
        try {
            return computeMd5HexString(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var2) {
            HttpLog.w( "should never happen!", var2);
            return null;
        }
    }

    public static String computeMd5HexString(InputStream is) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte['耀'];

            int len;
            while((len = is.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, len);
            }

            return encodeHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException var4) {
            HttpLog.w( "Should never happen!", var4);
        } catch (IOException var5) {
            HttpLog.w( "unexpected exception happened", var5);
        }

        return null;
    }

    public static String computeFileMd5HexString(String filepath) {
        File apkFile = new File(filepath);
        if (!apkFile.exists()) {
            return null;
        } else {
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(apkFile);
                String var3 = computeMd5HexString((InputStream)fis);
                return var3;
            } catch (FileNotFoundException var7) {
                HttpLog.w( "should not happen", var7);
            } finally {
                close(fis);
            }

            return null;
        }
    }
    private static   void close(Closeable target) {
        try {
            if (target != null) {
                target.close();
            }
        } catch (IOException var2) {
            ;
        }

    }
}
