package com.uxap.favourites.interfaces;

import com.uxap.favourites.model.CategoryDataModel;

public interface CategoryItemListener {
    void categoryIsClicked(CategoryDataModel categoryDataModel);
    void categoryIsLongPressed(CategoryDataModel categoryDataModel);
}
