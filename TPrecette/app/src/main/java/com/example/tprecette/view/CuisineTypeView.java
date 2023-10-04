package com.example.tprecette.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.tprecette.R;
import com.example.tprecette.RecetteActivity;

import java.io.IOException;
import java.io.InputStream;

public class CuisineTypeView extends View {
    String pays,cuisine;
    int color, textColor, marge;
    Paint paint;
    Bitmap bitmapImage;
    private Context context;

    public CuisineTypeView(Context context) {
        super(context);
        init();

    }

    public CuisineTypeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray attributPerso = context.obtainStyledAttributes(attrs, R.styleable.CuisineTypeView);
        pays = attributPerso.getString(R.styleable.CuisineTypeView_pays);
        cuisine = attributPerso.getString(R.styleable.CuisineTypeView_cuisine);
        init();

        setOnClickListener(v -> {
            Intent intent = new Intent(context, RecetteActivity.class);
            intent.putExtra("cuisine", cuisine);
            intent.putExtra("pays", pays);

            context.startActivity(intent);
        });

    }

    private void init() {
        color = Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        textColor = Color.BLACK;
        marge = 15;
        paint = new Paint();
        paint.setTextSize(60);
        paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        paint.setColor(color);
        AssetManager assetManager = getContext().getAssets();
        try {
            InputStream inputStream = assetManager.open("img/pays/" + pays + ".png");
            bitmapImage = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasure = MeasureSpec.makeMeasureSpec(600, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasure);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(color);
        canvas.drawBitmap(bitmapImage, (getWidth() - bitmapImage.getWidth()) / 2, marge, paint);
        paint.setColor(textColor);
        canvas.drawText(cuisine, (getWidth() - paint.measureText(cuisine)) / 2, bitmapImage.getHeight() + ((getHeight() - bitmapImage.getHeight()) / 2), paint);
    }






}
