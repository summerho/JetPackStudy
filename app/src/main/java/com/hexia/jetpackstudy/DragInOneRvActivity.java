package com.hexia.jetpackstudy;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hexia.jetpackstudy.itemdrag.DefaultItemCallback;
import com.hexia.jetpackstudy.itemdrag.DefaultItemTouchHelper;
import com.hexia.jetpackstudy.model.DragBean;

import java.util.ArrayList;
import java.util.List;

public class DragInOneRvActivity extends AppCompatActivity {

    private final List<DragBean> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_in_one_rv_layout);
        RecyclerView recyclerView = findViewById(R.id.drag_rv);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerSpace itemDecoration = new RecyclerSpace(dp2px(7));
        recyclerView.addItemDecoration(itemDecoration);
        initData();
        DragInOneRvAdapter adapter = new DragInOneRvAdapter(this, mDataList);
        recyclerView.setAdapter(adapter);
        DefaultItemCallback callback = new DefaultItemCallback(adapter);
        DefaultItemTouchHelper helper = new DefaultItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = adapter.getItemViewType(position);
                if (itemViewType == DragInOneRvAdapter.TYPE_DIVIDER) {
                    return layoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        adapter.setOnItemDragListener(selectedNum -> {

        });
    }

    private void initData() {
        String[] stringArray = getResources().getStringArray(R.array.optional_zhushu);
        for (int i = 0; i < stringArray.length; i++) {
            DragBean bean = new DragBean();
            String s = stringArray[i];
            String[] split = s.split(",");
            bean.name = split[0];
            bean.code = split[1];
            bean.type = Integer.parseInt(split[2]);
            mDataList.add(bean);
        }
    }

    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
