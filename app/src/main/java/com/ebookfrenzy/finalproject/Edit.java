package com.ebookfrenzy.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Edit extends ActionBarActivity {

    private File currentFile;
    private String latexCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        EditText input = (EditText) findViewById(R.id.inputLatexCode);

        Intent intentReceived = getIntent();
        String fileName = intentReceived.getStringExtra(Home.EXTRA_MESSAGE);

        try {
            currentFile = new File(Home.directory.getPath(), fileName);
            if (currentFile.exists()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(currentFile.getAbsolutePath()));

                    latexCode = bufferedReader.readLine();
                    bufferedReader.close();

                    input.setText(latexCode);
                } catch (Exception e) {
                    e.printStackTrace();

                    Toast toast = Toast.makeText(getApplicationContext(), "Press 'Save Code' when you're done.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void openHomeFromEdit(View v) {
        DialogAskIfSaved dialog = new DialogAskIfSaved();
        dialog.show(getFragmentManager(), "isSaved");
    }

    public void clearAll(View v) {
        EditText input = (EditText) findViewById(R.id.inputLatexCode);
        input.setText("");
    }

    public void saveCode(View v) {
        EditText input = (EditText) findViewById(R.id.inputLatexCode);
        latexCode = input.getText().toString();

        try {
            PrintWriter out = new PrintWriter(currentFile);
            out.print(latexCode);
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is responsible for handling the button click for the typeset option.
     */
    public void onClick(View v) {
        ImageFetcher fetcher = new ImageFetcher(v.getResources());
        ImageView view = (ImageView) findViewById(R.id.output);
        Bitmap b = fetcher.doInBackground(((TextView) findViewById(R.id.inputLatexCode)).getText().toString());

        if (b != null) {
            int width = b.getWidth();
            int height = b.getHeight();
            float scaleWidth = ((float) view.getWidth()) / width;
            float scaleHeight = ((float) view.getHeight()) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);

            view.setBackgroundColor(Color.WHITE);
            view.setImageBitmap(Bitmap.createBitmap(b, 0, 0, width, height, matrix, false));
        } else {
            System.out.println("no image found");
        }
    }
}
