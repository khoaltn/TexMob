package com.ebookfrenzy.finalproject;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Edit extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        EditText input = (EditText) findViewById(R.id.inputLatexCode);

        Intent intentReceived = getIntent();
        String fileName = intentReceived.getStringExtra(Home.EXTRA_MESSAGE);

        Toast toast = Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG);
        toast.show();
    }

    public void openHomeFromEdit(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void clearAll(View v) {
        EditText input = (EditText) findViewById(R.id.inputLatexCode);
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

    /**
     * This method is responsible for handling the button click for the typeset option.
     */
    public void onClick(View v) {

    }
}
