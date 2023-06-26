package com.example.wikimedia.data.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.wikimedia.data.models.APIError;
import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Error;
import com.example.wikimedia.data.webservice.RetrofitApi;
import com.example.wikimedia.data.webservice.RetrofitClient;
import com.example.wikimedia.utils.UtilService;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class ImagesRepo {

    private RetrofitApi apiService;

    private final  String  TAG = "ImagesRepo";

    public ImagesRepo() {
        this.apiService =  RetrofitClient.getRetrofitApiService();
    }


    public MutableLiveData<ArticleResponse> getPOTDLiveData(String date){
        MutableLiveData<ArticleResponse> data = new MutableLiveData<ArticleResponse>();


        Observable<Response<ArticleResponse>> observable= apiService.pictureOFTheDay("json","query","images",
                "pageimages", 500,"Template:POTD/"+date);

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
                            data.setValue(response.body());
                        } else {
                            Log.d(TAG,"error message"+" Error occured in response"+response.code());
                            // parse the response body …
                            APIError error = UtilService.parseError(response);
                            Error err=null;
                            if(error!=null){
                                err = error.getError();
                                Log.d(TAG, "onNext: "+error+  err);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: "+ e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return  data;
    }

}
