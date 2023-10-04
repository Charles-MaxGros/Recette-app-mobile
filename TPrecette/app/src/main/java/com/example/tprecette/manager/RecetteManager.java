package com.example.tprecette.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tprecette.entite.Recette;
import com.example.tprecette.service.ConnectionBD;

import java.util.ArrayList;

public class RecetteManager {
    public static ArrayList<Recette> getAll(Context context) {
        ArrayList<Recette> retour = null;
        Cursor cursor = ConnectionBD.getBd(context).rawQuery("select * from recette", null);
        if (cursor.isBeforeFirst()) {
            retour = new ArrayList<>();
            while (cursor.moveToNext()) {
                retour.add(new Recette(cursor));
            }
        }
        return retour;
    }

    public static ArrayList<Recette> getByPays(Context context, String pays) {
        ArrayList<Recette> retour = null;
        Cursor cursor = ConnectionBD.getBd(context).rawQuery("select * from recette WHERE pays = ?", new String[]{pays});
        if (cursor.isBeforeFirst()) {
            retour = new ArrayList<>();
            while (cursor.moveToNext()) {
                retour.add(new Recette(cursor));
            }
        }
        return retour;
    }

    public static long addRecette(Context context, Recette recetteToAdd) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", recetteToAdd.getImage());
        contentValues.put("titre", recetteToAdd.getTitre());
        contentValues.put("pays", recetteToAdd.getPays());
        contentValues.put("description", recetteToAdd.getDescription());
        contentValues.put("ingredient", recetteToAdd.getIngredient());
        contentValues.put("preparation", recetteToAdd.getPreparation());

        SQLiteDatabase bd = ConnectionBD.getBd(context);
        return bd.insert("recette", null, contentValues);
    }

    public static Recette getById(Context context, String idRecette) {
        Recette recette = new Recette();
        Cursor cursor = ConnectionBD.getBd(context).rawQuery("select * from recette WHERE id = ?", new String[]{idRecette});
        while (cursor.moveToNext()) {
            recette.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            recette.setTitre(cursor.getString(cursor.getColumnIndexOrThrow("titre")));
            recette.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
            recette.setIngredient(cursor.getString(cursor.getColumnIndexOrThrow("ingredient")));
            recette.setPreparation(cursor.getString(cursor.getColumnIndexOrThrow("preparation")));
            recette.setPays(cursor.getString(cursor.getColumnIndexOrThrow("pays")));
            recette.setImage(cursor.getString(cursor.getColumnIndexOrThrow("image")));
        }
        return recette;
    }

        public static void delete (Context context,int idRecette){
            SQLiteDatabase bd = ConnectionBD.getBd(context);
            bd.delete("recette", "id = ?", new String[]{String.valueOf(idRecette)});
    }

    public static void update(Context context, Recette recetteToUpdate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("titre", recetteToUpdate.getTitre());
        contentValues.put("image", recetteToUpdate.getImage());
        contentValues.put("description", recetteToUpdate.getDescription());
        contentValues.put("ingredient", recetteToUpdate.getIngredient());
        contentValues.put("preparation", recetteToUpdate.getPreparation());
        SQLiteDatabase bd = ConnectionBD.getBd(context);
        long nbRowChange = bd.update("recette", contentValues, "id = ?", new String[]{String.valueOf(recetteToUpdate.getId())});
        }
    }
