package com.example.tprecette.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tprecette.R;
import com.example.tprecette.entite.Recette;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RecetteAdapter extends ArrayAdapter<Recette> {
    int idLayout;
    Context context;
    AssetManager assetManager;

    public RecetteAdapter(@NonNull Context context, int resource, @NonNull List<Recette> objects) {
        super(context, resource, objects);
        idLayout = resource;
        assetManager = context.getAssets();
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Recette recette = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(idLayout, parent, false);
        }
        ImageView urlImage = convertView.findViewById(R.id.img_recette);
        TextView tvRecette = convertView.findViewById(R.id.tv_recette);
        tvRecette.setText(recette.getTitre());
        try {
            InputStream inputStream = context.openFileInput(recette.getImage());
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            urlImage.setImageDrawable(drawable);
            recette.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertView;

    }
}
