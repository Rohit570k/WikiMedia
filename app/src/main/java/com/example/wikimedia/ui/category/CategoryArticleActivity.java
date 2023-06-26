package com.example.wikimedia.ui.category;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wikimedia.R;
import com.example.wikimedia.adapters.ArticlesAdapter;
import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Articles.Pages;
import com.example.wikimedia.data.models.Category.Allcategory;
import com.example.wikimedia.data.models.Category.CategoryResponse;
import com.example.wikimedia.databinding.ActivityCategoryArticleBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryArticleActivity extends AppCompatActivity {

    private ActivityCategoryArticleBinding binding;
    private CategoryArticleViewModel categoryArticleViewModel;
    private final static String TAG = "CategoryActivity";

    private  ArticlesAdapter adapter;
    private List<Pages> pagesList;

    private String categoryName;
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

        apicallgetCategoryArticles();



    }

    private void apicallgetCategoryArticles() {
        categoryArticleViewModel.getCategoryMemberData(categoryName).observe(this, new Observer<ArticleResponse>() {
            @Override
            public void onChanged(ArticleResponse response) {

                if(response.getQuery()!=null) {
                    binding.emptyView.setVisibility(View.GONE);
                    pagesList.clear();
                    Map<String, Pages> map = response.getQuery().getPages();
                    map.forEach((key, page) -> {
                        //  Log.i(TAG, "ArticlesList: "+ key+"= "+page.getRevisions().get(0).getContent());
                        pagesList.add(page);
                    });
                    setUpEventRecyclerView();
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
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}