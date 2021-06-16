package com.uxap.favourites.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uxap.favourites.R;

public class CategoryItemsListRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryItemTitleText;
    private CardView cardCategoryItem;
    public CategoryItemsListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryItemTitleText = itemView.findViewById(R.id.rv_category_items_view_layout_title);
        cardCategoryItem = itemView.findViewById(R.id.cardCategoryItem);
    }

    public TextView getCategoryItemTitleText() {
        return categoryItemTitleText;
    }

    public void setCategoryItemTitleText(TextView categoryItemTitleText) {
        this.categoryItemTitleText = categoryItemTitleText;
    }

    public CardView getCardCategoryItem() {
        return cardCategoryItem;
    }

    public void setCardCategoryItem(CardView cardCategoryItem) {
        this.cardCategoryItem = cardCategoryItem;
    }
}
