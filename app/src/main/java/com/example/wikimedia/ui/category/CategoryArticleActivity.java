package com.example.wikimedia.ui.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.wikimedia.adapters.ArticlesAdapter;
import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Articles.Pages;
import com.example.wikimedia.databinding.ActivityCategoryArticleBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class CategoryArticleActivity extends AppCompatActivity {

    private ActivityCategoryArticleBinding binding;
    private CategoryArticleViewModel categoryArticleViewModel;
    private final static String TAG = "CategoryActivity";

    private  ArticlesAdapter adapter;
    private List<Pages> pagesList;

    private String categoryName;
    private String gcmcontinue = null;
    private boolean isScrolling=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryArticleViewModel = new ViewModelProvider(this).get(CategoryArticleViewModel.class);

         categoryName = getIntent().getStringExtra("Category");

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle(categoryName);
        actionBar.setDisplayHomeAsUpEnabled(true);

            pagesList = new ArrayList<>();

        setUpEventRecyclerView();

        binding.progressbar.setVisibility(View.VISIBLE);
        categoryArticleViewModel.apicallgetArticleList(categoryName,null);
        getCategoryMemberData();


        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling=true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager=(LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int currentItem = linearLayoutManager.getChildCount();
                int scrolledOut = linearLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && scrolledOut+currentItem==totalItem){
                    isScrolling=false;
                    if(gcmcontinue==null) return;
                    binding.progressbar.setVisibility(View.VISIBLE);
                    Log.i(TAG, "onScrolled: ");
                    categoryArticleViewModel.apicallgetArticleList(categoryName,gcmcontinue);
                    getCategoryMemberData();
                }
            }
        });




    }



    private void getCategoryMemberData() {

        categoryArticleViewModel.getCategoryMemberData().observe(this, new Observer<ArticleResponse>() {
            @Override
            public void onChanged(ArticleResponse response) {
                binding.progressbar.setVisibility(View.GONE);

                if(response.get_continue()!=null){
                    gcmcontinue=response.get_continue().getGcmcontinue();
                }else{
                    gcmcontinue=null;
                }

                if(response.getQuery()!=null) {
                    binding.emptyView.setVisibility(View.GONE);
                    Map<String, Pages> map = response.getQuery().getPages();
                    map.forEach((key, page) -> {
                        //  Log.i(TAG, "ArticlesList: "+ key+"= "+page.getRevisions().get(0).getContent());
                        pagesList.add(page);
                    });
                    adapter.notifyDataSetChanged();

                }else{
                    binding.emptyView.setVisibility(View.VISIBLE);
                    Toast.makeText(CategoryArticleActivity.this, "No article in this category", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpEventRecyclerView() {
        adapter=new ArticlesAdapter(pagesList,this);
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}