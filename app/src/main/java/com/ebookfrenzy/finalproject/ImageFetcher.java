package com.ebookfrenzy.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

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
public class ImageFetcher {

    public static void putImageToView(String latex, ImageView view) {
        URL url = null;
        try {
            url = new URL(view.getResources().getString(R.string.ip_addr));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(latex.getBytes());

            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            Bitmap b = BitmapFactory.decodeStream(inputStream);

            view.setImageBitmap(b);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
