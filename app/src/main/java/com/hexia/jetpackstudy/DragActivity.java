package com.hexia.jetpackstudy;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hexia.jetpackstudy.itemdrag.DefaultItemCallback;
import com.hexia.jetpackstudy.itemdrag.DefaultItemTouchHelper;
import com.hexia.jetpackstudy.model.DragBean;

import java.util.ArrayList;
import java.util.List;

public class DragActivity extends AppCompatActivity implements View.OnDragListener {

    public static boolean isFromBottomRv = false;

    private RecyclerView mTopRecyclerView;

    private RecyclerView mBottomRecyclerView;

    private DragAdapter mTopAdapter;

    private DragAdapter mBottomAdapter;

    private final List<DragBean> mTopList = new ArrayList<>();

    private final List<DragBean> mBottomList = new ArrayList<>();

    private DragBean mMoveBean;

    private int newContactPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_layout);
        mTopRecyclerView = findViewById(R.id.top_rv);
        mBottomRecyclerView = findViewById(R.id.bottom_rv);
        GridLayoutManager topLayoutManager = new GridLayoutManager(this, 3);
        GridLayoutManager bottomLayoutManager = new GridLayoutManager(this, 3);
        mTopRecyclerView.setLayoutManager(topLayoutManager);
        mBottomRecyclerView.setLayoutManager(bottomLayoutManager);
        RecyclerSpace itemDecoration = new RecyclerSpace(dp2px(7));
        mTopRecyclerView.addItemDecoration(itemDecoration);
        mBottomRecyclerView.addItemDecoration(itemDecoration);
        initData();
        mTopAdapter = new DragAdapter(this, mTopList, R.layout.top_item_layout);
        mBottomAdapter = new DragAdapter(this, mBottomList, R.layout.bottom_item_layout);
        mTopRecyclerView.setAdapter(mTopAdapter);
        mBottomRecyclerView.setAdapter(mBottomAdapter);
        mTopRecyclerView.setOnDragListener(this);
        mBottomRecyclerView.setOnDragListener(new MyDragInsideRcvListener());
        DefaultItemCallback callback = new DefaultItemCallback(mTopAdapter);
        DefaultItemTouchHelper helper = new DefaultItemTouchHelper(callback);
        helper.attachToRecyclerView(mTopRecyclerView);
    }

    private void initData() {
        String[] stringArray = getResources().getStringArray(R.array.optional_zhushu);
        for (int i = 0; i < 6; i++) {
            DragBean bean = new DragBean();
            String s = stringArray[i];
            String[] split = s.split(",");
            bean.name = split[0];
            bean.code = split[1];
            mTopList.add(bean);
        }
        for (int i = 6; i < 9; i++) {
            DragBean bean = new DragBean();
            String s = stringArray[i];
            String[] split = s.split(",");
            bean.name = split[0];
            bean.code = split[1];
            mBottomList.add(bean);
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        View selectedView = (View) dragEvent.getLocalState();
        RecyclerView rcvSelected = (RecyclerView) view;
        try {
            int currentPosition = mBottomRecyclerView.getChildAdapterPosition(selectedView);
            if (currentPosition != -1) {
                mMoveBean = mBottomList.get(currentPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:
                View onTopOf = rcvSelected.findChildViewUnder(dragEvent.getX(), dragEvent.getY());
                newContactPosition = rcvSelected.getChildAdapterPosition(onTopOf);
                break;
            case DragEvent.ACTION_DROP:
                if (isFromBottomRv) {
                    mTopList.add(mMoveBean);
                    mBottomList.remove(mMoveBean);
//                    mBottomAdapter.notifyItemRemoved(-1);
                    mTopAdapter.notifyDataSetChanged();
                    mBottomAdapter.notifyDataSetChanged();
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                selectedView.setVisibility(View.VISIBLE);
                if (newContactPosition != -1) {
                    rcvSelected.scrollToPosition(newContactPosition);
                    newContactPosition = -1;
                } else {
                    rcvSelected.scrollToPosition(0);
                }
                break;
            default:
                break;
        }
        return true;
    }

    class MyDragInsideRcvListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            RecyclerView rcv = (RecyclerView) v;
            View selectedView = (View) event.getLocalState();
            try {
                int currentPosition = rcv.getChildAdapterPosition(selectedView);
                if (currentPosition != -1) {
                    mMoveBean = mTopList.get(currentPosition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_LOCATION:
                    View onTopOf = rcv.findChildViewUnder(event.getX(), event.getY());
                    newContactPosition = rcv.getChildAdapterPosition(onTopOf);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    selectedView.setVisibility(View.VISIBLE);
                    if (newContactPosition != -1) {
                        rcv.scrollToPosition(newContactPosition);
                        newContactPosition = -1;
                    } else {
                        rcv.scrollToPosition(0);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    if (!isFromBottomRv) {
                        mBottomList.add(mMoveBean);
                        mTopList.remove(mMoveBean);
//                        mBottomAdapter.notifyItemInserted(-1);
                        mTopAdapter.notifyDataSetChanged();
                        mBottomAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
