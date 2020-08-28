package com.example.a3wresto.activity;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.a3wresto.R;

public class ProfilFragment extends Fragment {

    TextView nameTextview;
    TextView emailTextview;


    public ProfilFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profil, container, false);


        nameTextview = view.findViewById(R.id.profil_name);
        emailTextview = view.findViewById(R.id.profil_email);


        try{
            SharedPreferences prefs = this.getActivity().getSharedPreferences("connectedUser", Context.MODE_PRIVATE);
            String name = prefs.getString("nom", "Name");
            String email = prefs.getString("email", "email");
            Log.d("Prefs", "name" + name);

            nameTextview.setText(name);
            emailTextview.setText(email);

        }catch (Exception e){

        }
        return view;
    }


}
