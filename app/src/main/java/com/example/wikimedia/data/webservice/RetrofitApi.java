package com.example.wikimedia.data.webservice;

import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Category.CategoryResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitApi {



    @GET("api.php?")
    Observable<Response<ArticleResponse>> getArticle(
//            @Query("origin") String origin,
            @Query("format") String format,
            @Query("action") String action,
            @Query("generator") String generator,
            @Query("grnnamespace") int grnnamespace,
            @Query("prop") String prop,
//            @Query("exchars") int exchars,
//            @Query("explaintext") boolean explaintext,
            @Query("rvprop") String rvprop,
            @Query("grnlimit") int grnlimit,
            @Query("pithumbsize") int pithumbsize,
            @Query("grncontinue") String continueParams
    );

    @GET("api.php?")
    Observable<Response<ArticleResponse>> getArticleExtract(
            @Query("format") String format,
            @Query("action") String action,
            @Query("prop") String prop,
            @Query("exchars") int exchars,
            @Query("explaintext") boolean explaintext,
            @Query("titles") String titles
//            @Query("exintro") boolean exintro,
//            @Query("exlimit") int exlimit
    );


    //https://en.wikipedia.org/w/api.php?format=json&action=query&list=allcategories&acprefix=List%20of&formatversion=2
    /**
     * Category
     */
    @GET("api.php?")
    Observable<Response<CategoryResponse>> getCategoryList(
            @Query("format") String format,
            @Query("action") String action,
            @Query("list") String list,
            @Query("acprefix") String acprefix,
            @Query("formatversion") int formatversion,
            @Query("accontinue") String continueParams
    );

    //https://en.wikipedia.org/w/api.php?format=json&action=query&generator=categorymembers&
    // prop=extracts%7Cpageimages&exintro=&explaintext=&gcmtitle=Category:Indian%20film%20industries
    //gcmcontinue=page|502a423a40042e3a4432422a0306502a423a40042e3a4432422a011d01dcc1dcc0dc0f|10749933
    @GET("api.php?")
    Observable<Response<ArticleResponse>> getCategoryMembersArticle(
            @Query("format") String format,
            @Query("action") String action,
            @Query("generator") String generator,
            @Query("prop") String prop,
            @Query("exchars") int exchars,
            @Query("exintro") boolean exintro,
            @Query("explaintext") boolean explaintext,
            @Query("pithumbsize") int pithumbsize,
            @Query("gcmtitle") String gcmtitle,
            @Query("gcmcontinue") String continueParam
    );

    //https://en.wikipedia.org/w/api.php?format=json&action=query&generator=images&prop=imageinfo|pageimages
    // &pithumbsize=500&iiprop=url&titles=Template:POTD/2023-06-26

    @GET("api.php?")
    Observable<Response<ArticleResponse>> pictureOFTheDay(
            @Query("format") String format,
            @Query("action") String action,
            @Query("generator") String generator,
            @Query("prop") String prop,
            @Query("pithumbsize") int pithumbsize,
            @Query("titles") String titles
    );

}
