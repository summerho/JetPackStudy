package com.hexia.jetpackstudy;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hexia.jetpackstudy.itemdrag.ItemTouchHelperCallback;
import com.hexia.jetpackstudy.model.NewsBean;
import com.hexia.jetpackstudy.paging.NewsAdapter;
import com.hexia.jetpackstudy.paging.NewsViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

public class NewsActivity extends AppCompatActivity {

    private RefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_layout);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(true);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.news_item_divider, null));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        NewsAdapter adapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(adapter);
        ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.mNewsPagedList.observe(this, new Observer<PagedList<NewsBean>>() {
            @Override
            public void onChanged(PagedList<NewsBean> newsBeans) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                adapter.submitList(newsBeans);
            }
        });
        mRefreshLayout.setOnRefreshListener(refreshLayout -> newsViewModel.invalidateDataSource());
    }
}
