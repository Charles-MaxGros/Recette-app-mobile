package com.example.tprecette;

import static androidx.core.view.MotionEventCompat.getActionMasked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.tprecette.adapter.RecetteAdapter;
import com.example.tprecette.entite.Recette;
import com.example.tprecette.manager.RecetteManager;
import com.example.tprecette.service.ConnectionBD;
import com.example.tprecette.view.RecetteDetail;

import java.util.ArrayList;

public class RecetteActivity extends AppCompatActivity {
    Context context;
    ListView listViewMain;
    AssetManager assetManager;
    String pays, cuisine;

    boolean boolDialog = true;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recette);
        context = this;
        assetManager = context.getAssets();
        listViewMain = findViewById(R.id.list_view_main);
        Intent intent = getIntent();
        cuisine = intent.getStringExtra("cuisine");
        pays = intent.getStringExtra("pays");
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(v->{
            Intent intentAddRecette = new Intent(context, AddRecetteActivity.class);
            intentAddRecette.putExtra("pays", pays);
            context.startActivity(intentAddRecette);
        });
        getSupportActionBar().setTitle("Recettes " + cuisine);

        callBackListRecette();

        listViewMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                boolDialog = false;
                Recette recette = (Recette) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog dialog = builder.create();
                LinearLayout llViewLongPressDialoque = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.long_press_alert_dialogue, null);
                Button btnModifier = llViewLongPressDialoque.findViewById(R.id.btn_long_press_modifier);
                dialog.setView(llViewLongPressDialoque);
                dialog.show();
                btnModifier.setOnClickListener(v -> {
                    Intent intent = new Intent(context, ModifieRecetteActivity.class);
                    intent.putExtra("titre", recette.getTitre());
                    intent.putExtra("description", recette.getDescription());
                    intent.putExtra("ingredient", recette.getIngredient());
                    intent.putExtra("preparation", recette.getPreparation());
                    intent.putExtra("image", recette.getImage());
                    intent.putExtra("idRecette", String.valueOf(recette.getId()));
                    dialog.dismiss();
                    context.startActivity(intent);
                });

                Button btnSupprimer = llViewLongPressDialoque.findViewById(R.id.btn_long_press_supprimer);
                btnSupprimer.setOnClickListener(v -> {
                    RecetteManager.delete(context, recette.getId());
                    callBackListRecette();
                    dialog.dismiss();
                });
                boolDialog = true;
                return true;
            }

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

    @Override
    protected void onResume() {
        super.onResume();

     callBackListRecette();

    }

    protected void callBackListRecette() {

        ArrayList<Recette> recettes = RecetteManager.getByPays(context, pays);
        RecetteAdapter recetteAdapter = new RecetteAdapter(context, R.layout.recette_view_layout, recettes);
        listViewMain.setAdapter(recetteAdapter);

        listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (boolDialog) {
                    Recette recette = (Recette) parent.getItemAtPosition(position);
                    Intent intent = new Intent(context, RecetteDetail.class);
                    intent.putExtra("titre", recette.getTitre());
                    intent.putExtra("description", recette.getDescription());
                    intent.putExtra("ingredient", recette.getIngredient());
                    intent.putExtra("preparation", recette.getPreparation());
                    intent.putExtra("image", recette.getImage());
                    intent.putExtra("idRecette", String.valueOf(recette.getId()));
                    context.startActivity(intent);
                }

            }
        });
    }


}