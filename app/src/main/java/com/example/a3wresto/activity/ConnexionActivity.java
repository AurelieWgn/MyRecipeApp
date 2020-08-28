package com.example.a3wresto.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a3wresto.R;
import com.example.a3wresto.manager.WsManager;
import com.github.kittinunf.fuel.core.Response;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConnexionActivity extends AppCompatActivity implements WsManager.Listener {
    EditText emailText;
    EditText passwordText;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

         emailText = findViewById(R.id.email);
         passwordText = findViewById(R.id.password);

         pref = getApplicationContext().getSharedPreferences("connectedUser", 0);// verifier si user est deja connecté
        Log.d("test", "Log -> connexion back  " + pref.contains("isConnected"));
    }


    public void onClickConnexion(View view){

        String Email = emailText.getText().toString();
        String Password = passwordText.getText().toString();

        Map dataConnexion=new HashMap();
        dataConnexion.put("login", Email);
        dataConnexion.put("pass",Password);

        String jsonString = new Gson().toJson(dataConnexion);
        dataConnexion.put("Content-Type","application/json");


        WsManager wsManager = new WsManager();
        wsManager.executePostRequest("connexion", this, jsonString, dataConnexion);
    }


    public void onClickCreateAccount(View view){

        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String errorMessage) {
        Log.d("Connexion", "Echec de la connexion");
    }

    @Override
    public void onSuccess(String content) {

        if(content.contains("error")){
            // voir si = Object.containsKey("error")
            Log.d("Connexion error", "Vos indentifiants ne sont pas correcte");
        }else{

            //Enregistrer les info dans le mobil
            try {
                //Transformation du content en bon format de data, format java
                JSONObject obj = null;
                obj = new JSONObject(content);
                String name = obj.getString("prenom");
                String email = obj.getString("email");
                int id = obj.getInt("id");

                //Création du SharedPreferences
                pref = getApplicationContext().getSharedPreferences("connectedUser", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                //Enregistrement dans le SharedPreferances
                editor.putString("nom", name);
                editor.putString("email", email);
                editor.putInt("id_user", id);
                editor.putBoolean("isConnected", true);
                editor.apply();

                //Redirection
                Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
                startActivity(intent);
               // finish();


            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        //redirection vers la list
    }
}
