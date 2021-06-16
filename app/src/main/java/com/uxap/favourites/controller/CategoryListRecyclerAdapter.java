package com.uxap.favourites.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uxap.favourites.R;
import com.uxap.favourites.interfaces.CategoryItemListener;
import com.uxap.favourites.model.CategoryDataManager;
import com.uxap.favourites.model.CategoryDataModel;
import com.uxap.favourites.view.CategoryListRecyclerViewHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryListRecyclerAdapter extends RecyclerView.Adapter<CategoryListRecyclerViewHolder> {

    private CategoryItemListener categoryItemListener;

    ArrayList<CategoryDataModel> categoryDataArrayList = new ArrayList<>();

    public CategoryListRecyclerAdapter(ArrayList<CategoryDataModel> categoryDataArray, CategoryItemListener categoryItemListener) {
        this.categoryDataArrayList = categoryDataArray;
        this.categoryItemListener = categoryItemListener;
//        getTempData(categoryDataArrayList);
    }

    @NonNull
    @Override
    public CategoryListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_category_view_layout, parent, false);
        return new CategoryListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListRecyclerViewHolder holder, int position) {
        holder.getCategoryTitle().setText(categoryDataArrayList.get(position).getCategoryTitle());
        holder.getCategoryItemCount().setText(categoryDataArrayList.get(position).getCategoryItems().size()+"");
        holder.getCategoryDateAdded().setText(categoryDataArrayList.get(position).getCategoryDateAdded());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryItemListener.categoryIsClicked(categoryDataArrayList.get(position));
            }
        });
        holder.getCardCategory().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                categoryItemListener.categoryIsLongPressed(categoryDataArrayList.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }

    public void addCategoryToDataArray(CategoryDataModel categoryDataModel) {
        categoryDataArrayList.add(categoryDataModel);
        notifyItemInserted(categoryDataArrayList.size()-1);
    }

    public void removeCategoryFroDataArray(CategoryDataModel categoryDataModel){
        categoryDataArrayList.remove(categoryDataModel);
        notifyDataSetChanged();
    }
}
