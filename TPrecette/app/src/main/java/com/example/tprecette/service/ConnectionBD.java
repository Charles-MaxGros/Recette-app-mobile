package com.example.tprecette.service;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.example.tprecette.helper.DataBaseHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectionBD {
    private static int version = 1;
    private static String bnName = "recettes.db";
    private static SQLiteDatabase bd = null;
    private static DataBaseHelper helper;

    public static SQLiteDatabase getBd(Context context){
        if(helper == null){
            copyBdFromAsset(context);
            copyImagesFromAsset(context);
            helper = new DataBaseHelper(context, bnName, null, version);
        }
        bd = helper.getWritableDatabase();
        return bd;
    }
    public static void close(){
        if(bd!= null &&  !bd.isOpen()){
            bd.close();
        }
    }

    public static void copyBdFromAsset(Context context) {
        if (!isDataBase(context)) {
            try {
                int bufferSize = 256;
                InputStream in = context.getAssets().open("bd/" + bnName);
                OutputStream out = new FileOutputStream(context.getDatabasePath(bnName));
                byte[] buffer = new byte[bufferSize];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void copyImagesFromAsset(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            String[] imageFiles = assetManager.list("img/recettes");
            if (imageFiles != null) {
                for (String imageFile : imageFiles) {
                    InputStream in = assetManager.open("img/recettes/" + imageFile);
                    OutputStream out = new FileOutputStream(context.getFilesDir() + "/" + imageFile);
                    byte[] buffer = new byte[256];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDataBase(Context context) {
        return context.getDatabasePath(bnName).exists();
    }
}
