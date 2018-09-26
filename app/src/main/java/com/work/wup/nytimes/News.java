package com.work.wup.nytimes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Peti on 2018. 09. 21..
 * Ez az osztály a letöltött adatok feldolgozására szolgál.
 */

public class News {
    private String TAG = News.class.getSimpleName();
    private Article[] articles;
    private int newsCounter;
    Bitmap image;

    public News(String url){
        if (url != null) {
            try {

                JSONObject jsonObj = new JSONObject(url);

                JSONArray results = jsonObj.getJSONArray("results");

                newsCounter=results.length();
                articles=new Article[newsCounter];

                for (int i = 0; i < newsCounter; i++) {
                    JSONObject article = results.getJSONObject(i);
                    image=downloadImage(article.getJSONArray("media")
                                               .getJSONObject(0)
                                               .getJSONArray("media-metadata")
                                               .getJSONObject(1)
                                               .getString("url"));
                    articles[i]=new Article(article.getString("title"),
                                            article.getString("byline"),
                                            article.getString("url"),
                                            article.getString("published_date"),
                                            image);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }

    private Bitmap downloadImage(String link){
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            return null;
        }
    }

    public Article[] getArticles() {
        return articles;
    }
}
