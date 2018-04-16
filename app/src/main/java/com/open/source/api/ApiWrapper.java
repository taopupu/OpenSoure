package com.open.source.api;

import android.support.annotation.NonNull;

import com.feimeng.fdroid.config.FDConfig;
import com.feimeng.fdroid.mvp.model.api.FDApi;
import com.feimeng.fdroid.mvp.model.api.ResponseCodeInterceptorListener;
import com.open.source.data.bean.AppVersion;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import rx.Observable;


/**
 * Api服务包装类
 */
public class ApiWrapper extends FDApi {
    private static ApiWrapper mInstance;
    private ApiService mApi;

    public static ApiWrapper getInstance() {
        if (mInstance == null)
            synchronized (ApiWrapper.class) {
                if (mInstance == null) mInstance = new ApiWrapper();
            }
        return mInstance;
    }

    private ApiWrapper() {
        setResponseCodeListener(new ResponseCodeInterceptorListener() {
            @Override
            public boolean onResponse(int code) {
                if (code == 401) {
//                    Self.clear();
//                    JPushInterface.setAlias(BaseApp.baseApp.getApplicationContext(), 1, "");
//                    BaseActivity.toHomeActivity();
                    return true;
                }
                return false;
            }
        }, 401, 500);
//        addHttpMockData("mine/bar/order/list", "{\"code\":200,\"info\":\"成功\",\"data\":{\"pageNum\":1,\"pageSize\":10,\"list\":[{\"id\":\"123\",\"bar\":{\"id\":\"10\",\"name\":\"酷炫网吧\"},\"goods\":[{\"id\":\"1\",\"pictureUrl\":\"http://img3.3lian.com/2006/017/24/20051191942183.jpg\",\"name\":\"百事可乐\",\"size\":\"瓶装\",\"num\":\"1\",\"price\":\"3.00\"},{\"id\":\"1\",\"pictureUrl\":\"http://img3.3lian.com/2006/017/24/20051191942183.jpg\",\"name\":\"百事可乐\",\"size\":\"瓶装\",\"num\":\"1\",\"price\":\"3.00\"}],\"seat\":{\"num\":\"2\",\"duration\":\"1\",\"region\":{\"id\":\"123\",\"name\":\"商务区\",\"price\":\"5.00\"},\"ticket\":\"5201314\"},\"type\":\"goods\",\"totalPrice\":\"11.00\",\"status\":\"1\"}]}}");
        mApi = getRetrofit("http://testitpl.cnsiluhui.com/apidjapp/").create(ApiService.class);
//        mApi = getRetrofit("http://192.168.3.114:8061/apidjapp/").create(ApiService.class);
    }

    @Override
    protected OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logInterceptor = null;
        if (FDConfig.SHOW_HTTP_LOG) {
            logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder().connectTimeout((long) FDConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS).writeTimeout((long) FDConfig.WRITE_TIMEOUT, TimeUnit.SECONDS).readTimeout((long) FDConfig.READ_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
//                if (Self.isLogin()) {
//                    builder.addHeader("Token", Self.get().getToken());
//                }
                return chain.proceed(builder.build());
            }
        });
        if (logInterceptor != null) {
            clientBuilder.addInterceptor(logInterceptor);
        }
//        return startMock(clientBuilder);
        return clientBuilder.build();
    }

    //版本更新
    public Observable<AppVersion> version() {
        return mApi.version().compose(this.<AppVersion>applySchedulersFixed());
    }


}
