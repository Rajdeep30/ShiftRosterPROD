package com.example.shiftroster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class LoadingBar extends AppCompatActivity {
    AlertDialog alertDialog;
    Context context;
    LoadingBar(Context context)
    {
        this.context = context;
        alertDialog = new AlertDialog.Builder(context).create();
    }

    public void showAlertDialog()
    {

        View customLayout = LayoutInflater.from(context).inflate(R.layout.activity_progress_bar, null);
        alertDialog.setView(customLayout);
        alertDialog.show();

    }

    public void dismissAlertDialog()
    {
        alertDialog.dismiss();
    }
}