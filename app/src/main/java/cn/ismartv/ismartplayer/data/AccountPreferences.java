package cn.ismartv.ismartplayer.data;

/**
 * Created by huibin on 5/19/16.
 */
public class AccountPreferences {
    private static String skyApiHost;

    public static String getSkyApiHost() {
        return skyApiHost;
    }

    public static void setSkyApiHost(String skyApiHost) {
        AccountPreferences.skyApiHost = skyApiHost;
    }
}
