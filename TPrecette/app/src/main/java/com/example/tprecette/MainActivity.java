package com.example.tprecette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tprecette.service.ConnectionBD;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
    Context context;
    private static final String ONESIGNAL_APP_ID = "1b150199-e8f6-4fc2-9a2e-015bdd13225a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getSupportActionBar().setTitle("Liste de Cuisine");

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();
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
            aboutDialog();
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

    public void aboutDialog() {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = inflater.inflate(R.layout.dialog_about, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setView(view);
        alert.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConnectionBD.close();
    }
}