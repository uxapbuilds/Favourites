package com.uxap.favourites.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategoryDataManager {
    private Context context;
    private Gson gson;
    public CategoryDataManager(Context context) {
        this.context = context;
    }

    public void saveCategoryData(CategoryDataModel categoryDataModel){
        gson = new Gson();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

//        ArrayList<CategoryDataModel> dataArray = new ArrayList<>();
//        dataArray.add(categoryDataModel);

        List<CategoryDataModel> list = Arrays.asList(categoryDataModel);

        String dataAsJsonString = gson.toJson(list);

        editor.putString(categoryDataModel.getCategoryTitle(),dataAsJsonString);
        editor.apply();
    }

    public ArrayList<CategoryDataModel> retrieveCategoryData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String,?> mappedData = sharedPreferences.getAll();
        ArrayList<CategoryDataModel> dataArray = new ArrayList<>();

        for(Map.Entry<String,?> item : mappedData.entrySet()){

            String jsonString = (String)item.getValue();

            gson = new Gson();
            Type listOfMyClassObject = new TypeToken<ArrayList<CategoryDataModel>>() {}.getType();
            List<CategoryDataModel> outputList = gson.fromJson(jsonString, listOfMyClassObject);

            for(int i=0; i<outputList.size(); i++){
                CategoryDataModel categoryDataModel = new CategoryDataModel(outputList.get(i).getCategoryTitle(), outputList.get(i).getCategoryDateAdded(),outputList.get(i).getCategoryItems());
                dataArray.add(categoryDataModel);
                //Log.i("RETRIEVED-DATA-SF: ", categoryDataModel.getCategoryTitle()+" "+categoryDataModel.getCategoryDateAdded());

            }
        }
        return dataArray;
    }

    public void removeCategory(String string){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(string);
        editor.commit();
    }
}
