package com.hexia.jetpackstudy.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.hexia.jetpackstudy.model.NewsBean;

public class NewsDataSourceFactory extends DataSource.Factory<Integer, NewsBean> {

    private final MutableLiveData<NewsDataSource> mLiveDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, NewsBean> create() {
        NewsDataSource dataSource = new NewsDataSource();
        mLiveDataSource.postValue(dataSource);
        return dataSource;
    }
}
