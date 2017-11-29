package com.app.msa.androidphotos15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import model.Album;
import model.Photo;

public class PhotoActivity extends AppCompatActivity {


    ArrayList<Photo> photoList = new ArrayList<>();
    int position=0;
    RecyclerView photosView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        photosView=findViewById(R.id.photoList);
        photosView.setLayoutManager(new GridLayoutManager(getContext(),2));
        Bundle bundle = getIntent().getExtras();
       if (bundle!=null){
           ArrayList<Photo> p = bundle.getParcelableArrayList("PHOTOS");
            position = bundle.getInt("POSITION");
            photoList=p;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               photoList.add(new Photo("Heay"));
            }
        });


    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("PHOTOS",photoList);
        bundle.putInt("POSITION",position);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        Log.e("hello","onBackPressedRan");
        finish();
    }

    public Context getContext(){return this;}
}
