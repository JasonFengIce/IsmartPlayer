package tv.ismar.daisy.ui.fragment;

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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.ismar.daisy.R;
import tv.ismar.daisy.core.HttpApi;
import tv.ismar.daisy.core.ItemInfoManager;
import tv.ismar.daisy.data.ChannelEntity;
import tv.ismar.daisy.data.ChannelListEntity;
import tv.ismar.daisy.data.ChannelSectionEntity;
import tv.ismar.daisy.data.ItemEntity;

/**
 * Created by huibin on 5/20/16.
 */
public class ChannelPagerFragment extends Fragment {

    private static final String TAG = "ChannelPagerFragment";
    private static final String DEFAULT_LIST_URL = "http://res.tvxio.bestv.com.cn/media/upload/20160321/36c8886fd5b4163ae48534a72ec3a555.png";
    @BindView(R.id.channel_list)
    RecyclerView channelListLayout;


    //    private List<HomePageEntity.Posters> mPosters;
    private List<ItemEntity> mSectionList;

    //    private ChannelListAdapter mChannelListAdapter;
    private ChannelSectionListAdapter mChannelSectionListAdapter;

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
        mSectionList = new ArrayList<>();
        View fragmentView = inflater.inflate(R.layout.fragment_channel, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        channelListLayout.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
//        mChannelListAdapter = new ChannelListAdapter();
//        channelListLayout.setAdapter(mChannelListAdapter);
        mChannelSectionListAdapter = new ChannelSectionListAdapter();
        channelListLayout.setAdapter(mChannelSectionListAdapter);


        ChannelEntity channelEntity = new Gson().fromJson(getArguments().getString("channel"), ChannelEntity.class);
//        fetchChannelHomepage(channelEntity.getHomepage_url());
        fetchListUrl(channelEntity.getUrl());
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

//                    HomePageEntity homePageEntity = new Gson().fromJson(response.body().string(), HomePageEntity.class);
//                    mPosters = homePageEntity.getPosters();
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

    private void fetchListUrl(String api) {

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
                    ChannelListEntity[] channelListEntities = new Gson().fromJson(response.body().string(), ChannelListEntity[].class);
                    mSectionList = new ArrayList<ItemEntity>();
                    for (ChannelListEntity entity : channelListEntities) {
                        fetchSection(entity.getUrl());
                    }
                }
            }
        });

    }

    private void fetchSection(String api) {
        OkHttpClient okHttpClient = HttpApi.getInstance().getClient();

        final Request request = new Request.Builder()
                .url(api)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    ChannelSectionEntity channelSectionEntity = new Gson().fromJson(response.body().string(), ChannelSectionEntity.class);
                    if (channelSectionEntity != null && channelSectionEntity.getObjects() != null)
                        mSectionList.addAll(channelSectionEntity.getObjects());
                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mChannelSectionListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    class ChannelSectionListAdapter extends RecyclerView.Adapter<ChannelSectionListAdapter.ChannelListHolder> implements View.OnClickListener {
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
            if (mSectionList != null && !mSectionList.isEmpty()) {
                holder.itemView.setTag(mSectionList.get(position));
                holder.title.setText(mSectionList.get(position).getTitle());

                String listUrl = mSectionList.get(position).getList_url();
                String postUrl = mSectionList.get(position).getPosterUrl();
                String imageUrl;
                if (!listUrl.equals(DEFAULT_LIST_URL)) {
                    imageUrl = listUrl;
                } else {
                    imageUrl = postUrl;
                }

                Glide.with(ChannelPagerFragment.this).load(imageUrl).into(holder.image);
            }
        }

        @Override
        public int getItemCount() {
            if (mSectionList != null) {
                return mSectionList.size();
            }
            return 0;
        }

        @Override
        public void onClick(View v) {
            ItemEntity itemEntity = (ItemEntity) v.getTag();

            ItemInfoManager.getInstance().fetchItemInfo(getContext(), itemEntity.getItem_url());
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
}
