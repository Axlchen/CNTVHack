package xyz.axlchen.cntvhack.adapter;

import android.content.Context;
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
import xyz.axlchen.cntvhack.data.entity.ProgramInfo;
import xyz.axlchen.cntvhack.listener.CommonOnItemTouchListener;

public class ProgramListAdapter extends RecyclerView.Adapter<ProgramListAdapter.ViewHolder> {

    private List<ProgramInfo> mProgramList;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public ProgramListAdapter(RecyclerView recyclerView, List<ProgramInfo> programList) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        CommonOnItemTouchListener commonOnItemTouchListener = new CommonOnItemTouchListener(recyclerView.getContext());
        commonOnItemTouchListener.setOnItemClickListener(new CommonOnItemTouchListener.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (mProgramList != null && mProgramList.size() > position) {
                }
            }
        });
        mRecyclerView.addOnItemTouchListener(commonOnItemTouchListener);
        setProgramList(programList);
    }

    public void setProgramList(List<ProgramInfo> programList) {
        if (mProgramList == null) {
            mProgramList = new ArrayList<>();
        }
        if (programList != null && programList.size() > 0) {
            mProgramList.clear();
            mProgramList.addAll(programList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_program_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mProgramList.size() > position) {
            ProgramInfo programInfo = mProgramList.get(position);
            holder.title.setText(programInfo.getMediaName());
            holder.description.setText(programInfo.getDescription());
            Glide.with(mContext).load(programInfo.getLogoImg()).into(holder.logo);
        }
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private ImageView logo;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            logo = itemView.findViewById(R.id.iv_logo);
        }
    }
}
