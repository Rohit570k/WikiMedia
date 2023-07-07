package com.example.wikimedia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikimedia.R;
import com.example.wikimedia.data.models.Articles.Pages;
import com.example.wikimedia.data.models.Category.Allcategory;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Allcategory> allcategoryList;
    private Context context;
    private EventsClickInterface eventsClickInterface;

    public CategoryAdapter(List<Allcategory> allcategoryList, Context context, EventsClickInterface eventsClickInterface) {
        this.allcategoryList = allcategoryList;
        this.context = context;
        this.eventsClickInterface = eventsClickInterface;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category,parent,false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Allcategory category = allcategoryList.get(position);
        holder.categoryName.setText(category.getCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsClickInterface.onItemClick(category.getCategory());
            }
        });
    }

    @Override
    public int getItemCount() {
        return allcategoryList.size();
    }

    public void submitList(List<Allcategory> allcategoryList){
        this.allcategoryList= allcategoryList;
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);

        }
    }

    public  interface EventsClickInterface{
        public void onItemClick(String category );
    }
}
