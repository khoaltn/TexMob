package com.ebookfrenzy.finalproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.text.Editable;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by madmax on 4/22/15.
 */
public class ImageFetcher extends AsyncTask<String, Void, Bitmap> {
    private Resources resources;

    public ImageFetcher(Resources resources) {
        this.resources = resources;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL(resources.getString(R.string.ip_addr));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection == null) {
                System.out.println("Null urlConnection at doInBackground()");
                return null;
            }

            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            urlConnection.getOutputStream().write(params[0].getBytes());

            Bitmap b = BitmapFactory.decodeStream(urlConnection.getInputStream());

            urlConnection.disconnect();
            return b;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Error at putImageToView");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error at putImageToView");
        }
        return null;
    }
}
