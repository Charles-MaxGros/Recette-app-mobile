package com.example.tprecette.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tprecette.ContactActivity;
import com.example.tprecette.FormationActivity;
import com.example.tprecette.MainActivity;
import com.example.tprecette.ModifieRecetteActivity;
import com.example.tprecette.R;
import com.example.tprecette.adapter.RecetteAdapter;
import com.example.tprecette.entite.Recette;
import com.example.tprecette.manager.RecetteManager;
import com.example.tprecette.service.ConnectionBD;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RecetteDetail extends AppCompatActivity {
    TextView tvIngredient, tvPreparation, tvTitre, tvDescription;
    ImageView ivImage;
    String imageUri, idRecette;
    Button btnModifier, btnEffacer;
    Context context;

    RecetteManager recetteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_recette_detail);
        tvTitre = findViewById(R.id.tv_detail_titre);
        tvIngredient = findViewById(R.id.tv_ingredient_detail);
        tvPreparation = findViewById(R.id.tv_preparation_detail);
        ivImage = findViewById(R.id.iv_detail_image);
        btnModifier = findViewById(R.id.btn_modifier_detail);
        btnEffacer = findViewById(R.id.btn_effacer_detail);
        tvDescription = findViewById(R.id.tv_detail_descrition);
        getSupportActionBar().setTitle("Recettes detail");

        Intent intent = getIntent();
        idRecette = intent.getStringExtra("idRecette");
        tvTitre.setText(intent.getStringExtra("titre"));
        tvIngredient.setText(intent.getStringExtra("ingredient"));
        tvPreparation.setText(intent.getStringExtra("preparation"));
        tvDescription.setText(intent.getStringExtra("description"));
        imageUri = intent.getStringExtra("image");


        AssetManager assetManager = context.getAssets();

        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput(intent.getStringExtra("image"));
            Drawable drawableImage = Drawable.createFromStream(inputStream, null);
            ivImage.setImageDrawable(drawableImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        btnEffacer.setOnClickListener(v -> {
            recetteManager.delete(context, Integer.parseInt(intent.getStringExtra("idRecette")));
            finish();
        });

        btnModifier.setOnClickListener(v -> {
            Intent intentModificationRecette = new Intent(context, ModifieRecetteActivity.class);
            intentModificationRecette.putExtra("titre", tvTitre.getText());
            intentModificationRecette.putExtra("description", tvDescription.getText());
            intentModificationRecette.putExtra("ingredient", tvIngredient.getText());
            intentModificationRecette.putExtra("preparation", tvPreparation.getText());
            intentModificationRecette.putExtra("image", imageUri);
            intentModificationRecette.putExtra("idRecette", intent.getStringExtra("idRecette"));
            context.startActivity(intentModificationRecette);
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
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

    @Override
    protected void onResume() {
        super.onResume();
        callBackRecette();
    }

    protected void callBackRecette() {
        Recette recettes = RecetteManager.getById(context, idRecette);
        tvTitre.setText(recettes.getTitre());
        tvIngredient.setText(recettes.getIngredient());
        tvPreparation.setText(recettes.getPreparation());
        tvDescription.setText(recettes.getDescription());
        imageUri = recettes.getImage();

    }
}