package com.yingwumeijia.android.ywmj.client.utils.net.retrofit;


import android.content.Context;

import com.rx.android.jamspeedlibrary.utils.Preconditions;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

/**
 * Created by Jam
 * on 2016/5/24 10:33
 * Describe:
 */
public class RetrofitBuilder {

    private String baseUrl;
    private Retrofit mRetrofit;

    private OkHttpClient client;


    final Integer sa = 1;


    /**
     * 构造函数私有化
     *
     * @author Jam
     * create at 2016/5/24 10:49
     */
    private RetrofitBuilder() {

    }

    //make this class thread safe singleton
    private static class SingletonHolder {
        private static final RetrofitBuilder INSTANCE = new RetrofitBuilder();
    }

    public static RetrofitBuilder get() {
        return SingletonHolder.INSTANCE;
    }


    public Retrofit retrofit() {
        Preconditions.checkNotNull(baseUrl, "Base URL required.");
        if (mRetrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            mRetrofit = builder.baseUrl(baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
        }

        return mRetrofit;
    }


    /**
     * 创建给定的 Api Service
     */
    public <T> T create(Class<T> tClass) {
        return get().retrofit().create(tClass);
    }


    public static class Builder {
        private String baseUrl;
        private Context context;
        private OkHttpClient mClient;

        public RetrofitBuilder build() {
            Preconditions.checkNotNull(baseUrl, "Base URL required.");
            ensureSaneDefaults();

            RetrofitBuilder retrofitBuilder = get();
            retrofitBuilder.baseUrl = baseUrl;
            retrofitBuilder.client = mClient;

            return retrofitBuilder;
        }

        private void ensureSaneDefaults() {
            if (mClient == null) {
                mClient = defaultClient();
            }
        }

        private OkHttpClient defaultClient() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Timber.d(message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
//            builder.addInterceptor(new DefaultHeaderInterceptor(AccountManager.getInstance(), context));
            builder.cookieJar(new CookieManger(context));
            return builder.build();
        }

        public Builder client(OkHttpClient client) {
            mClient = client;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            Preconditions.checkNotNull(baseUrl, "baseUrl == null");
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

    }

}
