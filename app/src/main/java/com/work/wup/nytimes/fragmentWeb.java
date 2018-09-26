package com.work.wup.nytimes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Peti on 2018. 09. 23..
 * Itt olvasható a teljes cikk.
 */

public class fragmentWeb extends Fragment {


    View v;
    WebView web;
    String url;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_web, container, false);

        url = getArguments().getString("url");
        web=v.findViewById(R.id.web);
        web.loadUrl(url);

        return v;
    }

    public void openBrowser(){
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browser);
    }

}
