package com.app.msa.androidphotos15;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import model.Photo;

public class DisplayPhotoActivity extends AppCompatActivity {

    Photo photo;
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            photo = bundle.getParcelable("PHOTO");
        }
        imgView = findViewById(R.id.display_photo);
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        File imgFile = new File(photo.getUrl());
        if(imgFile.exists()){
            imgView.setImageURI(Uri.fromFile(imgFile));
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messege = "Name:"+imgFile.getName()+"\n"+
                                "Location Tags: "+photo.getLocation()+"\n"+
                        "Person Tags:"+photo.getPerson();
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Information");
                alertDialog.setMessage(""+messege);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    public Context getContext(){
        return this;
    }

}
