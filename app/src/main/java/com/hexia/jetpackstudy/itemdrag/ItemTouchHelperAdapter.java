package com.hexia.jetpackstudy.itemdrag;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperAdapter {

    void onItemMove(RecyclerView.ViewHolder sourceHolder, RecyclerView.ViewHolder targetHolder, int fromPosition, int targetPosition);

    void onItemSelect(RecyclerView.ViewHolder holder);

    void onItemClear(RecyclerView.ViewHolder holder);

}
