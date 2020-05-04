package com.mystreetprayer.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VOTD_Data extends AsyncTask<Void, Void, Void> {

    private String verseData = "";
    private String dailyverse = "";
    private String verseauthor = "";
    private String dailVersePref = "";
    private String verseAuthorPref = "";
    private SharedPreferences sharedPreferences;

    private Context context;

    public VOTD_Data(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        dailVersePref = sharedPreferences.getString("dailyverse", "");
        verseAuthorPref = sharedPreferences.getString("verseauthor", "");
    }


    @Override
    protected Void doInBackground(Void... voids) {

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

            sharedPreferences
                    .edit()
                    .putString("dailyverse", dailyverse)
                    .putString("verseauthor", verseauthor)
                    .apply();


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Fragment_Home.dailyVerse.setText(dailVersePref);
        Fragment_Home.verseAuthor.setText(verseAuthorPref);

    }
}
