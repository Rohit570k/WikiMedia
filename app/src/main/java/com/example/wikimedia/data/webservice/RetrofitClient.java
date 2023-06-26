package com.example.wikimedia.data.webservice;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "https://en.wikipedia.org/w/";


    private static RetrofitApi retrofitApi;
    private static Retrofit retrofit;
    //       private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .connectTimeout(1, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(25, TimeUnit.SECONDS)
//            .build();
    public static Retrofit retrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                     .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static RetrofitApi getRetrofitApiService() {
        if(retrofitApi == null ) {
            retrofitApi = retrofit().create(RetrofitApi.class);
        }
        return retrofitApi;
    }

}

