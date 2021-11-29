package com.hexia.jetpackstudy;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexia.jetpackstudy.itemdrag.ItemTouchHelperAdapter;
import com.hexia.jetpackstudy.model.DragBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DragInOneRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    public static final int TYPE_NORMAL = 1001;

    public static final int TYPE_DIVIDER = 1002;

    private final List<DragBean> mDataList;

    private final Context mContext;

    private int mSelectedNum = 0; // 上面的个数

    private int mDragPosition; // 当前手指拖拽的item的position

    protected OnItemDragListener mItemDragListener;

    public DragInOneRvAdapter(Context context, List<DragBean> list) {
        mContext = context;
        mDataList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_NORMAL) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.top_item_layout, null);
            holder = new ItemViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.divider_layout, null);
            holder = new DividerViewHolder(itemView);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            holder.setIsRecyclable(false);
            DragBean bean = mDataList.get(position < mSelectedNum ? position : position - 1);
            ((ItemViewHolder) holder).setData(bean);
        }
    }

    @Override
    public int getItemCount() {
        mSelectedNum = 0;
        for (DragBean bean : mDataList) {
            if (bean.type == 1) {
                mSelectedNum = mSelectedNum + 1;
            }
        }
        return mDataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mSelectedNum) {
            return TYPE_DIVIDER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setOnItemDragListener(OnItemDragListener itemDragListener) {
        this.mItemDragListener = itemDragListener;
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder sourceHolder, RecyclerView.ViewHolder targetHolder, int fromPosition, int targetPosition) {
        if (mDragPosition > 9) { // 最后三个空item不允许拖动
            return;
        }
        DragBean sourceBean;
        DragBean targetBean;
        if (fromPosition < mSelectedNum && targetPosition < mSelectedNum) { // 只在上面来回拖动
            Log.d("summerho", "只在上面来回拖动 fromPosition = " + fromPosition + ", targetPosition = " + targetPosition);
            sourceBean = mDataList.get(fromPosition);
            sourceBean.type = 1;
            targetBean = mDataList.get(targetPosition);
            targetBean.type = 1;
            if (fromPosition < targetPosition) {
                for (int i = fromPosition; i < targetPosition; i++) {
                    Collections.swap(mDataList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > targetPosition; i--) {
                    Collections.swap(mDataList, i, i - 1);
                }
            }
            Log.d("summerho", "只在上面来回拖动 " + mDataList.toString());
            notifyItemMoved(fromPosition, targetPosition);
            ((ItemViewHolder) sourceHolder).setData(sourceBean);
            ((ItemViewHolder) targetHolder).setData(targetBean);
        } else if (fromPosition > mSelectedNum && targetPosition > mSelectedNum) { // 只在下面来回拖动
            Log.d("summerho", "只在下面来回拖动 fromPosition = " + fromPosition + ", targetPosition = " + targetPosition);
            sourceBean = mDataList.get(fromPosition - 1);
            sourceBean.type = 0;
            targetBean = mDataList.get(targetPosition - 1);
            targetBean.type = 0;
            if (fromPosition < targetPosition) {
                for (int i = fromPosition; i < targetPosition; i++) {
                    Collections.swap(mDataList, i - 1, i);
                }
            } else {
                for (int i = fromPosition; i > targetPosition; i--) {
                    Collections.swap(mDataList, i - 1, i - 2);
                }
            }
            Log.d("summerho", "只在下面来回拖动 " + mDataList.toString());
            notifyItemMoved(fromPosition, targetPosition);
            ((ItemViewHolder) sourceHolder).setData(sourceBean);
            ((ItemViewHolder) targetHolder).setData(targetBean);
        } else if (fromPosition > mSelectedNum && targetPosition < mSelectedNum) { // 下面的往上面拖
            Log.d("summerho", "下面的往上面拖 fromPosition = " + fromPosition + ", targetPosition = " + targetPosition);
            Log.d("summerho", "下面的往上面拖 before" + mDataList.toString());
            sourceBean = mDataList.get(fromPosition - 1);
            sourceBean.type = 1;
            targetBean = mDataList.get(targetPosition);
            targetBean.type = 1;
            for (int i = fromPosition; i > targetPosition + 1; i--) {
                Collections.swap(mDataList, i - 1, i - 2);
            }
            Log.d("summerho", "下面的往上面拖 after" + mDataList.toString());
            notifyItemMoved(fromPosition, targetPosition);
            ((ItemViewHolder) sourceHolder).setData(sourceBean);
            ((ItemViewHolder) targetHolder).setData(targetBean);
        } else if (fromPosition < mSelectedNum && targetPosition > mSelectedNum) { // 上面的往下面拖
            Log.d("summerho", "上面的往下面拖 fromPosition = " + fromPosition + ", targetPosition = " + targetPosition);
            Log.d("summerho", "上面的往下面拖 before" + mDataList.toString());
            sourceBean = mDataList.get(fromPosition);
            sourceBean.type = 0;
            targetBean = mDataList.get(targetPosition - 1);
            targetBean.type = 0;
            for (int i = fromPosition; i < targetPosition - 1; i++) {
                Collections.swap(mDataList, i, i + 1);
            }
            Log.d("summerho", "上面的往下面拖 after" + mDataList.toString());
            notifyItemMoved(fromPosition, targetPosition);
            ((ItemViewHolder) sourceHolder).setData(sourceBean);
            ((ItemViewHolder) targetHolder).setData(targetBean);
        } else if (mSelectedNum == 0) { // 全部在下面，下面的网上拖
            Log.d("summerho", "mSelectedNum = 0, fromPosition = " + fromPosition + ", targetPosition = " + targetPosition);
            sourceBean = mDataList.get(fromPosition - 1);
            sourceBean.type = 1;
            if (fromPosition > 1) {
                for (int i = fromPosition; i > 1; i--) {
                    Collections.swap(mDataList, i - 1, i - 2);
                }
                notifyItemMoved(fromPosition, targetPosition);
            } else {
                notifyItemChanged(0);
            }
            ((ItemViewHolder) sourceHolder).setData(sourceBean);
        } else if (fromPosition > mSelectedNum && targetPosition == mSelectedNum && mSelectedNum > 0) { // 下面的往上拖，且上面的最后一行不满3个
            Log.d("summerho","targetPosition = mSelectedNum, mSelectedNum > 0");
            Log.d("summerho", "targetPosition = mSelectedNum, mSelectedNum > 0, before" + mDataList.toString());
            sourceBean = mDataList.get(fromPosition - 1);
            sourceBean.type = 1;
            for (int i = fromPosition; i > targetPosition + 1; i--) {
                Collections.swap(mDataList, i - 1, i - 2);
            }
            notifyItemMoved(fromPosition, targetPosition);
            Log.d("summerho", "targetPosition = mSelectedNum, mSelectedNum > 0, after" + mDataList.toString());
            ((ItemViewHolder) sourceHolder).setData(sourceBean);
        }
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder holder) {
        mDragPosition = holder.getBindingAdapterPosition();
        Log.e("summerho", "onItemSelect, mDragPosition = " + mDragPosition);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder holder) {
        Log.e("summerho", "onItemClear");
        List<DragBean> newList = new ArrayList<>();
        for (DragBean bean : mDataList) {
            if (!TextUtils.isEmpty(bean.name)) {
                newList.add(bean);
            }
        }
        for (int i = 0; i < 3; i++) {
            newList.add(new DragBean("", "", 0));
        }
        mDataList.clear();
        mDataList.addAll(newList);
        notifyDataSetChanged();
        if (mItemDragListener != null) {
            mItemDragListener.onItemDragEnd(mSelectedNum);
        }
        Log.d("summerho", "onItemClear " + mDataList.toString());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNameTv;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTv = itemView.findViewById(R.id.name_tv);
        }

        public void setData(DragBean bean) {
            if (bean == null) {
                return;
            }
            if (!TextUtils.isEmpty(bean.name)) {
                mNameTv.setText(bean.name);
                if (bean.type == 1) {
                    mNameTv.setTextColor(Color.parseColor("#0C121E"));
                } else {
                    mNameTv.setTextColor(Color.parseColor("#757E93"));
                }
            } else {
                itemView.setVisibility(View.GONE);
            }
        }
    }

    public class DividerViewHolder extends RecyclerView.ViewHolder {

        public DividerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemDragListener {
        void onItemDragEnd(int selectedNum);
    }
}
