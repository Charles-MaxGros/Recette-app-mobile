package com.example.tprecette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tprecette.ContactActivity;
import com.example.tprecette.R;

public class FormationActivity extends AppCompatActivity {
    Button buttonContact;
    Button buttoncontact2;
    Button buttoncontact3;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formation);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Formation");

        buttonContact = findViewById(R.id.button_contact1);
        buttoncontact2 = findViewById(R.id.button_contact2);
        buttoncontact3 = findViewById(R.id.button_contact3);

        buttonContact.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        });

        buttoncontact2.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        });

        buttoncontact3.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.recette_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean retour = super.onOptionsItemSelected(item);
        int idItem = item.getItemId();

        if (idItem == R.id.menu_contact) {
            //redirection vers la vue vue contact
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
            return true;
        } else if (idItem == R.id.menu_about) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.dialog_about, null);
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setView(view);
            alert.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
            alert.show();
        } else if (idItem == R.id.button_cours_cuisine) {
            Intent intent = new Intent(this, FormationActivity.class);
            startActivity(intent);
        }
        else if (idItem == R.id.button_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return retour;
    }

}
