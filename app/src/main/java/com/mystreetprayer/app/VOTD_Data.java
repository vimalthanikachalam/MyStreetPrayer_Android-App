package com.mystreetprayer.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VOTD_Data extends AsyncTask<Context, Void, String> {

    private String verseData = "";
    private String dailyverse = "";
    private String verseauthor = "";


    @Override
    protected String doInBackground(Context... params) {

        try {
            URL url = new URL("https://beta.ourmanna.com/api/v1/get/?format=json");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";

            while (line != null){

                line = bufferedReader.readLine();
                verseData  = verseData + line;
            }


            JSONObject mainObject = new JSONObject(verseData).getJSONObject("verse");
            JSONObject verseObject = mainObject.getJSONObject("details");



            dailyverse = verseObject.getString("text");
            verseauthor = verseObject.getString("reference");


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(String.valueOf(aVoid));

        Fragment_Home.dailyVerse.setText(this.dailyverse);
        Fragment_Home.verseAuthor.setText(this.verseauthor);

    }
}
