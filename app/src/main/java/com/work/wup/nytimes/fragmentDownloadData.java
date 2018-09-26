package com.work.wup.nytimes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Peti on 2018. 09. 21..
 * Ez a fragment azt a célt szolgálja, hogy megmutathassa a Json adatok letöltésének állapotát.
 */

public class fragmentDownloadData extends Fragment{

    View v;
    int downloadCounter;
    ProgressBar loading;
    TextView loadingText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_download_data, container, false);

        downloadCounter=0;
        loading=v.findViewById(R.id.loadingBar);
        loadingText=v.findViewById(R.id.loadingText);
        ((MainActivity)getActivity()).startDownload();
        return v;
    }

    public void addDownloadCounter(){
        downloadCounter++;
        loading.setProgress(downloadCounter);
    }

}
