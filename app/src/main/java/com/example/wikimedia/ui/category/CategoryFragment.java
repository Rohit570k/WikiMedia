package com.example.wikimedia.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikimedia.adapters.CategoryAdapter;
import com.example.wikimedia.data.models.Category.Allcategory;
import com.example.wikimedia.data.models.Category.CategoryResponse;
import com.example.wikimedia.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategoryFragment extends Fragment implements CategoryAdapter.EventsClickInterface {

    private FragmentCategoryBinding binding;
    private final static String TAG = "CategoryFragments";
    private CategoryAdapter adapter;
  private  CategoryViewModel categoryViewModel;
  private List<Allcategory> allcategoryList;

  private boolean isScrolling = false;
  private String  continueParam = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       categoryViewModel =
                new ViewModelProvider(this).get(CategoryViewModel.class);

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        allcategoryList = new ArrayList<>();

//        categoryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpEventRecyclerView();


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                allcategoryList.clear();
                binding.progressbar.setVisibility(View.VISIBLE);
                performSearch(query);
                getCategoryList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true;

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int currentItem = linearLayoutManager.getChildCount();
                int scrolledOut = linearLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && scrolledOut+currentItem==totalItem){
                    isScrolling=false;
                    if(continueParam==null) return;
                    binding.progressbar.setVisibility(View.VISIBLE);
                    Log.i(TAG, "onScrolled: ");
                    categoryViewModel.apicallgetCategoryList(continueParam);
                    getCategoryList();
                }
            }
        });

    }

    // Method to perform the search based on the search query
    private void performSearch(String query) {
        // Update the search query in the ViewModel and invoke api call in viewmodel
        categoryViewModel.setSearchPrefix(query);
    }

    private void getCategoryList() {
        categoryViewModel.getCategoryList().observe(getViewLifecycleOwner(), new Observer<CategoryResponse>() {
            @Override
            public void onChanged(CategoryResponse response) {
                categoryViewModel.categoryResponse = response;
                binding.progressbar.setVisibility(View.GONE);

                if(response.getContinue()!=null){
                    continueParam=response.getContinue().getAccontinue();
                }else{
                    continueParam=null;
                }
                List<Allcategory> categoryList=response.getQuery().getAllcategories();
               if(!categoryList.isEmpty()){
                   int car = categoryViewModel.getCar();
                   Toast.makeText(getContext(), String.valueOf(car), Toast.LENGTH_SHORT).show();
                   allcategoryList.addAll(categoryList);

                   adapter.notifyDataSetChanged();


               }else{
                   Toast.makeText(getContext(), "No Category Related to prefix", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }


    private void setUpEventRecyclerView() {
       // Log.i(TAG, "setUpEventRecyclerView: "+allcategoryList.get(1).getCategory());
        adapter=new CategoryAdapter(allcategoryList,getContext(),this::onItemClick);
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(String category) {
        //Toast.makeText(requireContext(),category+" ",Toast.LENGTH_SHORT).show();
        Intent intent =new Intent(getContext(), CategoryArticleActivity.class);
        intent.putExtra("Category",  category);
        startActivity(intent);
    }
}