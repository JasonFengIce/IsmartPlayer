package tv.ismar.daisy;

import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by huibin on 5/22/16.
 */
public class AESDemo {

    private static String keyType = "AES/CBC/PKCS5Padding";
    private static String ivString = "8271454372123456";

    public AESDemo() {
    }

    public static byte[] encrypt(String content, String keyWord) {
        try {
            SecretKeySpec e = new SecretKeySpec(keyWord.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(keyType);
            byte[] byteContent = content.getBytes("utf-8");
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            System.out.println(ivString.getBytes().length);
            cipher.init(1, e, iv);
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (Exception var7) {
            Log.d("ExceptionMessage", var7.toString());
            var7.printStackTrace();
            return null;
        }
    }

    public static String encrypttoStr(String content, String password) {
        return parseByte2HexStr(encrypt(content, password));
    }

    public static byte[] decrypt(byte[] content, String keyWord) {
        try {
            SecretKeySpec e = new SecretKeySpec(keyWord.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(keyType);
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            cipher.init(2, e, iv);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        } catch (NoSuchPaddingException var7) {
            var7.printStackTrace();
        } catch (InvalidKeyException var8) {
            var8.printStackTrace();
        } catch (IllegalBlockSizeException var9) {
            var9.printStackTrace();
        } catch (BadPaddingException var10) {
            var10.printStackTrace();
        } catch (InvalidAlgorithmParameterException var11) {
            var11.printStackTrace();
        }

        return null;
    }

    public static byte[] decrypt(String content, String keyWord) {
        return decrypt(parseHexStr2Byte(content), keyWord);
    }

    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if(hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if(hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            System.out.println(result.length);
            return result;
        }
    }

    public static void main(String[] args) {
        String content = "322225555666A11111";
        String Key = "952c1d8e4a00000z";
        System.out.println("length=" + Key.getBytes().length);
        System.out.println("source=" + content);
        String encryptResult = encrypttoStr(content, Key);
        System.out.println("after encrypt=" + encryptResult);
        byte[] decryptResult = decrypt(encryptResult, Key);
        System.out.println("result length=" + decryptResult.length);
        System.out.println("decrypt=" + new String(decryptResult));
        String test = "66bf43fdfcd1a964c080c14be76b0d4a".toUpperCase();
        decryptResult = decrypt(test, Key);
        System.out.println("Final:" + new String(decryptResult));
    }
}
