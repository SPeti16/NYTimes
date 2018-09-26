package com.work.wup.nytimes;

import android.graphics.Bitmap;

/**
 * Created by Peti on 2018. 09. 22..
 * Ez az osztály a Json-ról leszedett adatok tárolására szolgál.
 */

public class Article {

    private String title, byline, url, date;
    Bitmap image;

    public Article(String t, String b, String u, String d, Bitmap i){
        title=t;
        byline=b;
        url=u;
        date=d;
        image=i;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getByline() {
        return byline;
    }

    public String getUrl() {
        return url;
    }

    public Bitmap getImage(){return image;}
}
