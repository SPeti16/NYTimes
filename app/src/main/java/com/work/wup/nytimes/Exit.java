package com.work.wup.nytimes;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

/**
 * Created by Peti on 2018. 09. 23..
 */

public class Exit {

    Dialog dialog;
    AppCompatButton yes,no;

    public void showDialogExit(final Activity activity){
        dialog = new Dialog(activity);

        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);




        yes=dialog.findViewById(R.id.exitYes);
        no=dialog.findViewById(R.id.exitNo);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });


        dialog.show();

    }



}
