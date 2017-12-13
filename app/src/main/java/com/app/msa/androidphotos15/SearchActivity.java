package com.app.msa.androidphotos15;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import model.Album;
import model.Photo;
import model.User;

public class SearchActivity extends AppCompatActivity {
    ArrayList<String> personTags = new ArrayList<>();
    ArrayList<String> locationTags = new ArrayList<>();
    ArrayList<Photo> allPhotos = new ArrayList<Photo>();
    ImageButton addLocationTag;
    ImageButton addPersonTag;
    ImageButton deleteLocationTag;
    ImageButton deletePersonTag;
    TextView defaultLocationText;
    TextView defaultPersonText;
    TextView listOfLocationTagText;
    TextView listOfPersonTagText;
    Button saveButton;
    Button cancelButton;
    RecyclerView resultList;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        addLocationTag = findViewById(R.id.search_addLoc_button);
        addPersonTag = findViewById(R.id.search_addPerson_button);
        saveButton = findViewById(R.id.search_save_button);
        cancelButton=findViewById(R.id.search_cancel_button);
        defaultLocationText = findViewById(R.id.search_noTag);
        defaultPersonText=findViewById(R.id.search_noTag2);
        listOfLocationTagText=findViewById(R.id.search_current_location_tags);
        listOfPersonTagText=findViewById(R.id.search_current_person_tag);
        deleteLocationTag = findViewById(R.id.search_deleteLoc);
        deletePersonTag = findViewById(R.id.search_deletePerson_button);
        resultList=findViewById(R.id.search_resultList);
        resultList.setLayoutManager(new GridLayoutManager(this,2));
        user=readUser();
        for (Album a:user.getAlbums()){
            for (Photo p:a.getPhotosList()){
                allPhotos.add(p);
            }
        }
        addLocationTag.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Location Tag");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String result = input.getText().toString().toLowerCase();
                    if (result!=null && result.trim().length()>0){
                        if (locationTags.contains(result)==false) {
                            locationTags.add(result);
                            defaultLocationText.setVisibility(View.INVISIBLE);
                            listOfLocationTagText.setVisibility(View.VISIBLE);
                            setTextViewText(locationTags,listOfLocationTagText);
                        }else showErrorDialog("Tag already exists!");
                    }else showErrorDialog("Please add a valid tag");

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        });

        addPersonTag.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Person Tag");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String result = input.getText().toString().toLowerCase();
                    if (result!=null && result.trim().length()>0){
                        if (personTags.contains(result)==false) {
                            personTags.add(result);
                            defaultPersonText.setVisibility(View.INVISIBLE);
                            listOfPersonTagText.setVisibility(View.VISIBLE);
                            setTextViewText(personTags,listOfPersonTagText);
                        }else showErrorDialog("Tag already exists!");
                    }else showErrorDialog("Please add a valid tag");

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

        });
        deleteLocationTag.setOnClickListener(v->{
            if (locationTags.size()>0) {
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle("Select tag to delete");
                String[] items = new String[locationTags.size()];
                String[] items2 = {"Copy & Move","Cut & Move"};
                for (int x = 0; x < items.length; x++) {
                    items[x] = locationTags.get(x);
                }
                b.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        locationTags.remove(which);
                        setTextViewText(locationTags, listOfLocationTagText);
                        if (locationTags.size() == 0) {
                            listOfLocationTagText.setVisibility(View.INVISIBLE);
                            defaultLocationText.setVisibility(View.VISIBLE);
                        }
                    }
                });
                b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                b.show();
            }else showErrorDialog("No tags to delete");
        });

        deletePersonTag.setOnClickListener(v->{
            if (personTags.size()>0) {
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle("Select tag to delete");
                String[] items = new String[personTags.size()];
                for (int x = 0; x < items.length; x++) {
                    items[x] = personTags.get(x);
                }
                b.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        personTags.remove(which);
                        setTextViewText(personTags, listOfPersonTagText);
                        if (personTags.size() == 0) {
                            listOfPersonTagText.setVisibility(View.INVISIBLE);
                            defaultPersonText.setVisibility(View.VISIBLE);
                        }
                    }
                });
                b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                b.show();
            }else showErrorDialog("No tags to delete");
        });

        saveButton.setOnClickListener(v->{
            if (locationTags.size()>0||personTags.size()>0) {
                ArrayList<Photo> resultPhotos = new ArrayList<>();
                for (Photo p : allPhotos) {
                    if (checkLocation(p) && checkPerson(p)) {
                        resultPhotos.add(p);
                    }
                }
                SearchAdapter adapter = new SearchAdapter(resultPhotos, this);
                resultList.setAdapter(adapter);
            }else showErrorDialog("Add a tag before searching!");
        });
        cancelButton.setOnClickListener(v->{
            finish();
        });

    }

    public boolean checkLocation(Photo p){
        ArrayList<String> photoTags = p.getLocation();
        ArrayList<String> tags = locationTags;
        if (tags.isEmpty())
            return true;
        for (String t:tags){
            if (tagMatches(t,photoTags)==false){
                return false;
            }
        }
        return true;
    }
    public boolean checkPerson(Photo p){
        ArrayList<String> photoTags = p.getPerson();
        ArrayList<String> tags = personTags;
        if (tags.isEmpty())
            return true;
        for (String t:tags){
            if (tagMatches(t,photoTags)==false){
                return false;
            }
        }
        return true;
    }
    private boolean tagMatches(String s, ArrayList<String> list){
        for (String ls:list){
            if (ls.contains(s))
                return true;
        }
        return false;
    }
    public void setTextViewText(ArrayList<String> list, TextView textView){
        String result="";
        for (String s:list){
            result=result+"  "+s;
        }
        textView.setText(result);
    }

    private void showErrorDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(""+message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    private User readUser(){
        String filename = this.getFilesDir()+"data.ser";
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
