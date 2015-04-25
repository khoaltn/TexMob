package com.ebookfrenzy.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class Home extends ActionBarActivity {

    private ListView list;
    private ArrayList<String> myFiles = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public static File master, directory;

    public final static String EXTRA_MESSAGE = "com.ebookfrenzy.finalproject.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list = (ListView) findViewById(R.id.list);

        File currentFile;

        // Try to save file on External Storage. If unavailable, then save file on Internal Storage.
        // On emulator, the external storage does not really work, so for now we stick with the internal storage.

        /*if (isExternalStorageReadable() && isExternalStorageWritable()) {
            master = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), "TexMob");
        }
        else*/ {
            master = new File(getApplicationContext().getFilesDir().getAbsolutePath(), "TexMob");
        }
        master.mkdirs();

        // Create a folder for code files
        directory = new File(master.getAbsolutePath(), "Code");
        directory.mkdirs();

        if (directory.isDirectory()) {
            // Check for the files
            File[] contents = directory.listFiles();
            if (contents.length == 0) {
                currentFile = new File(directory.getAbsolutePath(), "your_first_file.txt");
                myFiles.add(currentFile.getName());

                // Write something into the first file
                try {
                    FileWriter ostream = new FileWriter(currentFile.getAbsolutePath(), true);
                    ostream.write("%Welcome to TexMob!");
                    ostream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                for (int i = 0; i < contents.length; i++) { myFiles.add(contents[i].getName()); }
            }
        }
        else myFiles.add("No files found");

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_selectable_list_item, myFiles);

        list.setAdapter(adapter);
    }


    // Call openHelpTutorial() when the user presses Help. This opens the HelpTutorial activity.
    public void openHelpTutorial(View v) {
        Intent intent = new Intent(this, HelpTutorial.class);
        startActivity(intent);
    }


    // Called when the user presses New.
    public void openNew(View v) {
        DialogAskNewFileName dialog = new DialogAskNewFileName();
        dialog.show(getFragmentManager(), "NewFile");
    }

    // Open chosen file when the user presses Open.
    public void openExisting(View v) {
        list = (ListView) findViewById(R.id.list);

        // to open existing file
        TextView selectedFile = (TextView) list.getChildAt(list.getCheckedItemPosition());
        if (selectedFile != null) {
            String fileName = selectedFile.getText().toString();
            File existingFile = new File(directory.getPath(), fileName);

            if (!existingFile.exists() || fileName.equals("No files found")) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please choose an existing file or create a new one.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Intent intent = new Intent(this, Edit.class);
                intent.putExtra(EXTRA_MESSAGE, fileName);
                startActivity(intent);
            }
        }
    }

    // Delete chosen file when the user presses Delete.
    public void deleteFile(View v) {
        list = (ListView) findViewById(R.id.list);
        int deletedFileIndex = list.getCheckedItemPosition();

        TextView selectedFile = (TextView) list.getChildAt(deletedFileIndex);

        if (selectedFile != null) {
            try {
                String fileName = selectedFile.getText().toString();
                File trash = new File(directory.getPath(), fileName);

                if (!trash.exists()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "File not found.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    trash.delete();

                    adapter.remove(myFiles.get(deletedFileIndex));
                    list.clearChoices();
                    adapter.setNotifyOnChange(true);
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Rename chosen file when user presses Rename.
    public void renameFile(View v) {
        list = (ListView) findViewById(R.id.list);
        final int renamedFileIndex = list.getCheckedItemPosition();
        final TextView selectedFile = (TextView) list.getChildAt(renamedFileIndex);

        if (selectedFile != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(builder.getContext());

            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String oldName = selectedFile.getText().toString();
                    File renamedFile = new File(directory.getPath(), oldName);

                    if (!renamedFile.exists()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "File not found.", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        String newName = input.getText().toString();
                        if (!Home.haveSameFileName(Home.directory, newName) && !newName.equals("")) {
                            File newNameFile = new File(directory.getPath(), newName);
                            boolean check = renamedFile.renameTo(newNameFile);

                            adapter.remove(myFiles.get(renamedFileIndex));
                            adapter.insert(newName, renamedFileIndex);

                            System.out.println(check);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Error renaming file. Try another name.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setMessage("Enter file's name: ");
            builder.create().show();

            adapter.setNotifyOnChange(true);
            adapter.notifyDataSetChanged();
        }
    }

    // Check for files with the same name in the folder
    public static boolean haveSameFileName(File dir, String fileName) {
        if (dir.isDirectory()) {
            File[] contents = dir.listFiles();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i].getName().equals(fileName)) return true;
            }
        }
        else if (dir.getName().equals(fileName)) return true;
        return false;
    }

    // Check for external storage
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
    // Check for external storage

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
