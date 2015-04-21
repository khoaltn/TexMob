package com.ebookfrenzy.finalproject;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class Home extends ActionBarActivity {

    public static File currentFile;

    public ListView list;

    public final static String EXTRA_MESSAGE = "com.ebookfrenzy.finalproject.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list = (ListView) findViewById(R.id.list);
        ArrayList<String> myFiles = new ArrayList<>();

        File directory;

        // Try to save file on External Storage. If unavailable, then save file on Internal Storage.
        // On emulator, the external storage does not really work, so for now we stick with the internal storage.

        /*if (isExternalStorageReadable() && isExternalStorageWritable()) {
            directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath(), "TexMob");
        }
        else*/ {
            directory = new File(getApplicationContext().getFilesDir().getPath(), "TexMob");
        }
        directory.mkdirs();

        if (directory.isDirectory()) {
            File[] contents = directory.listFiles();
            if (contents.length == 0) {
                currentFile = new File(directory, "your_first_file");
                myFiles.add(currentFile.getName());
            }
            else {
                for (int i = 0; i < contents.length; i++) { myFiles.add(contents[i].getName()); }
            }
        }
        else myFiles.add("No files found");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_selectable_list_item, myFiles);

        list.setAdapter(adapter);
    }


    // Call openHelpTutorial() when the user presses Help. This opens the HelpTutorial activity
    public void openHelpTutorial(View v) {
        Intent intent = new Intent(this, HelpTutorial.class);
        startActivity(intent);
    }

    public void openNew(View v) {
        Intent intent = new Intent(this, Edit.class);
        startActivity(intent);
    }

    // Called when the user presses Open.
    public void openExisting(View v) {
        list = (ListView) findViewById(R.id.list);

        // Add code to open existing file
        TextView selectedFile = (TextView) list.getChildAt(list.getCheckedItemPosition());
        String fileName = selectedFile.getText().toString();

        if (selectedFile == null || fileName.equals("No files found")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please choose an existing file or create a new one.", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Intent intent = new Intent(this, Edit.class);
            intent.putExtra(EXTRA_MESSAGE, fileName);
            startActivity(intent);
        }

    }
    //**********INCOMPLETE

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
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
