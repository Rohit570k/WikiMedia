package com.example.wikimedia.data.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.wikimedia.data.models.APIError;
import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Error;
import com.example.wikimedia.data.webservice.RetrofitApi;
import com.example.wikimedia.data.webservice.RetrofitClient;
import com.example.wikimedia.utils.UtilService;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class ArticleRepo {

    private RetrofitApi apiService;
    private CompositeDisposable compositeDisposable ;

    private final  String  TAG = "ArticleRepo";

    //Constructor
    public ArticleRepo(){
        this.apiService = RetrofitClient.getRetrofitApiService();
        this.compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<ArticleResponse>  getRandomArticleLiveData(){
        MutableLiveData<ArticleResponse> data = new MutableLiveData<ArticleResponse>();


        Observable<Response<ArticleResponse>> observable= apiService.getArticle("json","query","random",0,
                                "revisions|pageimages|images","content",10,300);

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

//                                    Toast.makeText(AllJobsActivity.this,error.getMsg()+" ",Toast.LENGTH_SHORT).show();

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





    public MutableLiveData<ArticleResponse>  getArticleExtractLiveData(String title){
        MutableLiveData<ArticleResponse> data = new MutableLiveData<ArticleResponse>();


        Observable<Response<ArticleResponse>> observable= apiService.getArticleExtract("json","query","extracts",1200,
                true,title);

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




//For Article
//  https://en.wikipedia.org/w/api.php?origin=*&format=json&action=query&generator=random&grnnamespace=0
// &prop=revisions%7Cimages%7Cextracts&exchars=1000&explaintext&rvprop=content&grnlimit=3"

//https://en.wikipedia.org/w/api.php?origin=*&format=json&action=query&generator=random&grnnamespace=0
// &prop=revisions|pageimages|images&rvprop=content&grnlimit=10&pithumbsize=500

//For Extarcts
//"https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exchars=1200&explaintext&titles=Adipurush"