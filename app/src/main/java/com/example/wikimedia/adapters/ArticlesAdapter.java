package com.example.wikimedia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wikimedia.R;
import com.example.wikimedia.data.models.Articles.Pages;
import com.example.wikimedia.utils.UtilService;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Pages>  pagesDataList;
    private Context context;

    public ArticlesAdapter(List<Pages> pagesDataList, Context context) {
        this.pagesDataList = pagesDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article,parent,false);
        return new ArticlesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapter.ViewHolder holder, int position) {
          Pages pagedata = pagesDataList.get(position);
          holder.title.setText(pagedata.getTitle());
          holder.description.setText(pagedata.getExtracts());
          if(pagedata.getThumbnail()!=null) {
              holder.image.setAlpha(1.0f);
              String imageUrl = pagedata.getThumbnail().getSource();
              Glide.with(holder.image).load(imageUrl).into(holder.image);
          }else{
              holder.image.setAlpha(0.3f);
          }

        // Set OnClickListener for the card
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://en.wikipedia.org/wiki/"+pagedata.getTitle();
                UtilService.openUrlInBrowser(context,url);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pagesDataList.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder {
        TextView title , description;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.categoryName);
            description = itemView.findViewById(R.id.article_description);
            image = itemView.findViewById(R.id.article_image);
        }
    }
}
