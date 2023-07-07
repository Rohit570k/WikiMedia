package com.example.wikimedia.ui.images;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Category.CategoryResponse;
import com.example.wikimedia.data.repositories.ImagesRepo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImagesViewModel extends ViewModel {


    private ImagesRepo imagesRepo;
    public ArticleResponse articleResponse;
    private LiveData<ArticleResponse> articleResponseLiveData;
    public ImagesViewModel(Context context) {
       imagesRepo = new ImagesRepo(context);
        // Get the current date
        Date currentDate = new Date();

        // Format the date as "yyyy-MM-dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = dateFormat.format(currentDate);
        articleResponseLiveData= imagesRepo.getPOTDLiveData(date);
    }



    public LiveData<ArticleResponse> getPageOfTheDay(){

        return articleResponseLiveData;


    }


}