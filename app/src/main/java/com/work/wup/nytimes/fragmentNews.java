package com.work.wup.nytimes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Peti on 2018. 09. 22..
 */

public class fragmentNews extends Fragment {


    View v;
    //int newsCounter;
    LinearLayout news;
    Article[] articles;
    int[] findWord;
    int searchCounter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news, container, false);



        //newsCounter=20;

        news=v.findViewById(R.id.news);
        createNewsPlace();


        return v;
    }

    public void refresh(){

        if( news.getChildCount() > 0)
        {news.removeAllViews();}
        createNewsPlace();
    }

    public void search(String word){
        searchCounter=0;
        findWord=new int[articles.length];
        for(int i=0;i<articles.length;i++){
            findWord[i]=-1;
            if(articles[i].getTitle().contains(word) || articles[i].getByline().contains(word)
                    || articles[i].getDate().contains(word)){
                findWord[searchCounter]=i;
                searchCounter++;
            }
        }
        news.removeAllViews();
        findWord();
    }

    private void findWord(){
        if( searchCounter > 0) {
            for (int i = 0; i < searchCounter; i++) {
                //if (findWord[i] >= 0) {
                    new shortNews(v, news, articles[findWord[i]], i);
                //}
            }
        }
    }

    private void createNewsPlace(){
        //Bitmap image;
        articles=((MainActivity)getActivity()).getArticle();
        for(int i=0;i<articles.length;i++){
            /*image= BitmapFactory.decodeResource(v.getContext().getResources(),
                    R.drawable.nytimeslogo);*/
            new shortNews(v,news,articles[i],i);
        }
    }



}
