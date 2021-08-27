package com.hexia.jetpackstudy.http;

import static android.content.ContentValues.TAG;

import com.google.gson.Gson;
import com.hexia.jetpackstudy.model.NewsBean;
import com.hexia.jetpackstudy.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HttpDataParse {

    public static ArrayList<NewsBean> parseNewsList(String json, String id) {
        // Gson, JsonObject
        // 使用JsonObject解析方式: 如果遇到{},就是JsonObject;如果遇到[], 就是JsonArray
        if (json != null) {
            JSONObject jsonObject;
            ArrayList<NewsBean> list = new ArrayList<>();
            try {
                jsonObject = new JSONObject(json);
                JSONArray array = jsonObject.getJSONArray(id);
                Gson gson = new Gson();
                for (int i = 0; i < array.length(); i++) {
                    String js = array.get(i).toString();
                    NewsBean bean = gson.fromJson(js, NewsBean.class);
                    list.add(bean);
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtils.d(TAG, "parseJson: 数据解析错误");
            }
        } else {
            LogUtils.d(TAG, "parseData: 没有数据");
        }
        return null;
    }
}
