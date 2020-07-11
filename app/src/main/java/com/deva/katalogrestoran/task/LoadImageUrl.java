package com.deva.katalogrestoran.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadImageUrl extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;

    public LoadImageUrl(ImageView imgView){
        this.imageView = imgView;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL imageUrl = new URL(urls[0]);
            Bitmap bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            return bmp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Bitmap bmp) {
        imageView.setImageBitmap(bmp);
    }
}