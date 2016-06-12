package tv.ismar.daisy.core;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import cn.ismartv.activator.core.rsa.Coder;
import cn.ismartv.activator.core.rsa.SkyAESTool2;
import cn.ismartv.ijkplayer.activities.VideoActivity;
import cn.ismartv.iqiyiplayer.SdkTestActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import tv.ismar.daisy.AESDemo;
import tv.ismar.daisy.BuildUrl;
import tv.ismar.daisy.data.AccountPreferences;
import tv.ismar.daisy.data.ClipInfoEntity;
import tv.ismar.daisy.data.ItemEntity;
import tv.ismar.daisy.ui.activity.EpisodeListActivity;

/**
 * Created by huibin on 6/12/16.
 */
public class ItemInfoManager {

    private static final String TAG = "ItemInfoManager";

    private static ItemInfoManager ourInstance = new ItemInfoManager();

    public static ItemInfoManager getInstance() {
        return ourInstance;
    }

    private ItemInfoManager() {
    }


    public void fetchItemInfo(final Context context, String api) {
        OkHttpClient okHttpClient = HttpApi.getInstance().getClient();

        Request request = new Request.Builder()
                .url(api)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {

                    ItemEntity itemEntity = new Gson().fromJson(response.body().string(), ItemEntity.class);


                    if (itemEntity.getSubitems() != null && itemEntity.getSubitems().length != 0) {
                        Intent intent = new Intent();
                        intent.setClass(context, EpisodeListActivity.class);
                        intent.putExtra("item", new Gson().toJson(itemEntity));
                        context.startActivity(intent);
                    } else {
                        fetchClipInfo(context, String.valueOf(itemEntity.getClip().getPk()));
                    }
                }
            }
        });
    }


    private void fetchClipInfo(final Context context, String pk) {
        BuildUrl buildUrl = BuildUrl.getInstance();
        Retrofit retrofit = HttpApi.getInstance().resetAdapter_SKY;
        retrofit.create(HttpApi.ClipInfo.class).doRequest(pk, AccountPreferences.getDeviceToken(), getAES(AccountPreferences.getSnToken(), ""), "1").enqueue(new retrofit2.Callback<ClipInfoEntity>() {
            @Override
            public void onResponse(retrofit2.Call<ClipInfoEntity> call, retrofit2.Response<ClipInfoEntity> response) {
                ClipInfoEntity clipInfoEntity = response.body();

                if (!TextUtils.isEmpty(clipInfoEntity.getIqiyi_4_0())) {
                    Intent intent = new Intent();
                    intent.setClass(context, SdkTestActivity.class);
                    intent.putExtra("clip_info", clipInfoEntity.getIqiyi_4_0());
                    context.startActivity(intent);
                } else {
                    String url = aesDecrypt(clipInfoEntity.getBestUrl(), AccountPreferences.getDeviceToken());
                    Log.i(TAG, "url: " + url);

                    VideoActivity.intentTo(context, url, url);
                }

            }


            @Override
            public void onFailure(retrofit2.Call<ClipInfoEntity> call, Throwable t) {

            }
        });
    }

    private static String getAES(String sn, String access_token) {
        String keyCrypt = "smartvdefaultkey";
        String result = null;
        String contents = (new StringBuilder(String.valueOf((new Date())
                .getTime()))).append(sn).toString();
        if (access_token != null && access_token.length() > 0) {
            if (access_token.length() > 15) {
                result = AESDemo.encrypttoStr(contents,
                        access_token.substring(0, 16));
            } else {
                int leng = 16 - access_token.length();
                for (int i = 0; i < leng; i++)
                    access_token = (new StringBuilder(
                            String.valueOf(access_token))).append("0")
                            .toString();

                result = AESDemo.encrypttoStr(contents,
                        access_token.substring(0, 16));
            }
        } else {
            result = AESDemo.encrypttoStr(contents, keyCrypt);// 1422928853725001122334455
        }
        return result;
    }

    private String aesDecrypt(String content, String key) {
        String result = "";
        byte[] base64;
        try {
            base64 = Coder.UrlSafeBase64_decode(content);
            result = SkyAESTool2.decrypt(key.substring(0, 16), base64);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
