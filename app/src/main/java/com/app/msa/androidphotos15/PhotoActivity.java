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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Album;
import model.Photo;
import model.User;

public class PhotoActivity extends AppCompatActivity {


    ArrayList<Photo> photoList = new ArrayList<>();
    int position=0;
    RecyclerView photosView;
    User user = null;
    boolean runOnStop=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        runOnStop=true;
        //photosView=findViewById(R.id.photoList);
       // photosView.setLayoutManager(new GridLayoutManager(getContext(),2));
        Bundle bundle = getIntent().getExtras();
       if (bundle!=null){
           ArrayList<Photo> p = bundle.getParcelableArrayList("PHOTOS");
            position = bundle.getInt("POSITION");
            photoList=p;
        }
        user=readUser();

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
        runOnStop=false;
        finish();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (runOnStop) {
            user.getAlbums().get(position).getPhotosList().clear();
            user.getAlbums().get(position).getPhotosList().addAll(photoList);
            writeUser(user);
        }
    }

    public Context getContext(){return this;}

    private void writeUser(User u){
        String filename = getContext().getFilesDir()+"data.ser";
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(u);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private User readUser(){
        String filename = getContext().getFilesDir()+"data.ser";
        FileInputStream fis = null;
        ObjectInputStream in = null;
        User tempUser=null;
        File f = new File(filename);

        try {
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            tempUser = (User) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tempUser;
    }
}
