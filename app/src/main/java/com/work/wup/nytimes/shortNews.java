package com.work.wup.nytimes;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Peti on 2018. 09. 22..
 * Ebben az osztályban hozom létre a cikkekhez tartozó adatokból a megjelenítendő felületet.
 */

public class shortNews {

    public shortNews(View v, LinearLayout news, Article article,int counter){
        LinearLayout group = new LinearLayout(v.getContext());
        group.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        layoutParams.setMargins(10,2,10,2);
        if(counter%2==0){
        group.setBackgroundResource(R.drawable.group_black);}
        else{
            group.setBackgroundResource(R.drawable.group);
        }
        group.setLayoutParams(layoutParams);

        LinearLayout data = new LinearLayout(v.getContext());
        data.setOrientation(LinearLayout.VERTICAL);
        data.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT,1));

            TextView title =new TextView(v.getContext());
            title.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            title.setTypeface(null, Typeface.BOLD);
            textColor(title,counter);
            title.setTextSize(16);
            title.setText(article.getTitle());

            TextView byline =new TextView(v.getContext());
            byline.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            byline.setTypeface(null, Typeface.ITALIC);
            textColor(byline,counter);
            byline.setText(article.getByline());

            LinearLayout dateGroup = new LinearLayout(v.getContext());
            dateGroup.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            dateParams.gravity=Gravity.END;
            dateGroup.setLayoutParams(dateParams);


                TextView dateText = new TextView(v.getContext());
                dateText.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                dateText.setText(article.getDate());
                textColor(dateText,counter);

                ImageView dateImage = new ImageView(v.getContext());
                dateImage.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                if(counter%2==0){
                    dateImage.setImageResource(R.drawable.ic_date_w);
                }else{
                    dateImage.setImageResource(R.drawable.ic_date);
                }


        LinearLayout.LayoutParams piece = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 150,4));
        piece.gravity= Gravity.CENTER;

        CircleImageView image = new CircleImageView(v.getContext());
        image.setLayoutParams(piece);
        image.setImageBitmap(article.getImage());

        ImageView arrow = new ImageView(v.getContext());
        arrow.setLayoutParams(piece);
        arrow.setTag(article.getUrl());
        if(counter%2==0){
            arrow.setImageResource(R.drawable.ic_arrow_w);
        }else{
            arrow.setImageResource(R.drawable.ic_arrow);
        }
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", (String)view.getTag());
                ((MainActivity)view.getContext()).sendDataFragment(fragmentWeb.class.getName(),new fragmentWeb(),bundle);
            }
        });

        dateGroup.addView(dateImage);
        dateGroup.addView(dateText);

        data.addView(title);
        data.addView(byline);
        data.addView(dateGroup);

        group.addView(image);
        group.addView(data);
        group.addView(arrow);


        news.addView(group);

    }
    private void textColor(TextView object, int counter){
        if(counter%2==1){
            object.setTextColor(Color.BLACK);
        }else{
            object.setTextColor(Color.WHITE);
        }
    }

}
