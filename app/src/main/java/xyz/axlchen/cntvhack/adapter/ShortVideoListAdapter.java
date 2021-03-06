package xyz.axlchen.cntvhack.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.ui.activity.PlayVideoActivity;
import xyz.axlchen.cntvhack.data.entity.ShortVideoInfo;
import xyz.axlchen.cntvhack.listener.CommonOnItemTouchListener;

public class ShortVideoListAdapter extends RecyclerView.Adapter<ShortVideoListAdapter.ViewHolder> {

    private List<ShortVideoInfo> mVideoList;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public ShortVideoListAdapter(RecyclerView recyclerView, List<ShortVideoInfo> videoList) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        CommonOnItemTouchListener commonOnItemTouchListener = new CommonOnItemTouchListener(recyclerView.getContext());
        commonOnItemTouchListener.setOnItemClickListener(new CommonOnItemTouchListener.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (mVideoList != null && mVideoList.size() > position){
                    Intent intent = new Intent(mRecyclerView.getContext(), PlayVideoActivity.class);
                    intent.putExtra(PlayVideoActivity.VIDEO_ID,mVideoList.get(position).getGuid());
                    mContext.startActivity(intent);
                }
            }
        });
        mRecyclerView.addOnItemTouchListener(commonOnItemTouchListener);
        setVideoList(videoList);
    }

    public void setVideoList(List<ShortVideoInfo> videoList) {
        if (mVideoList == null) {
            mVideoList = new ArrayList<>();
        }
        if (videoList != null && videoList.size() > 0) {
            mVideoList.clear();
            mVideoList.addAll(videoList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_short_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mVideoList.size() > position) {
            ShortVideoInfo shortVideoInfo = mVideoList.get(position);
            holder.title.setText(shortVideoInfo.getTitle());
            Glide.with(mContext).load(shortVideoInfo.getKeyFrame()).into(holder.keyFrame);
        }
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView keyFrame;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            keyFrame = itemView.findViewById(R.id.iv_key_frame);
        }
    }
}
