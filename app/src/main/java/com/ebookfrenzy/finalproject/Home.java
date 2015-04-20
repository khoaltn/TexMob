package com.ebookfrenzy.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class Home extends ActionBarActivity {

    public static File currentFile;

    public ListView listView;

    public final static String EXTRA_MESSAGE = "com.ebookfrenzy.finalproject.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (ListView) findViewById(R.id.list);

        // Try to save file on External Storage. If unavailable, then save file on Internal Storage.
        if (isExternalStorageReadable() && isExternalStorageWritable()) {
            currentFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "untitled");
        }
        else {
            currentFile = new File(getApplicationContext().getFilesDir(), "untitled");
        }
        currentFile.setWritable(true);
        Toast toast = Toast.makeText(getApplicationContext(), currentFile.getPath().toString(), Toast.LENGTH_SHORT);
        toast.show();

        FileOutputStream ostream;

        try {
            ostream = openFileOutput(currentFile.getName(), Context.MODE_PRIVATE);
            ostream.write("This is a test".getBytes());
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ArrayList<String> myFiles = new ArrayList<>();

// INCOMPLETE: For all files in Directory, add file name
        myFiles.add(currentFile.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_selectable_list_item, myFiles);

        listView.setAdapter(adapter);
    }


    // Call openHelpTutorial() when the user presses Help. This opens the HelpTutorial activity
    public void openHelpTutorial(View v) {
        Intent intent = new Intent(this, HelpTutorial.class);
        startActivity(intent);
    }

//**********INCOMPLETE
    // Call openEdit() when the user presses New or Open.
    public void openEdit(View v) {
        listView = (ListView) findViewById(R.id.list);
        Button buttonNew = (Button) findViewById(R.id.buttonNew);
        Button buttonOpen = (Button) findViewById(R.id.buttonOpen);

        if (v == buttonNew) {
// Add code to create new file
            Intent intent = new Intent(this, Edit.class);
            startActivity(intent);
        }
        else if (v == buttonOpen) {
// Add code to open existing file
            String selectedFile = (String) listView.getSelectedItem();
            if (selectedFile != "") {
                currentFile = new File(getApplicationContext().getFilesDir(), selectedFile);

                Intent intent = new Intent(this, Edit.class);
                intent.putExtra(EXTRA_MESSAGE, currentFile.getName());
                startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Please choose an existing file on your left.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
//**********INCOMPLETE

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
