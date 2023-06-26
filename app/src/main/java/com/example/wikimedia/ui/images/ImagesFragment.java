package com.example.wikimedia.ui.images;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.wikimedia.adapters.ArticlesAdapter;
import com.example.wikimedia.data.models.Articles.ArticleResponse;
import com.example.wikimedia.data.models.Articles.Pages;
import com.example.wikimedia.databinding.FragmentArticleBinding;
import com.example.wikimedia.databinding.FragmentImagesBinding;
import com.example.wikimedia.ui.article.ArticleViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ImagesFragment extends Fragment {

    private FragmentImagesBinding binding;

    private final static String TAG = "ImagesFragment";

    private ImagesViewModel  imagesViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        imagesViewModel =
                new ViewModelProvider(this, new ViewModelProvider.Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                        return (T) new ImagesViewModel(getActivity().getApplicationContext());
                    }
                }).get(ImagesViewModel.class);

        binding = FragmentImagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //String  date = "2023-06-26";

        // Get the current date
        Date currentDate = new Date();

        // Format the date as "yyyy-MM-dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = dateFormat.format(currentDate);

        imagesViewModel.getPageOfTheDay(date).observe(getViewLifecycleOwner(), new Observer<ArticleResponse>() {
            @Override
            public void onChanged(ArticleResponse response) {

                Map<String, Pages> map = response.getQuery().getPages();
                Toast.makeText(getContext(), "dbsize: "+map.size(), Toast.LENGTH_SHORT).show();
                //Get first value here only value is there
                Map.Entry<String, Pages> entry = map.entrySet().iterator().next();
                String key = entry.getKey();
                Pages page = entry.getValue();


                String imageUrl = page.getThumbnail().getSource();
                Glide.with(binding.imageView).load(imageUrl).into(binding.imageView);
                binding.potd.setText("Picture of the Day: \n "+page.getTitle());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}