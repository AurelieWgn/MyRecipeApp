package com.example.a3wresto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3wresto.R;
import com.example.a3wresto.adapter.ItemRecipe;
import com.example.a3wresto.adapter.ItemRecipeAdapter;
import com.example.a3wresto.manager.WsManager;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RecipeListFragment extends Fragment implements WsManager.Listener, ItemRecipeAdapter.ItemClickListener {

    public RecipeListFragment() { }


    private RecyclerView recyclerView;
    private List<ItemRecipe> recipeList;
    private Gson gson = new Gson();
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipelist_fragment, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        try{
            Map dataConnexion=new HashMap();
            String jsonString = new Gson().toJson(dataConnexion);
            dataConnexion.put("Content-Type","application/json");

            WsManager wsManager = new WsManager();
            wsManager.executePostRequest("listRecettes", this, jsonString, dataConnexion);

        }catch(Exception e){
            Log.d("liste", "Impossible de récupèrer la liste");
        }

        return view;
    }

    @Override
    public void onFailure(String errorMessage) {
        Log.d("liste", errorMessage);
    }

    @Override
    public void onSuccess(String content) {

        //transformer la data en list d'ItemRecipe
        ItemRecipe[] founderArray = gson.fromJson(content, ItemRecipe[].class);
        recipeList = Arrays.asList(founderArray);

        ItemRecipeAdapter adapter = new ItemRecipeAdapter(recipeList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListener(int position) {
        ItemRecipe itemRecipe = recipeList.get(position);

        Intent intent = new Intent( getActivity(), DetailsRecipeActivity.class);
        intent.putExtra("RECIPE_ID", itemRecipe.getId());
        startActivity(intent);
    }
}
