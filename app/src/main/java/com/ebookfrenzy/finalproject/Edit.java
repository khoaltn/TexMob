package com.ebookfrenzy.finalproject;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Edit extends ActionBarActivity {

    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        input = (EditText) findViewById(R.id.inputLatexCode);

        Intent intentReceived = getIntent();
        String fileName = intentReceived.getStringExtra(Home.EXTRA_MESSAGE);

        InputStream istream = null;
        try {
            istream = new BufferedInputStream(new FileInputStream(fileName));
            byte in[] = new byte[0];
            istream.read(in);
            input.setText(in.toString());
        } catch (Exception e) { e.printStackTrace(); }
        finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void openHomeFromEdit(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void clearAll(View v) {
        input.setText("");
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
}
