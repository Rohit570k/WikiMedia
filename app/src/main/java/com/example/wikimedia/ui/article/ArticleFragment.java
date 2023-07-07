package com.example.wikimedia.ui.article;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikimedia.adapters.ArticlesAdapter;
import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Articles.Pages;
import com.example.wikimedia.databinding.FragmentArticleBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ArticleFragment extends Fragment {

    private final static String TAG = "ArticleFragments";
    private FragmentArticleBinding binding;

    private ArticleViewModel articleViewModel;
    private ArticlesAdapter adapter;
    private List<Pages> pagesDataList;
    private String grncontinue = null;
    private boolean isScrolling = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         articleViewModel =
                new ViewModelProvider(this).get(ArticleViewModel.class);

        binding = FragmentArticleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        pagesDataList = new ArrayList<>();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Fetch randomartice
        setUpEventRecyclerView();
        binding.progressbar.setVisibility(View.VISIBLE);
        articleViewModel.apicallgetArticles(null);
        getRandomArticle();

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
                LinearLayoutManager linearLayoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int visibleItem = linearLayoutManager.getChildCount();
                int scrolledOut = linearLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && visibleItem+scrolledOut==totalItem){
                    isScrolling = false;
                    if(grncontinue==null) return;//means it reached the last page
                    binding.progressbar.setVisibility(View.VISIBLE);
                    articleViewModel.apicallgetArticles(grncontinue);
                    getRandomArticle();
                }

            }
        });


    }


    public void getRandomArticle(){
        articleViewModel.getRandomArticle().observe(getViewLifecycleOwner(), new Observer<ArticleResponse>() {
            @Override
            public void onChanged(ArticleResponse articleResponse) {

                if(articleResponse.get_continue()!=null){
                    grncontinue=articleResponse.get_continue().getGrncontinue();
                }else{
                    grncontinue=null;
                }
                Map<String, Pages> map = articleResponse.getQuery().getPages();

                map.forEach((key, page) -> {
                    //  Log.i(TAG, "ArticlesList: "+ key+"= "+page.getRevisions().get(0).getContent());
                    // Network call  for getting  extracts
                    getExtractFromTitle(page,page.getTitle());
                    Log.i(TAG, "onChanged: "+"extracts");
                });
//                adapter.notifyDataSetChanged();
                binding.progressbar.setVisibility(View.GONE);


            }

        });

    }
    private void getExtractFromTitle(Pages page, String title) {

        articleViewModel.getArticleExtracts(title).observe(getViewLifecycleOwner(), articleResponse -> {
            Map<String, Pages> map = articleResponse.getQuery().getPages();
            //Get first value here only value is there
                Map.Entry<String,Pages> entry = map.entrySet().iterator().next();
                String key = entry.getKey();
                Pages value = entry.getValue();


            // Update the extracts field of the Pages object
            page.setExtracts(value.getExtracts());
            Log.i(TAG, "getExtractFromTitle: "+ value.getExtracts());
            // Add the updated Pages object to the list
            pagesDataList.add(page);
            adapter.notifyDataSetChanged();
            Log.d(TAG, "onViewCreated: "+"++++++++++++++++++++++++++++++++++++++++++");


        });

    }


    private void setUpEventRecyclerView() {
        adapter=new ArticlesAdapter(pagesDataList,getContext());
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}