package com.hexia.jetpackstudy.itemdrag;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;

public class DefaultItemTouchHelper extends ItemTouchHelper {

    public DefaultItemTouchHelper(@NonNull Callback callback) {
        super(callback);
    }
}
