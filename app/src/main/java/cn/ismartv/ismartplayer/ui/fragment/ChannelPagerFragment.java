package cn.ismartv.ismartplayer.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ismartv.ismartplayer.R;
import cn.ismartv.ismartplayer.core.HttpApi;
import cn.ismartv.ismartplayer.data.ChannelEntity;
import cn.ismartv.ismartplayer.data.HomePageEntity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by huibin on 5/20/16.
 */
public class ChannelPagerFragment extends Fragment {

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


    class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelListHolder> {
        @Override
        public ChannelListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ChannelListHolder holder = new ChannelListHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.item_channel_list, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ChannelListHolder holder, int position) {
            if (mPosters != null) {
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

        class ChannelListHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title)
            TextView title;

            @BindView(R.id.image)
            ImageView image;

            public ChannelListHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
