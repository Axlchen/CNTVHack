package xyz.axlchen.cntvhack.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.core.CoreClassManager;
import xyz.axlchen.cntvhack.data.entity.ChannelList;
import xyz.axlchen.cntvhack.data.entity.NowEpgInfo;
import xyz.axlchen.cntvhack.listener.CommonOnItemTouchListener;
import xyz.axlchen.cntvhack.net.service.EpgService;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ViewHolder> {

    private static final String TAG = "ChannelListAdapter";
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
            final ChannelList.ChannelInfo channelInfo = mChannelList.get(position);
            holder.name.setText(channelInfo.getTitle());
            Glide.with(mContext).load("http://t.live.cntv.cn/imagehd/"
                    + channelInfo.getChannelId() + "_01.png").into(holder.keyFrame);
            holder.currentItem.setTag(channelInfo.getChannelId());
            CoreClassManager.getGsonRetrofit().create(EpgService.class).getNowEpg(channelInfo.getChannelId())
                    .enqueue(new Callback<NowEpgInfo>() {
                        @Override
                        public void onResponse(Call<NowEpgInfo> call, Response<NowEpgInfo> response) {
                            if (response.body() != null &&
                                    holder.currentItem.getTag().equals(channelInfo.getChannelId())) {
                                if (TextUtils.isEmpty(response.body().getErrcode())) {
                                    NowEpgInfo nowEpgInfo = response.body();
                                    holder.currentItem.setText(nowEpgInfo.getItemName());
                                    holder.progressLayout.setVisibility(View.VISIBLE);
                                    if (!TextUtils.isEmpty(nowEpgInfo.getStartString()) &&
                                            nowEpgInfo.getStartString().trim().length() > 5) {
                                        holder.startTime.setText(nowEpgInfo.getStartString().trim().substring(0, 5));
                                    }
                                    if (!TextUtils.isEmpty(nowEpgInfo.getEndString()) &&
                                            nowEpgInfo.getEndString().trim().length() > 5) {
                                        holder.endTime.setText(nowEpgInfo.getEndString().trim().substring(0, 5));
                                    }
                                    holder.progress.setProgress(getProgress(nowEpgInfo.getStart(),
                                            nowEpgInfo.getNow(), nowEpgInfo.getEnd()));
                                } else {
                                    holder.currentItem.setText("暂无节目单");
                                    holder.progressLayout.setVisibility(View.INVISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<NowEpgInfo> call, Throwable t) {
                            Log.d(TAG, t.toString());
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

    private int getProgress(long start, long now, long end) {
        return (int) ((now - start) * 1.0f / (end - start) * 100);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView currentItem;
        private ImageView keyFrame;
        private TextView startTime;
        private TextView endTime;
        private ProgressBar progress;
        private LinearLayout progressLayout;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_name);
            currentItem = view.findViewById(R.id.tv_current_item);
            keyFrame = view.findViewById(R.id.iv_key_frame);
            startTime = view.findViewById(R.id.tv_start_time);
            endTime = view.findViewById(R.id.tv_end_time);
            progressLayout = view.findViewById(R.id.ll_progress);
            progress = view.findViewById(R.id.pb_progress);
            progress.setMax(100);
        }
    }
}
