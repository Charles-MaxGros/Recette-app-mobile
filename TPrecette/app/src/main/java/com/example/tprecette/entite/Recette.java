package com.example.tprecette.entite;

import android.database.Cursor;
import android.graphics.drawable.Drawable;

public class Recette {
    private int id;
    private String titre;
    private String image;
    private String description;
    private String pays;
    private String ingredient;
    private String preparation;

    private Drawable imageDrawable;


    public Recette() {
    }

    public Recette(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        this.titre = cursor.getString(cursor.getColumnIndexOrThrow("titre"));
        this.image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
        this.pays = cursor.getString(cursor.getColumnIndexOrThrow("pays"));
        this.description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        this.ingredient = cursor.getString(cursor.getColumnIndexOrThrow("ingredient"));
        this.preparation = cursor.getString(cursor.getColumnIndexOrThrow("preparation"));

    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(Drawable imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String recetteTitle) {
        this.titre = recetteTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredients) {
        this.ingredient = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
}
