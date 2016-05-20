package cn.ismartv.ismartplayer;

import android.util.Log;

import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by huaijie on 6/2/15.
 */
public class BuildUrl {
    private static final String TAG = "BuildUrl";

    public static final String deviceType = "A11";
    public static final String deviceVersion = "2.0";

    private static BuildUrl instance;

    private BuildUrl() {

    }

    public static BuildUrl getInstance() {
        if (instance == null) {
            instance = new BuildUrl();
        }
        return instance;
    }


    public String getSN() {
        return "001122334455";
    }

    public String getAccessToken() {
        return "";
    }

    public String getSign() {
        return getAES(getSN());
    }


    private String getAES(String sn) {
        String contents = (new Date()).getTime() + sn;
        return encrypt(contents, "smartvdefaultkey");
    }

    private String encrypt(String content, String keyWord) {
        String keyType = "AES/CBC/PKCS5Padding";
        String ivString = "8271454372123456";
        StringBuffer sb = new StringBuffer();
        try {
            SecretKeySpec e = new SecretKeySpec(keyWord.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(keyType);
            byte[] byteContent = content.getBytes("utf-8");
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            cipher.init(1, e, iv);
            byte[] result = cipher.doFinal(byteContent);

            //------------------
            for (int i = 0; i < result.length; ++i) {
                String hex = Integer.toHexString(result[i] & 255);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return sb.toString();
    }

}
