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
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import model.Photo;

public class SlideshowActivity extends AppCompatActivity {


    int position;
    ArrayList<Photo> listOfPhotos;
    ImageView fullPhotoView;
    Photo currentPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        //grabs the information sent from the PhotoActivity
        if (bundle!=null){
            position = bundle.getInt("POSITION");
            listOfPhotos = bundle.getParcelableArrayList("LIST");
        }
        fullPhotoView = findViewById(R.id.fullphoto);
        Photo currentPhoto = listOfPhotos.get(position);
        fullPhotoView.setScaleType(ImageView.ScaleType.FIT_XY);
        File imgFile = new File(currentPhoto.getUrl());
        if(imgFile.exists()){
            fullPhotoView.setImageURI(Uri.fromFile(imgFile));
        }
        FloatingActionButton leftFab = (FloatingActionButton) findViewById(R.id.leftfab);
        leftFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==0)
                    position=listOfPhotos.size()-1;
                else position=position-1;
                Photo currentPhoto = listOfPhotos.get(position);
                File imgFile = new File(currentPhoto.getUrl());
                if(imgFile.exists()){
                    fullPhotoView.setImageURI(Uri.fromFile(imgFile));
                }
            }
        });
        FloatingActionButton rightFab = (FloatingActionButton) findViewById(R.id.rightfab);
        rightFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==listOfPhotos.size()-1)
                    position=0;
                else position=position+1;
                Photo currentPhoto = listOfPhotos.get(position);
                File imgFile = new File(currentPhoto.getUrl());
                if(imgFile.exists()){
                    fullPhotoView.setImageURI(Uri.fromFile(imgFile));
                }
            }
        });
        FloatingActionButton infoFab = (FloatingActionButton) findViewById(R.id.infofab);
        infoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo photo = listOfPhotos.get(position);
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
