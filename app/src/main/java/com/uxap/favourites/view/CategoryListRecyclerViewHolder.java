package com.uxap.favourites.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uxap.favourites.R;

public class CategoryListRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryTitle, categoryItemCount, categoryDateAdded;
    private CardView cardCategory;
    public CategoryListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryTitle = itemView.findViewById(R.id.rv_view_layout_title);
        categoryDateAdded = itemView.findViewById(R.id.rv_view_layout_date_added);
        categoryItemCount = itemView.findViewById(R.id.rv_view_layout_item_count);
        cardCategory = itemView.findViewById(R.id.cardCategory);
    }

    public TextView getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(TextView categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public TextView getCategoryItemCount() {
        return categoryItemCount;
    }

    public void setCategoryItemCount(TextView categoryItemCount) {
        this.categoryItemCount = categoryItemCount;
    }

    public TextView getCategoryDateAdded() {
        return categoryDateAdded;
    }

    public void setCategoryDateAdded(TextView categoryDateAdded) {
        this.categoryDateAdded = categoryDateAdded;
    }

    public CardView getCardCategory() {
        return cardCategory;
    }

    public void setCardCategory(CardView cardCategory) {
        this.cardCategory = cardCategory;
    }
}
