package com.hexia.jetpackstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mNewsBtn = findViewById(R.id.news_btn);
        mNewsBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        });
        Button dragBtn = findViewById(R.id.drag_btn);
        dragBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, DragActivity.class);
            startActivity(intent);
        });
        Button dragBtn2 = findViewById(R.id.drag_btn2);
        dragBtn2.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, DragActivity.class);
            startActivity(intent);
        });
    }
}