package com.example.wikimedia.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wikimedia.adapters.ArticlesAdapter;
import com.example.wikimedia.adapters.CategoryAdapter;
import com.example.wikimedia.data.models.Category.Allcategory;
import com.example.wikimedia.data.models.Category.CategoryResponse;
import com.example.wikimedia.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment implements CategoryAdapter.EventsClickInterface {

    private FragmentCategoryBinding binding;
    private final static String TAG = "CategoryFragments";
    private CategoryAdapter adapter;
  private  CategoryViewModel categoryViewModel;
  private List<Allcategory> allcategoryList;
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

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                apicallgetCategories(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void apicallgetCategories(String prefix) {
        categoryViewModel.getCategoryList(prefix).observe(getViewLifecycleOwner(), new Observer<CategoryResponse>() {
            @Override
            public void onChanged(CategoryResponse response) {
                List<Allcategory> categoryList=response.getQuery().getAllcategories();
               if(!categoryList.isEmpty()){
                   allcategoryList.clear();
                   allcategoryList.addAll(categoryList);
                   setUpEventRecyclerView();
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
        adapter.notifyDataSetChanged();
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