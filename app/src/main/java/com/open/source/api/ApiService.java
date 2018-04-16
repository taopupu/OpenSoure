package com.open.source.api;

import com.feimeng.fdroid.mvp.model.api.bean.Response;
import com.open.source.data.bean.AppVersion;

import retrofit2.http.GET;
import rx.Observable;

/**
 * API接口
 */
public interface ApiService {

    //版本更新
    @GET("json/version.json")
    Observable<Response<AppVersion>> version();
}
