package com.open.source.api;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * 以JSON数据方式请求
 * Created by feimeng turn_on 2017/3/10.
 */
public class JsonRequestBody {
    private Map<String, Object> mDataMap;

    private JsonRequestBody() {
        mDataMap = new HashMap<>();
    }

    public static JsonRequestBody getInstance() {
        return new JsonRequestBody();
    }

    public JsonRequestBody put(String key, Object value) {
        mDataMap.put(key, value);
        return this;
    }

    public RequestBody body() {
        String json = new Gson().toJson(mDataMap);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }
}
