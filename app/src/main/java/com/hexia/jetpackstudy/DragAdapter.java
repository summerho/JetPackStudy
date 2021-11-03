package com.hexia.jetpackstudy;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexia.jetpackstudy.itemdrag.ItemTouchHelperAdapter;
import com.hexia.jetpackstudy.model.DragBean;

import java.util.Collections;
import java.util.List;

public class DragAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private Context mContext;

    private List<DragBean> mDataList;

    private int mItemLayoutId;

    public DragAdapter(Context context, List<DragBean> list, int itemLayoutId) {
        mContext = context;
        mDataList = list;
        mItemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(mItemLayoutId, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DragBean bean = mDataList.get(position);
        ((ViewHolder) holder).setData(bean);
        holder.itemView.setOnLongClickListener(view -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            DragActivity.isFromBottomRv = mItemLayoutId == R.layout.bottom_item_layout;
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder holder, int fromPosition, int targetPosition) {
        if (fromPosition < mDataList.size() && targetPosition < mDataList.size()) {
            Collections.swap(mDataList, fromPosition, targetPosition);
            notifyItemMoved(fromPosition, targetPosition);
        }
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder holder) {
//        holder.itemView.setScaleX(1.0f);
//        holder.itemView.setScaleY(1.0f);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder holder) {
//        holder.itemView.setScaleX(1.0f);
//        holder.itemView.setScaleY(1.0f);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTv = itemView.findViewById(R.id.name_tv);
        }

        public void setData(DragBean bean) {
            if (bean != null) {
                mNameTv.setText(bean.name);
            }
        }
    }
}
