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
import xyz.axlchen.cntvhack.data.entity.ProgramVideoInfo;
import xyz.axlchen.cntvhack.listener.CommonOnItemTouchListener;
import xyz.axlchen.cntvhack.util.DensityUtil;

public class ProgramVideoListAdapter extends RecyclerView.Adapter<ProgramVideoListAdapter.ViewHolder> {

    private List<ProgramVideoInfo> mProgramVideoList;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public ProgramVideoListAdapter(RecyclerView recyclerView, List<ProgramVideoInfo> programVideoList) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        CommonOnItemTouchListener commonOnItemTouchListener = new CommonOnItemTouchListener(recyclerView.getContext());
        commonOnItemTouchListener.setOnItemClickListener(new CommonOnItemTouchListener.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (mProgramVideoList != null && mProgramVideoList.size() > position) {
                    Intent intent = new Intent(mRecyclerView.getContext(), PlayVideoActivity.class);
                    intent.putExtra(PlayVideoActivity.VIDEO_ID, mProgramVideoList.get(position).getGuid());
                    mContext.startActivity(intent);
                }
            }
        });
        mRecyclerView.addOnItemTouchListener(commonOnItemTouchListener);
        setProgramVideoList(programVideoList);
    }

    public void setProgramVideoList(List<ProgramVideoInfo> programVideoList) {
        if (mProgramVideoList == null) {
            mProgramVideoList = new ArrayList<>();
        }
        if (programVideoList != null && programVideoList.size() > 0) {
            mProgramVideoList.clear();
            mProgramVideoList.addAll(programVideoList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_program_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mProgramVideoList.size() > position) {
            ProgramVideoInfo videoInfo = mProgramVideoList.get(position);
            holder.title.setText(videoInfo.getTitle());
            Glide.with(mContext)
                    .load(DensityUtil.getDensity(mContext) >= DensityUtil.XXHDPI ? videoInfo.getImage2() : videoInfo.getImage1())
                    .into(holder.keyFrame);
        }
    }

    @Override
    public int getItemCount() {
        return mProgramVideoList.size();
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
