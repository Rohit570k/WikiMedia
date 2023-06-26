package com.example.wikimedia.ui.images;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Category.CategoryResponse;
import com.example.wikimedia.data.repositories.ImagesRepo;

public class ImagesViewModel extends ViewModel {


    private ImagesRepo imagesRepo;
    public ImagesViewModel() {
       imagesRepo = new ImagesRepo();
    }

    public LiveData<ArticleResponse> getPageOfTheDay(String date){
        return imagesRepo.getPOTDLiveData(date);


    }


}