package com.hexia.jetpackstudy.itemdrag;

public interface IItemDragCallback {

    /**
     * 拖拽中
     */
    void onItemDragging(int from, int to);

    /**
     * 拖拽结束
     */
    void onItemDragged();
}
