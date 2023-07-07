package com.example.wikimedia.ui.article;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.repositories.ArticleRepo;

public class ArticleViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final ArticleRepo articleRepo;
    private LiveData<ArticleResponse> articleResponseLiveData;

    public ArticleViewModel(Application application) {
        this.articleRepo =  new ArticleRepo();
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<ArticleResponse> getRandomArticle(){
        return articleResponseLiveData;
    }
    public void apicallgetArticles(String grncontinue){
        articleResponseLiveData= articleRepo.getRandomArticleLiveData(grncontinue);
    }

    public LiveData<ArticleResponse> getArticleExtracts(String title){
        return articleRepo.getArticleExtractLiveData(title);
    }

    public LiveData<String> getText() {
        return mText;
    }
}