package com.example.tprecette;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tprecette.R;
import com.example.tprecette.entite.Recette;
import com.example.tprecette.manager.RecetteManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddRecetteActivity extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;
    EditText edTitle, etIngredients, etPreparationSteps, etDescription;
    TextView tvPays, tvImage;
    Button btnSubmit, btnCancel, btnImage;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recette);
        getSupportActionBar().setTitle("Add new recette");

        edTitle = findViewById(R.id.et_title);
        tvImage = findViewById(R.id.tv_image);
        tvPays = findViewById(R.id.tv_pays);
        btnImage = findViewById(R.id.btn_image);
        imgView = findViewById(R.id.img_view);

        etIngredients = findViewById(R.id.et_ingredients);
        etPreparationSteps = findViewById(R.id.et_preparation_steps);
        etDescription = findViewById(R.id.et_description);

        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        Intent intent = getIntent();
        String pays = intent.getStringExtra("pays");
        tvPays.setText("Pays : " + pays);

        btnSubmit.setOnClickListener(v -> {
            String title = edTitle.getText().toString();
            String image = tvImage.getText().toString();
            String description = etDescription.getText().toString();
            String ingredients = etIngredients.getText().toString();
            String preparationSteps = etPreparationSteps.getText().toString();

            if (title.equals("") || image.equals("")) {
                Toast.makeText(this, "Veuillez remplir les champs avec (*)", Toast.LENGTH_SHORT).show();
            } else {
                Recette recette = new Recette();
                recette.setTitre(title);
                recette.setImage(image);
                recette.setPays(pays);
                recette.setDescription(description);
                recette.setIngredient(ingredients);
                recette.setPreparation(preparationSteps);

                long result = RecetteManager.addRecette(AddRecetteActivity.this, recette);
                if (result != -1) {
                    Toast.makeText(AddRecetteActivity.this, "Recette ajoutée avec succès", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(v -> {
            finish();
        });

        btnImage.setOnClickListener(v -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setType("image/*");
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                Uri imageUri = data.getData();
                imgView.setImageURI(imageUri);

                String imagePath = getPathFromUri(imageUri);
                String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                btnImage.setText(imageName);
                btnImage.setBackgroundTintList(getResources().getColorStateList(R.color.green, getTheme()));


                try {
                    copyImageToAssets(imageUri, imageName);
                    Toast.makeText(this, "Image uploaded", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to upload", Toast.LENGTH_SHORT).show();
                }
            }
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

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_idx);
    }

    private void copyImageToAssets(Uri uri, String imageName) throws IOException {
        InputStream in = getContentResolver().openInputStream(uri);
        File outFile = new File(getFilesDir(), imageName);
        OutputStream out = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }

        in.close();
        out.flush();
        out.close();

        File assetsDir = new File(getFilesDir().getParent(), "app/src/main/assets/img/recettes");
        if (!assetsDir.exists()) {
            assetsDir.mkdirs();
        }

        File assetFile = new File(assetsDir, imageName);
        copyFile(outFile, assetFile);

        String assetPath = imageName;
        tvImage.setText(assetPath);
        tvImage.setVisibility(View.GONE);
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(sourceFile);
        FileOutputStream outputStream = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
}