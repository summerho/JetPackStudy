package com.hexia.jetpackstudy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hexia.jetpackstudy.model.NewsBean;
import com.hexia.jetpackstudy.paging.NewsAdapter;
import com.hexia.jetpackstudy.paging.NewsViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class MainActivity extends AppCompatActivity {

    private RefreshLayout mRefreshLayout;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableLoadMore(true);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.news_item_divider, null));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        NewsAdapter adapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(adapter);
        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.mNewsPagedList.observe(this, new Observer<PagedList<NewsBean>>() {
            @Override
            public void onChanged(PagedList<NewsBean> newsBeans) {
                adapter.submitList(newsBeans);
            }
        });
        mRefreshLayout.setOnRefreshListener(refreshLayout -> newsViewModel.invalidateDataSource());
    }
}