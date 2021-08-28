package com.hexia.jetpackstudy.paging;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.hexia.jetpackstudy.http.HttpDataParse;
import com.hexia.jetpackstudy.http.OkHttpUtil;
import com.hexia.jetpackstudy.model.NewsBean;
import com.hexia.jetpackstudy.utils.Api;
import com.hexia.jetpackstudy.utils.LogUtils;

import java.util.List;

import okhttp3.Request;

public class NewsDataSource extends PageKeyedDataSource<Integer, NewsBean> {

    private static final int FIRST_PAGE_NUM = 0;

    private void requestNews(int pageNum, Callback<List<NewsBean>> callback) {
        String url = Api.CommonUrl + Api.tid + "/" + pageNum + Api.endUrl;
        OkHttpUtil.getDataAsync(url, new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onResponse(String response) {
                List<NewsBean> newsList = HttpDataParse.parseNewsList(response, Api.tid);
                callback.onSuccess(newsList);
            }

            @Override
            public void onError(Request request, Exception e) {
                callback.onFailed(e.getMessage());
            }
        });
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer,
            NewsBean> callback) {
        requestNews(FIRST_PAGE_NUM, new Callback<>() {
            @Override
            public void onSuccess(List<NewsBean> list) {
                callback.onResult(list, null, FIRST_PAGE_NUM + 20);
            }

            @Override
            public void onFailed(String msg) {
                LogUtils.e(TAG, msg);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NewsBean> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NewsBean> callback) {
        requestNews(params.key, new Callback<>() {
            @Override
            public void onSuccess(List<NewsBean> list) {
                Integer nextKey = params.key + 20;
                callback.onResult(list, nextKey);
            }

            @Override
            public void onFailed(String msg) {
                LogUtils.e(TAG, msg);
            }
        });
    }

    public interface Callback<R> {
        void onSuccess(R object);

        void onFailed(String msg);
    }
}
