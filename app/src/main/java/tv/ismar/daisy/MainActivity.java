package tv.ismar.daisy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ismartv.activator.IsmartvActivator;
import cn.ismartv.activator.data.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tv.ismar.daisy.core.HttpApi;
import tv.ismar.daisy.data.AccountPreferences;
import tv.ismar.daisy.data.ChannelEntity;
import tv.ismar.daisy.ui.fragment.ChannelPagerFragment;

public class MainActivity extends AppCompatActivity {
    private static final String DEFAULT_PEATH_HOST = "http://sky.tvxio.com";

    @BindView(R.id.channel_indicator)
    RecyclerView mChannelIndicatorLayout;

    @BindView(R.id.channel_pager)
    ViewPager channelPager;

    private ArrayList<ChannelEntity> mChannelEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        active();



    }

    private void active() {
        new IsmartvActivator(this, new IsmartvActivator.Callback() {
            @Override
            public void onSuccess(Result result) {
                AccountPreferences.setSkyApiHost(result.getDomain());
                AccountPreferences.setDeviceToken(result.getDevice_token());
                AccountPreferences.setSnToken(result.getSn_Token());
                fetchChannel();
            }

            @Override
            public void onFailure(String msg) {

            }
        }, DEFAULT_PEATH_HOST).execute();
    }


    private void fetchChannel() {
        HttpApi.Channels channels = HttpApi.getInstance().resetAdapter_SKY.create(HttpApi.Channels.class);
        channels.doRequest().enqueue(new Callback<ArrayList<ChannelEntity>>() {
            @Override
            public void onResponse(Call<ArrayList<ChannelEntity>> call, Response<ArrayList<ChannelEntity>> response) {
                if (response.body() != null) {
                    mChannelEntities = response.body();
                    fillChannelPager(mChannelEntities);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ChannelEntity>> call, Throwable t) {

            }
        });
    }

    private void fillChannelPager(ArrayList<ChannelEntity> channelEntities) {
        channelPager.setAdapter(new ChannelPagerAdapter(getSupportFragmentManager(), channelEntities));
    }


    private class ChannelPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<ChannelEntity> channelEntities;

        public ChannelPagerAdapter(FragmentManager fm, ArrayList<ChannelEntity> channelEntities) {
            super(fm);
            this.channelEntities = channelEntities;
        }

        @Override
        public Fragment getItem(int position) {
            return ChannelPagerFragment.newInstance(channelEntities.get(position));
        }

        @Override
        public int getCount() {
            return channelEntities.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (mChannelEntities.get(position).getName());
        }
    }
}
