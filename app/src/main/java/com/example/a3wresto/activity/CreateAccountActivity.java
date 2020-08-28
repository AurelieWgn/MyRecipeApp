package com.example.a3wresto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a3wresto.R;
import com.example.a3wresto.manager.WsManager;
import com.example.a3wresto.manager.WsManager.Listener;
import com.github.kittinunf.fuel.android.core.Json;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity implements Listener {


    EditText password;
    EditText email;
    EditText nom;
    EditText prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        nom  = findViewById(R.id.nom);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        prenom = findViewById(R.id.prenom);

    }

    public void onClickCreateAccount(View view){

        String Name = nom.getText().toString();
        String FirstName = prenom.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();


        //Map pour mettre en form le Json
        Map dataAccount=new HashMap();
        //Adding elements to map
        dataAccount.put("login", Email);
        dataAccount.put("pass",Password);
        dataAccount.put("nom", Name);
        dataAccount.put("prenom", FirstName);

        String jsonString = new Gson().toJson(dataAccount);

        dataAccount.put("Content-Type","application/json");

        WsManager wsManager = new WsManager();

        //(String target, final Listener listener, String jsonString, Map<String, String> header )
        wsManager.executePostRequest("addCompte", this, jsonString, dataAccount);

    }


    @Override
    public void onFailure(String errorMessage) {
        Log.d("CREAT ACCOUNT", errorMessage);
        Log.d("CREAT ACCOUNT", "ça marche pas");

        //Afficher un message d'err
    }

    @Override
    public void onSuccess(String content) {

        Intent intent = new Intent( CreateAccountActivity.this, ConnexionActivity.class);
        startActivity(intent);

    }
}
