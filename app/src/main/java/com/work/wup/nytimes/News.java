package com.work.wup.nytimes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Peti on 2018. 09. 21..
 */

public class News {
    private String TAG = News.class.getSimpleName();
    private Article[] articles;
    private int newsCounter;
    Bitmap image;
    //private String title, byline, url;
    //private Date date;

    public News(String url){
        if (url != null) {
            try {

                //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                //SimpleDateFormat jsonFormat = new SimpleDateFormat(
                  //      "EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US);
                //jsonFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));



                JSONObject jsonObj = new JSONObject(url);

                JSONArray results = jsonObj.getJSONArray("results");

                //JSONArray contacts = jsonObj.getJSONArray("contacts");

                newsCounter=results.length();
                articles=new Article[newsCounter];

                //String date;
                //Date jsonDate;

                for (int i = 0; i < newsCounter; i++) {
                    JSONObject article = results.getJSONObject(i);
                    //date=article.getString("published_date");
                    //sonDate = jsonFormat.parse(date);
                    //date=format.format(jsonDate);
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
                    /*String user = c.getString("user");
                    String id = c.getString("id");
                    String title = c.getString("title");
                    String orientation = c.getString("orientation");

                        /*JSONObject gestures = c.getJSONObject("gestures");
                        String fullscreen = gestures.getString("fullscreen");
                        String target = gestures.getString("target");
                        String show = gestures.getString("show");
                        String posx = gestures.getString("pos-x");
                        String posy = gestures.getString("pos-y");
                        String gestsize = gestures.getString("gest-size");
                        String timer = gestures.getString("timer");
                        String attack = gestures.getString("attack");
                        String type = gestures.getString("type");

                    String status = c.getString("status");
                    String shortTitle = c.getString("shortTitle");
                    String subTitle = c.getString("subTitle");
                    String appStoreLink = c.getString("appStoreLink");
                    String playStoreLink = c.getString("playStoreLink");
                    String menuPos = c.getString("menuPos");
                    String videoUploaded = c.getString("videoUploaded");
                    String isTest = c.getString("isTest");

                    HashMap<String, String> contact = new HashMap<>();

                    contact.put("id", id);
                    contact.put("title", title);
                    contact.put("shortTitle", shortTitle);
                    contact.put("picture", "http://demos.atmosplay.com/"+shortTitle+"/icon.png");*/

                    //contactList.add(contact);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            //} catch (ParseException e) {
              //  e.printStackTrace();
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
            // Log exception
            return null;
        }
    }

    public Article[] getArticles() {
        return articles;
    }
}
