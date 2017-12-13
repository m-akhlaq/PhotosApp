package com.app.msa.androidphotos15;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Album;
import model.Photo;
import model.User;

public class MovingPhotoActivity extends AppCompatActivity {


    Spinner albumSpinner;
    Button doneButton;
    Button cancelButton;
    ImageView photoImageView;
    RadioGroup radioGroup;
    RadioButton cutRadioButton;
    RadioButton copyRadioButton;
    Photo photo;
    User user;
    int photoPosition;
    int albumPosition;
    ArrayList<String> listOfAlbums =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_photo);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            photo = bundle.getParcelable("PHOTO");
            photoPosition=bundle.getInt("PHOTOPOSITION");
            albumPosition=bundle.getInt("ALBUMPOSITION");
        }


        photoImageView=findViewById(R.id.move_imageView);
        photoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        File imgFile = new File(photo.getUrl());
        if(imgFile.exists()){
            photoImageView.setImageURI(Uri.fromFile(imgFile));
        }
        user = readUser();
       /** for (Album a:user.getAlbums()){
            listOfAlbums.add(a.getName());
        }*/
       for (int x=0;x<user.getAlbums().size();x++){
           if (x!=albumPosition){
               listOfAlbums.add(user.getAlbums().get(x).getName());
           }
       }
        albumSpinner = findViewById(R.id.move_spinner);
        doneButton=findViewById(R.id.move_button);
        cancelButton=findViewById(R.id.move_cancel_button);
        copyRadioButton = findViewById(R.id.move_copy_button);
        cutRadioButton=findViewById(R.id.move_cut_button);
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listOfAlbums);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        albumSpinner.setAdapter(adapter);
        cancelButton.setOnClickListener(v->{
            setResult(RESULT_CANCELED);
            finish();
        });
        doneButton.setOnClickListener(v->{
            int position=albumSpinner.getSelectedItemPosition();
            if(position>=albumPosition)
                position++;
            Log.e("Er","Album Pos: "+albumPosition+" spinner Pos "+position);
            if (copyRadioButton.isChecked()){
                user.getAlbums().get(position).addPhoto(photo);
                writeUser(user);
                setResult(RESULT_OK);
                finish();
            }else if (cutRadioButton.isChecked()){
                user.getAlbums().get(position).addPhoto(photo);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("PHOTOPOSITION",photoPosition);
                Intent intent = new Intent();
                intent.putExtras(bundle1);
                writeUser(user);
                setResult(RESULT_OK,intent);
                finish();

            }



        });


    }

    public Context getContext(){
        return this;
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
}
