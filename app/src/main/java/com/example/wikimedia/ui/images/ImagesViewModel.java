package com.example.wikimedia.ui.images;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Category.CategoryResponse;
import com.example.wikimedia.data.repositories.ImagesRepo;

public class ImagesViewModel extends ViewModel {


    private ImagesRepo imagesRepo;
    public ImagesViewModel(Context context) {
       imagesRepo = new ImagesRepo(context);
    }

    public LiveData<ArticleResponse> getPageOfTheDay(String date){
        return imagesRepo.getPOTDLiveData(date);


    }


}