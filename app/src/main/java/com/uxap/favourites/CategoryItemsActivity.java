package com.uxap.favourites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uxap.favourites.controller.CategoriesItemsRecyclerAdapter;
import com.uxap.favourites.interfaces.CategoryDataListener;
import com.uxap.favourites.model.CategoryDataModel;

import java.util.ArrayList;

public class CategoryItemsActivity extends AppCompatActivity implements CategoryDataListener {
    private TextView ctTextLabel;
    private RecyclerView rvCategoryItem;
    private FloatingActionButton fabAddCategoryItem;
    private CategoryDataModel categoryDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        categoryDataModel = (CategoryDataModel) getIntent().getSerializableExtra(MainActivity.CATEGORY_DATA_MODEL_OBJ_CODE);

        ctTextLabel = findViewById(R.id.ct_text_label);
        ctTextLabel.setText(categoryDataModel.getCategoryTitle());

        rvCategoryItem = findViewById(R.id.rv_category_items);
        rvCategoryItem.setAdapter(new CategoriesItemsRecyclerAdapter(categoryDataModel.getCategoryItems(), this));
        rvCategoryItem.setLayoutManager(new LinearLayoutManager(this));

        fabAddCategoryItem = findViewById(R.id.fabAddCategoryItem);
        fabAddCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewItemDialog();
            }
        });

    }

    private void createNewItemDialog() {
        Dialog addCategoryItemDialog = new Dialog(this);
        addCategoryItemDialog.setContentView(R.layout.add_category_item_dialog_layout);
        addCategoryItemDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background_drawable));
        addCategoryItemDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addCategoryItemDialog.getWindow().getAttributes().windowAnimations = R.style.baseAnimation;

        EditText edtCategoryItemName = addCategoryItemDialog.findViewById(R.id.edtCategoryItemName);
        Button btnAddCategoryItem = addCategoryItemDialog.findViewById(R.id.btnAddCategoryItem);

        btnAddCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCategoryItemName.getText().toString().trim().length()>60){
                    Toast.makeText(CategoryItemsActivity.this, "Max Length for Category Name Exceeded, must be under 60 Chars.", Toast.LENGTH_LONG).show();
                }else if(edtCategoryItemName.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(CategoryItemsActivity.this, "Category Name Can't be Empty. Please Put a Name.", Toast.LENGTH_LONG).show();
                }else{
                    categoryDataModel.getCategoryItems().add(edtCategoryItemName.getText().toString());
                    CategoriesItemsRecyclerAdapter categoriesItemsRecyclerAdapter = (CategoriesItemsRecyclerAdapter) rvCategoryItem.getAdapter();
//                            if (categoryDataModel.getCategoryItems().size() == 0) {
//                                categoriesItemsRecyclerAdapter.notifyRecycler(0);
//                            } else if (categoryDataModel.getCategoryItems().size() > 0) {
//                                categoriesItemsRecyclerAdapter.notifyRecycler(1);
//                            }
                    categoriesItemsRecyclerAdapter.notifyRecycler();
                    addCategoryItemDialog.dismiss();
                }
            }
        });
        addCategoryItemDialog.show();
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.CATEGORY_DATA_MODEL_OBJ_CODE,categoryDataModel);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void cardIsLongPressed(String string) {

        AlertDialog.Builder deleteItemDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        deleteItemDialog.setTitle("Delete Item?")
                .setMessage(string+" will be deleted permanently.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        categoryDataModel.getCategoryItems().remove(string.trim());
                        CategoriesItemsRecyclerAdapter categoriesItemsRecyclerAdapter = (CategoriesItemsRecyclerAdapter)rvCategoryItem.getAdapter();
//                        if(categoryDataModel.getCategoryItems().size()==0) {
//                            categoriesItemsRecyclerAdapter.notifyRecycler(0);
//                        }else if(categoryDataModel.getCategoryItems().size()>0){
//                            categoriesItemsRecyclerAdapter.notifyRecycler(1);
//                        }
                        categoriesItemsRecyclerAdapter.notifyRecycler();

                        Toast.makeText(CategoryItemsActivity.this, string+" was deleted", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false).create().show();
    }
}