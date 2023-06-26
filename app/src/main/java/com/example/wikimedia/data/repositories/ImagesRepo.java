package com.example.wikimedia.data.repositories;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.wikimedia.data.db.ImagesDatabase;
import com.example.wikimedia.data.models.APIError;
import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Articles.Pages;
import com.example.wikimedia.data.models.Articles.Query;
import com.example.wikimedia.data.models.Articles.Thumbnail;
import com.example.wikimedia.data.models.Error;
import com.example.wikimedia.data.webservice.RetrofitApi;
import com.example.wikimedia.data.webservice.RetrofitClient;
import com.example.wikimedia.utils.UtilService;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class ImagesRepo {

    private RetrofitApi apiService;

    private ImagesDatabase imagesDatabase;
    private final  String  TAG = "ImagesRepo";
    private Context context;

    public ImagesRepo(Context context) {
        this.context = context;
        imagesDatabase = ImagesDatabase.getInstance(context);
        this.apiService =  RetrofitClient.getRetrofitApiService();
    }


    public MutableLiveData<ArticleResponse> getPOTDLiveData(String date){
        MutableLiveData<ArticleResponse> data = new MutableLiveData<ArticleResponse>();

        if(UtilService.isInternetAvailable(context)) {

            Observable<Response<ArticleResponse>> observable = apiService.pictureOFTheDay("json", "query", "images",
                    "pageimages", 500, "Template:POTD/" + date);

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<ArticleResponse>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Response<ArticleResponse> response) {
                            if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                                Log.i(TAG, "onResponse:" + new Gson().toJson(response.body()));

                                ExecutorService executorService= Executors.newSingleThreadExecutor();
                                Handler handler = new Handler(Looper.getMainLooper());
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Save to database
                                imagesDatabase.pagesDao().deleteAllPages();
                                imagesDatabase.thumbnailDao().deleteAllThumbnails();
                                        Map<String, Pages> map = response.body().getQuery().getPages();
                                        Map.Entry<String, Pages> entry = map.entrySet().iterator().next();
                                        Pages page = entry.getValue();
                                        imagesDatabase.pagesDao().insert(page);
                                      //  imagesDatabase.thumbnailDao().insert(page.getThumbnail());
                                    }
                                });


                                //set to live data
                                data.setValue(response.body());
                            } else {
                                Log.d(TAG, "error message" + " Error occured in response" + response.code());
                                // parse the response body …
                                APIError error = UtilService.parseError(response);
                                Error err = null;
                                if (error != null) {
                                    err = error.getError();
                                    Log.d(TAG, "onNext: " + error + err);
                                }
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else{
            /**
             * If internet connection is not available
             * Pages stored in the db will be shown
             */
            ExecutorService executorService= Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    List<Pages> pages = imagesDatabase.pagesDao().getPages();
                    List<Thumbnail> thumbnails = imagesDatabase.thumbnailDao().getThumbnail();
                    Map<String ,Pages> map = new HashMap<>();
                    for(int i = 0 ; i< pages.size();i++){
                        String pageId = String.valueOf(pages.get(i).getPageid());
//                        pages.get(i).setThumbnail(thumbnails.get(i));
                        map.put(pageId, pages.get(i));
                    }
                    ArticleResponse articleResponse = new ArticleResponse();
                    Query query=new Query();
                    query.setPages(map);
                    articleResponse.setQuery(query);

                    //do after background execution is done- post execution
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            data.setValue(articleResponse);
                        }
                    });


                }
            });

        }

        return  data;
    }

}
