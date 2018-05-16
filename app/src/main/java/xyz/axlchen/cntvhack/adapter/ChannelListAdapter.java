package xyz.axlchen.cntvhack.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.data.entity.ChannelList;
import xyz.axlchen.cntvhack.listener.CommonOnItemTouchListener;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ViewHolder> {

    private List<ChannelList.ChannelInfo> mChannelList;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public ChannelListAdapter(RecyclerView recyclerView, List<ChannelList.ChannelInfo> channelList) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        CommonOnItemTouchListener commonOnItemTouchListener = new CommonOnItemTouchListener(recyclerView.getContext());
        commonOnItemTouchListener.setOnItemClickListener(new CommonOnItemTouchListener.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (mChannelList != null && mChannelList.size() > position) {

                }
            }
        });
        mRecyclerView.addOnItemTouchListener(commonOnItemTouchListener);
        setChannelList(channelList);
    }

    public void setChannelList(List<ChannelList.ChannelInfo> channelList) {
        if (mChannelList == null) {
            mChannelList = new ArrayList<>();
        }
        if (channelList != null && channelList.size() > 0) {
            mChannelList.clear();
            mChannelList.addAll(channelList);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mChannelList.size() > position) {
            ChannelList.ChannelInfo channelInfo = mChannelList.get(position);
            holder.name.setText(channelInfo.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView currentItem;
        private ImageView keyFrame;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_name);
            currentItem = view.findViewById(R.id.tv_current_item);
            keyFrame = view.findViewById(R.id.iv_key_frame);
        }
    }
}
