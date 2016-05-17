package cn.ismartv.ismartplayer.core;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.ismartv.ismartplayer.data.ChannelEntity;
import cn.ismartv.log.interceptor.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import timber.log.Timber;

/**
 * Created by huaijie on 5/17/16.
 */
public class HttpApi {
    private static final int DEFAULT_CONNECT_TIMEOUT = 2;
    private static final int DEFAULT_READ_TIMEOUT = 5;

    private static HttpApi ourInstance = new HttpApi();


    private OkHttpClient client;

    public Retrofit resetAdapter_SKY;

    public static HttpApi getInstance() {
        return ourInstance;
    }

    private HttpApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();


        resetAdapter_SKY = new Retrofit.Builder()
                .client(client)
                .baseUrl(appendProtocol("skytest.tvxio.com/v3_0/SKY2/tou"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }


    private String appendProtocol(String host) {
        Uri uri = Uri.parse(host);
        String url = uri.toString();
        if (!uri.toString().startsWith("http://") && !uri.toString().startsWith("https://")) {
            url = "http://" + host;
        }

        if (!url.endsWith("/")) {
            url = url + "/";
        }
        return url;
    }

    public interface Channels {
        @GET("/api/tv/channels/")
        Call<ArrayList<ChannelEntity>> doRequest(
        );
    }
}
