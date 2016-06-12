package tv.ismar.daisy.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import tv.ismar.daisy.R;
import tv.ismar.daisy.core.ItemInfoManager;
import tv.ismar.daisy.data.ItemEntity;

/**
 * Created by huibin on 6/12/16.
 */
public class EpisodeListActivity extends Activity {

    private ItemEntity.SubItem[] mSubItems;
    private EpisodeListAdapter mEpisodeListAdapter;

    @BindView(R.id.episode_list)
    public RecyclerView mEpisodeListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        ItemEntity itemEntity = new Gson().fromJson(intent.getStringExtra("item"), ItemEntity.class);
        mSubItems = itemEntity.getSubitems();

        mEpisodeListLayout.setLayoutManager(new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false));
        mEpisodeListAdapter = new EpisodeListAdapter();
        mEpisodeListLayout.setAdapter(mEpisodeListAdapter);
    }


    class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeListAdapter.EpisodeListHolder> implements View.OnClickListener {
        @Override
        public EpisodeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(EpisodeListActivity.this).inflate(R.layout.item_episode, parent,
                    false);
            itemView.setOnClickListener(this);
            EpisodeListHolder holder = new EpisodeListHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(EpisodeListHolder holder, int position) {
            if (mSubItems != null && mSubItems.length != 0) {
                holder.itemView.setTag(mSubItems[position]);
                holder.title.setText("第" + (position + 1) + "集");
            }
        }

        @Override
        public int getItemCount() {
            if (mSubItems != null) {
                return mSubItems.length;
            }
            return 0;
        }

        @Override
        public void onClick(View v) {
            ItemEntity.SubItem subItem = (ItemEntity.SubItem) v.getTag();
            ItemInfoManager.getInstance().fetchItemInfo(EpisodeListActivity.this, subItem.getUrl());
        }

        class EpisodeListHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title)
            TextView title;

            View itemView;

            public EpisodeListHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                itemView = view;
            }
        }
    }

}
