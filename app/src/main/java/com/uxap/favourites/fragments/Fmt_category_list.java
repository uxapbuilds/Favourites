package com.uxap.favourites.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.uxap.favourites.MainActivity;
import com.uxap.favourites.R;
import com.uxap.favourites.controller.CategoryListRecyclerAdapter;
import com.uxap.favourites.interfaces.CategoryInteractionListener;
import com.uxap.favourites.interfaces.CategoryItemListener;
import com.uxap.favourites.model.CategoryDataManager;
import com.uxap.favourites.model.CategoryDataModel;

import java.util.ArrayList;

public class Fmt_category_list extends Fragment implements CategoryItemListener {

    private RecyclerView rvCategoryList;
    private CategoryInteractionListener categoryInteractionListener;
    private CategoryDataManager categoryDataManager;

    public Fmt_category_list() {
        // Required empty public constructor
    }

    public static Fmt_category_list newInstance() {
        Fmt_category_list fragment = new Fmt_category_list();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CategoryInteractionListener){
            categoryInteractionListener = (CategoryInteractionListener)context;
            categoryDataManager = new CategoryDataManager(context);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getView()!=null){
            rvCategoryList = getView().findViewById(R.id.rv_category_list);
            rvCategoryList.setAdapter(new CategoryListRecyclerAdapter(categoryDataManager.retrieveCategoryData(),this));
            rvCategoryList.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_list, container, false);
    }

    @Override
    public void categoryIsClicked(CategoryDataModel categoryDataModel) {
        categoryInteractionListener.onCategoryIsTapped(categoryDataModel);
    }

    @Override
    public void categoryIsLongPressed(CategoryDataModel categoryDataModel) {

        AlertDialog.Builder deleteItemDialog = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        deleteItemDialog.setTitle("Delete Category?")
                .setMessage(categoryDataModel.getCategoryTitle().trim()+" will be deleted permanently.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        categoryDataManager.removeCategory(categoryDataModel.getCategoryTitle().trim());
                        CategoryListRecyclerAdapter categoryListRecyclerAdapter = (CategoryListRecyclerAdapter)rvCategoryList.getAdapter();
                        categoryListRecyclerAdapter.removeCategoryFroDataArray(categoryDataModel);
                        Toast.makeText(getContext(), categoryDataModel.getCategoryTitle().trim()+" was deleted", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false).create().show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        categoryInteractionListener = null;
    }

    public void saveToCategoryManager(CategoryDataModel categoryDataModel){
        categoryDataManager.saveCategoryData(categoryDataModel);
        CategoryListRecyclerAdapter categoryListRecyclerAdapter = (CategoryListRecyclerAdapter)rvCategoryList.getAdapter();
        categoryListRecyclerAdapter.addCategoryToDataArray(categoryDataModel);
    }

    public void saveCategory (CategoryDataModel category){
        categoryDataManager.saveCategoryData(category);
        rvCategoryList.setAdapter(new CategoryListRecyclerAdapter(categoryDataManager.retrieveCategoryData(), this));
    }

}