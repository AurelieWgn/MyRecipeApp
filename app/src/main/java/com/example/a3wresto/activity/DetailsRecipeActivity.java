package com.example.a3wresto.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.a3wresto.R;
import com.example.a3wresto.adapter.ItemRecipe;
import com.example.a3wresto.manager.WsManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DetailsRecipeActivity extends AppCompatActivity  implements WsManager.Listener{

    TextView titletextView;
    ImageView photoImageView;
    RatingBar noteTextView;
    TextView tempsPreparationTextView;
    TextView tempsCookRimeTextView;
    TextView caloriesTextView;
    TextView ingredientsTextView;
    TextView instructionsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_recipe_fragment);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.recipe_toolbar);
        setSupportActionBar(myToolbar);
        /**GENERATE BACK BUTTON**/
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        //this.getSupportActionBar().setTitle("");
       // this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        int recipe_id =  getIntent().getIntExtra("RECIPE_ID", 0);
        Log.d("recipe_id", "test recipe_id ->" + recipe_id);

         titletextView = findViewById(R.id.title_text_view);
         photoImageView = findViewById(R.id.photo_image_view);
         noteTextView = findViewById(R.id.rating_bar);
         tempsPreparationTextView = findViewById(R.id.preparation_time);
         tempsCookRimeTextView = findViewById(R.id.cook_time);
         caloriesTextView = findViewById(R.id.calorie_value);
         ingredientsTextView = findViewById(R.id.ingrediants);
         instructionsTextView = findViewById(R.id.instruction);

        try{
            Map dataConnexion=new HashMap();
            String jsonString = new Gson().toJson(dataConnexion);
            dataConnexion.put("Content-Type","application/json");

            WsManager wsManager = new WsManager();
            wsManager.executePostRequest("infoRecette/"+recipe_id, this, jsonString, dataConnexion);

        }catch (Exception e){
            Log.d("response", "Impossible de récup la recette");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public void onFailure(String errorMessage) {
        Log.d("response", "Impossible de récup la recette");
    }

    @Override
    public void onSuccess(String content) {

        ItemRecipe itemRecipe = new Gson().fromJson(content, ItemRecipe.class);

        titletextView.setText(String.valueOf(itemRecipe.getTitle()));
        Picasso.get().load(itemRecipe.getPhoto()).into(photoImageView);
        noteTextView.setRating((float) itemRecipe.getNote());
        tempsPreparationTextView.setText(String.valueOf(itemRecipe.getTempsPreparation() + " min"));
        tempsCookRimeTextView.setText(String.valueOf(itemRecipe.getTempsCookRime() + " min"));
        caloriesTextView.setText(String.valueOf(itemRecipe.getCalories() + " Kcal"));
        ingredientsTextView.setText(String.valueOf(itemRecipe.getIngredients()));
        instructionsTextView.setText(String.valueOf(itemRecipe.getInstructions()));
        this.getSupportActionBar().setTitle(itemRecipe.getTitle());


    }
}
