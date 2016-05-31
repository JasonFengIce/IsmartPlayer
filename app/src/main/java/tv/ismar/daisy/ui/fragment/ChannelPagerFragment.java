package tv.ismar.daisy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
import tv.ismar.daisy.R;
import tv.ismar.daisy.core.HttpApi;
import tv.ismar.daisy.data.AccountPreferences;
import tv.ismar.daisy.data.ChannelEntity;
import tv.ismar.daisy.data.ClipInfoEntity;
import tv.ismar.daisy.data.HomePageEntity;
import tv.ismar.daisy.data.ItemEntity;

/**
 * Created by huibin on 5/20/16.
 */
public class ChannelPagerFragment extends Fragment {

    private static final String TAG = "ChannelPagerFragment";
    @BindView(R.id.channel_list)
    RecyclerView channelListLayout;


    private List<HomePageEntity.Posters> mPosters;

    private ChannelListAdapter mChannelListAdapter;

    public static ChannelPagerFragment newInstance(ChannelEntity channelEntity) {
        ChannelPagerFragment newFragment = new ChannelPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("channel", new Gson().toJson(channelEntity));
        newFragment.setArguments(bundle);
        return newFragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_channel, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        channelListLayout.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        mChannelListAdapter = new ChannelListAdapter();
        channelListLayout.setAdapter(mChannelListAdapter);

        ChannelEntity channelEntity = new Gson().fromJson(getArguments().getString("channel"), ChannelEntity.class);
        fetchChannelHomepage(channelEntity.getHomepage_url());
    }


    private void fetchChannelHomepage(String api) {
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

                    HomePageEntity homePageEntity = new Gson().fromJson(response.body().string(), HomePageEntity.class);
                    mPosters = homePageEntity.getPosters();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mChannelListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    private void fillChannelLayout(HomePageEntity homePageEntity) {

    }


    class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelListHolder> implements View.OnClickListener {
        @Override
        public ChannelListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(
                    getActivity()).inflate(R.layout.item_channel_list, parent,
                    false);
            itemView.setOnClickListener(this);
            ChannelListHolder holder = new ChannelListHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(ChannelListHolder holder, int position) {
            if (mPosters != null) {
                holder.itemView.setTag(mPosters.get(position));
                holder.title.setText(mPosters.get(position).getTitle());
                Glide.with(ChannelPagerFragment.this).load(mPosters.get(position).getCustom_image()).into(holder.image);
            }
        }

        @Override
        public int getItemCount() {
            if (mPosters != null) {
                return mPosters.size();
            }
            return 0;
        }

        @Override
        public void onClick(View v) {
            HomePageEntity.Posters posters = ((HomePageEntity.Posters) v.getTag());
            fetchItemInfo(posters.getUrl());
        }

        class ChannelListHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title)
            TextView title;

            @BindView(R.id.image)
            ImageView image;

            View itemView;

            public ChannelListHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                itemView = view;
            }
        }
    }

    private void fetchClipInfo(String pk) {
        BuildUrl buildUrl = BuildUrl.getInstance();
        Retrofit retrofit = HttpApi.getInstance().resetAdapter_SKY;
        retrofit.create(HttpApi.ClipInfo.class).doRequest(pk, AccountPreferences.getDeviceToken(), getAES(AccountPreferences.getSnToken(), ""), "1").enqueue(new retrofit2.Callback<ClipInfoEntity>() {
            @Override
            public void onResponse(retrofit2.Call<ClipInfoEntity> call, retrofit2.Response<ClipInfoEntity> response) {
                ClipInfoEntity clipInfoEntity = response.body();

                if (!TextUtils.isEmpty(clipInfoEntity.getIqiyi_4_0())) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), SdkTestActivity.class);
                    intent.putExtra("clip_info", clipInfoEntity.getIqiyi_4_0());
                    startActivity(intent);
                } else {
                    String url = aesDecrypt(clipInfoEntity.getBestUrl(), AccountPreferences.getDeviceToken());
                    Log.i(TAG, "url: " + url);

                    VideoActivity.intentTo(getContext(), url, url);
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

    private void fetchItemInfo(String api) {
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
                    fetchClipInfo(String.valueOf(itemEntity.getClip().getPk()));
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mChannelListAdapter.notifyDataSetChanged();
//                        }
//                    });
                }
            }
        });
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
