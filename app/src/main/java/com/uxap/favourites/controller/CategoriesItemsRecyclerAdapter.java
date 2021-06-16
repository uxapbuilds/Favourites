package com.uxap.favourites.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uxap.favourites.R;
import com.uxap.favourites.interfaces.CategoryDataListener;
import com.uxap.favourites.view.CategoryItemsListRecyclerViewHolder;

import java.util.ArrayList;

public class CategoriesItemsRecyclerAdapter extends RecyclerView.Adapter<CategoryItemsListRecyclerViewHolder> {
    private ArrayList<String> categoryItems;
    private CategoryDataListener categoryItemsListener;
    public CategoriesItemsRecyclerAdapter(ArrayList<String> categoryItems, CategoryDataListener categoryItemsListener) {
        this.categoryItems = categoryItems;
        this.categoryItemsListener = categoryItemsListener;
    }

    @NonNull
    @Override
    public CategoryItemsListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_items_view_layout, parent, false);

        return new CategoryItemsListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemsListRecyclerViewHolder holder, int position) {
        holder.getCategoryItemTitleText().setText((position+1)+". "+categoryItems.get(position));
        holder.getCardCategoryItem().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                categoryItemsListener.cardIsLongPressed(categoryItems.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public void notifyRecycler(){
//        if(i == 1) {
//            notifyItemInserted(categoryItems.size() - 1);
//        }else if(i == 0){
//            notifyDataSetChanged();
//        }
//        notifyItemRangeRemoved(0, categoryItems.size());
//        notifyItemRangeInserted(0, categoryItems.size());
        notifyDataSetChanged();
    }
}
