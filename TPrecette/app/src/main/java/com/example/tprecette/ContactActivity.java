package com.example.tprecette;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    private Button boutonSoumission;
    private EditText editTextNom;
    private EditText editTextPrenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        boutonSoumission = findViewById(R.id.button_soumission);
        editTextNom = findViewById(R.id.editText_name);
        editTextPrenom = findViewById(R.id.editText_prenom);
     //   getSupportActionBar().hide();
        getSupportActionBar().setTitle("Contact");


        boutonSoumission.setOnClickListener(v ->
                showDialog()
        );

    }

    private void showDialog() {
        String nom = editTextNom.getText().toString();
        String prenom = editTextPrenom.getText().toString();

        if (TextUtils.isEmpty(nom) || TextUtils.isEmpty(prenom)) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires: Nom et Prenom", Toast.LENGTH_SHORT).show();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Merci " + prenom + "!");
            builder.setMessage("Votre formulaire a été soumis avec succès.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(ContactActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
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
