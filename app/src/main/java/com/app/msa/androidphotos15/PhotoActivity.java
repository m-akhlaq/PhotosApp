package com.app.msa.androidphotos15;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

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
    FilePickerDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        runOnStop=true;
        photosView=findViewById(R.id.photoList);
        //sets up the properties for the file picker
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File("/mnt/sdcard");
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;
        dialog = new FilePickerDialog(this,properties);
        dialog.setTitle("Select a File");
        //the onclick listener that responds to a file being selected
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                Bundle bundle = new Bundle();
                bundle.putString("PHOTO",files[0]);
                Intent intent = new Intent(getContext(),AddPhotoActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
                /**photoList.add(new Photo(files[0]));
                PhotoAdapter photoAdapter = new PhotoAdapter(photoList,getContext());
                photosView.setAdapter(photoAdapter);**/
            }
        });
        photosView.setLayoutManager(new GridLayoutManager(getContext(),2));
        Bundle bundle = getIntent().getExtras();
        //grabs the information sent from the AlbumActivity
       if (bundle!=null){
           ArrayList<Photo> p = bundle.getParcelableArrayList("PHOTOS");
            position = bundle.getInt("POSITION");
            photoList=p;
            user=bundle.getParcelable("USER");
        }
        //sets up adapters and toolbar
        PhotoAdapter photoAdapter = new PhotoAdapter(photoList,this,position);
        photosView.setAdapter(photoAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        //sends the information back to the main activity when the back button is pressed
        user.getAlbums().get(position).getPhotosList().clear();
        user.getAlbums().get(position).getPhotosList().addAll(photoList);
        setResult(RESULT_OK);
        Log.e("hello","onBackPressedRan");
        runOnStop=false;
        writeUser(user);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //only runs if the activity ends in any way other than the back button being pressed. Serializes the User data
        if (runOnStop) {
            user.getAlbums().get(position).getPhotosList().clear();
            user.getAlbums().get(position).getPhotosList().addAll(photoList);
            writeUser(user);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.photo_search) {
            Intent intent = new Intent(getContext(),SearchActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.photo_slideshow) {
            if (photoList.size()!=0) {
                Bundle bundle = new Bundle();
                bundle.putInt("POSITION", 0);
                bundle.putParcelableArrayList("LIST", photoList);
                Intent intent = new Intent(getContext(), SlideshowActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Er","Inside Activity result code"+resultCode+" "+requestCode);

        if (requestCode==1){
            Log.e("Er","Inside result code");
            if (resultCode==RESULT_OK && data!=null){
                Log.e("Er","Inside result OK, data recived");
                Bundle bundle = data.getExtras();
                String url = bundle.getString("URL");
                ArrayList<String> locationTags = bundle.getStringArrayList("LOCATION");
                ArrayList<String> personTags = bundle.getStringArrayList("PERSON");
                Photo photo = new Photo(url);
                photo.getLocation().addAll(locationTags);
                photo.getPerson().addAll(personTags);
                photoList.add(photo);
                PhotoAdapter photoAdapter = new PhotoAdapter(photoList,getContext(),position);
                photosView.setAdapter(photoAdapter);
            }
        }
        if (requestCode==2){
            Log.e("Er","Inside result code");
            if (resultCode==RESULT_OK && data!=null){
                Bundle bundle = data.getExtras();
                int position = bundle.getInt("POSITION");
                Photo p = photoList.get(position);
                ArrayList<String> locationTags = bundle.getStringArrayList("LOCATION");
                ArrayList<String> personTags = bundle.getStringArrayList("PERSON");
                p.getPerson().clear();
                p.getLocation().clear();
                p.getLocation().addAll(locationTags);
                p.getPerson().addAll(personTags);
                PhotoAdapter photoAdapter = new PhotoAdapter(photoList,getContext(),position);
                photosView.setAdapter(photoAdapter);
            }

        }

        if (requestCode==3){
            if (resultCode==RESULT_OK){
             if (data!=null) {
                 Bundle b = data.getExtras();
                 if (b!=null){
                     int position = b.getInt("PHOTOPOSITION");
                     Log.e("Er",position+" result");
                     photoList.remove(position);
                     PhotoAdapter photoAdapter = new PhotoAdapter(photoList,getContext(),position);
                     photosView.setAdapter(photoAdapter);
                 }
             }
             user=readUser();
            }
        }
    }


    /**
     * Context for easy access
     * @return Context of the current activity
     */
    public Context getContext(){return this;}

    /**
     *
     * @param u user who you want to be serialzied
     */
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
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(dialog!=null)
                    {   //Show dialog if the read permission has been granted.
                        dialog.show();
                    }
                }
                else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(this,"Permission is Required for getting list of files", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
