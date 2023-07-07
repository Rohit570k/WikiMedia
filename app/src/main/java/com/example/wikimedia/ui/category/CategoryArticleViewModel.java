package com.example.wikimedia.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Category.CategoryResponse;
import com.example.wikimedia.data.repositories.ArticleRepo;
import com.example.wikimedia.data.repositories.CategoryRepo;

public class CategoryArticleViewModel extends ViewModel {

    private CategoryRepo categoryRepo;

    private LiveData<ArticleResponse> articleResponseLiveData;

    public CategoryArticleViewModel() {
        this.categoryRepo= new CategoryRepo();

    }

    public LiveData<ArticleResponse> getCategoryMemberData(){
        return articleResponseLiveData;
    }

    public void apicallgetArticleList(String category,String gcmcontinue){
        articleResponseLiveData= categoryRepo.getCategoryMembersArticle(category,gcmcontinue);
    }

}
