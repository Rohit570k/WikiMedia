package com.example.wikimedia.ui.category;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.wikimedia.data.models.Category.CategoryResponse;
import com.example.wikimedia.data.repositories.CategoryRepo;

import java.util.Map;

import kotlin.jvm.functions.Function1;

public class CategoryViewModel extends AndroidViewModel {


   private  final String TAG ="categoryVM";
    private CategoryRepo categoryRepo;
    private String  searchPrefix = "";

    public CategoryResponse categoryResponse;
    private int car = 0;
    private LiveData<CategoryResponse> categoryResponseLiveData;

    public CategoryViewModel(Application application) {
        super(application);
        this.categoryRepo= new CategoryRepo();

    }
    // Setter method to update the search query
    public void setSearchPrefix(String prefix) {
        searchPrefix =prefix;
        Log.i(TAG, "setSearchPrefix: ");
        apicallgetCategoryList(null);
    }
    public  int getCar(){
        car++;
        return car;
    }

     public LiveData<CategoryResponse> getCategoryList(){
        return categoryResponseLiveData;
     }


    public void apicallgetCategoryList(String continueParam){
        categoryResponseLiveData= categoryRepo.getCategoryListLiveData(searchPrefix,continueParam);

     }




}