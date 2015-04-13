package com.ebookfrenzy.finalproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class HelpTutorial extends ActionBarActivity {

    ViewGroup container;
    Transition next;
    Scene tutorial1, tutorial2, tutorial3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_tutorial);

        container = (ViewGroup) findViewById(R.id.container);
        next = TransitionInflater.from(this).inflateTransition(R.transition.transition_next);

        tutorial1 = Scene.getSceneForLayout(container, R.layout.activity_help_tutorial1, this);
        tutorial2 = Scene.getSceneForLayout(container, R.layout.activity_help_tutorial2, this);
        tutorial3 = Scene.getSceneForLayout(container, R.layout.activity_help_tutorial3, this);
        tutorial1.enter();
    }

    public void openTutorial2(View v) {
        TransitionManager.go(tutorial2, next);
    }

    public void openTutorial3(View v) {
        TransitionManager.go(tutorial3, next);
    }

    public void openHomeFromTutorial(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help_tutorial, menu);
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
     * A placeholder fragment containing a simple view.
     */

}
