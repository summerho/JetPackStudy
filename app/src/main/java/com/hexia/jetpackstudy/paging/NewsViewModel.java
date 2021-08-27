package com.hexia.jetpackstudy.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.hexia.jetpackstudy.model.NewsBean;

public class NewsViewModel extends ViewModel {

    public LiveData<PagedList<NewsBean>> mNewsPagedList;

    public NewsViewModel() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .setPrefetchDistance(5)
                .setInitialLoadSizeHint(20)
                .build();
        mNewsPagedList = new LivePagedListBuilder<>(new NewsDataSourceFactory(), config).build();
    }

    public void invalidateDataSource() {
        PagedList<NewsBean> pagedList = mNewsPagedList.getValue();
        if (pagedList != null) {
            pagedList.getDataSource().invalidate();
        }
    }
}
