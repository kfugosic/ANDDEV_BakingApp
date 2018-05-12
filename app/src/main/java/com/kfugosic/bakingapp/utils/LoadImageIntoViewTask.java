package com.kfugosic.bakingapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 *
 */
public class LoadImageIntoViewTask extends AsyncTask<String, Void, Bitmap> {

    private WeakReference<Context> contextRef;
    private WeakReference<ImageView> imageViewRef;

    public LoadImageIntoViewTask(Context context, ImageView imageView) {
        this.contextRef = new WeakReference<>(context);
        this.imageViewRef = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            return Picasso.with(contextRef.get())
                    .load(params[0])
                    .get();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(result == null) {
            imageViewRef.get().setVisibility(View.GONE);
        } else {
            imageViewRef.get().setImageBitmap(result);
            imageViewRef.get().setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
