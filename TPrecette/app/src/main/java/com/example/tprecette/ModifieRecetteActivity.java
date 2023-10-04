package com.example.tprecette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tprecette.entite.Recette;
import com.example.tprecette.manager.RecetteManager;

public class ModifieRecetteActivity extends AppCompatActivity {
    String idRecette;
    TextView tvIngredient, tvPreparation, tvTitre, tvDescription;
    String imageUri;

    Button btnAppliquer;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifie_recette);
        tvTitre = findViewById(R.id.et_modif_titre);
        tvDescription = findViewById(R.id.et_modif_description);
        tvIngredient = findViewById(R.id.et_modif_ingredient);
        tvPreparation = findViewById(R.id.et_modif_preparation);
        btnAppliquer = findViewById(R.id.btn_appliquer);
        context = this;

        getSupportActionBar().setTitle("Recettes modification");
        Intent intent = getIntent();

        idRecette = intent.getStringExtra("idRecette");
        Recette recette = RecetteManager.getById(context, idRecette);

        tvTitre.setText(recette.getTitre());
        tvIngredient.setText(recette.getIngredient());
        tvPreparation.setText(recette.getPreparation());
        tvDescription.setText(recette.getDescription());
        imageUri = recette.getImage();

        btnAppliquer.setOnClickListener(v->{
            recette.setTitre(tvTitre.getText().toString());
            recette.setDescription(tvDescription.getText().toString());
            recette.setIngredient(tvIngredient.getText().toString());
            recette.setPreparation(tvPreparation.getText().toString());
            recette.setImage(imageUri);
            RecetteManager.update(context,recette);
            finish();
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