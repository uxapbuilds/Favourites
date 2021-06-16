package com.uxap.favourites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uxap.favourites.controller.CategoriesItemsRecyclerAdapter;
import com.uxap.favourites.controller.CategoryListRecyclerAdapter;
import com.uxap.favourites.fragments.Fmt_category_list;
import com.uxap.favourites.interfaces.CategoryInteractionListener;
import com.uxap.favourites.model.CategoryDataManager;
import com.uxap.favourites.model.CategoryDataModel;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements CategoryInteractionListener {
    public static final String CATEGORY_DATA_MODEL_OBJ_CODE = "100";
    private static final int ITEMS_ACTIVITY_REQUEST_CODE = 200;
    private Fmt_category_list fmt_category_list;
    private FloatingActionButton fabAddItem;
    private FrameLayout mainFragContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAddItem = findViewById(R.id.fabAddItem);
        mainFragContainer = findViewById(R.id.frameL_fragment_container);

        fmt_category_list = Fmt_category_list.newInstance();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameL_fragment_container, fmt_category_list).commit();

        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCategoryDialog();
            }
        });
    }

    @Override
    public void onCategoryIsTapped(CategoryDataModel categoryDataModel) {
        Intent intent = new Intent(MainActivity.this, CategoryItemsActivity.class);
        intent.putExtra(CATEGORY_DATA_MODEL_OBJ_CODE, categoryDataModel);
        startActivityForResult(intent,ITEMS_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ITEMS_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
               CategoryDataModel categoryDataModel = (CategoryDataModel) data.getExtras().getSerializable(CATEGORY_DATA_MODEL_OBJ_CODE);
               fmt_category_list.saveCategory(categoryDataModel);
            }
        }
    }

    public void createNewCategoryDialog(){
        Dialog addCategoryDialog = new Dialog(this);
        addCategoryDialog.setContentView(R.layout.add_category_dialog_layout);
        addCategoryDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background_drawable));
        addCategoryDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addCategoryDialog.getWindow().getAttributes().windowAnimations = R.style.baseAnimation;
        EditText edtNewCategoryName = addCategoryDialog.findViewById(R.id.edtCategoryName);
        Button btnAddCategory = addCategoryDialog.findViewById(R.id.btnAddCategory);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNewCategoryName.getText().toString().trim().length()>60){
                    Toast.makeText(MainActivity.this, "Max Length for Category Name Exceeded, must be under 60 Chars.", Toast.LENGTH_LONG).show();
                }else if(edtNewCategoryName.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Category Name Can't be Empty. Please Put a Name.", Toast.LENGTH_LONG).show();
                }else{
                    CategoryDataModel categoryDataModel = new CategoryDataModel(edtNewCategoryName.getText().toString().trim(), getCurrentDate(), new ArrayList<>());
                    fmt_category_list.saveToCategoryManager(categoryDataModel);
                    addCategoryDialog.dismiss();
                }
            }
        });
        addCategoryDialog.show();
    }

    public String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return "Created On:\n"+df.format(c);
    }
}