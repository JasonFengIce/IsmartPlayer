package cn.ismartv.ismartplayer.data;

/**
 * Created by huibin on 5/19/16.
 */
public class AccountPreferences {
    private static String skyApiHost;
    private static String deviceToken;
    private static String snToken;

    public static String getDeviceToken() {
        return deviceToken;
    }

    public static void setDeviceToken(String deviceToken) {
        AccountPreferences.deviceToken = deviceToken;
    }

    public static String getSnToken() {
        return snToken;
    }

    public static void setSnToken(String snToken) {
        AccountPreferences.snToken = snToken;
    }

    public static String getSkyApiHost() {
        return skyApiHost;
    }

    public static void setSkyApiHost(String skyApiHost) {
        AccountPreferences.skyApiHost = skyApiHost;
    }
}
