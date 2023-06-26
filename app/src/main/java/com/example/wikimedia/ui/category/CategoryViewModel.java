package com.example.wikimedia.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Category.CategoryResponse;
import com.example.wikimedia.data.repositories.CategoryRepo;

public class CategoryViewModel extends ViewModel {



    private CategoryRepo categoryRepo;

    public CategoryViewModel() {
        this.categoryRepo= new CategoryRepo();

    }

    public LiveData<CategoryResponse> getCategoryList(String prefix){
        return categoryRepo.getCategoryListLiveData(prefix);
    }



}