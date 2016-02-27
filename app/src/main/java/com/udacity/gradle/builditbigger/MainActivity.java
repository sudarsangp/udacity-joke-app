package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.MyJoke;
import com.example.sudarsan.androidjoke.JokeActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EndpointsAsyncTask sampleTask = new EndpointsAsyncTask();
        sampleTask.execute(new Pair<Context, String>(this, "GPS"));
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

    public void launchLibraryActivity(View view){
        new GetJoke().execute("");
    }

    public class GetJoke extends AsyncTask<String, Void, String> {
        private JsonGetTaskListener mListener = null;
        private Exception mError = null;

        @Override
        protected String doInBackground(String... params){
            MyJoke joke = new MyJoke();
            String jokeContent = joke.getJoke();
            return jokeContent;
        }

        @Override
        protected void onPostExecute(String result){
            if (this.mListener != null)
                this.mListener.onComplete(result, mError);
            Intent myIntent = new Intent(getBaseContext(), JokeActivity.class);
            myIntent.putExtra("joke", result);
            startActivity(myIntent);
        }

        public GetJoke setListener(JsonGetTaskListener listener) {
            this.mListener = listener;
            return this;
        }


    }
    public interface JsonGetTaskListener {
        void onComplete(String jsonString, Exception e);
    }
}
