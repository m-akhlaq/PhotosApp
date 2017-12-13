package com.app.msa.androidphotos15;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import model.Album;
import model.Photo;

public class AddPhotoActivity extends AppCompatActivity {
    ArrayList<String> personTags = new ArrayList<>();
    ArrayList<String> locationTags = new ArrayList<>();
    String photoLocation;
    ImageView photoView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            photoLocation = bundle.getString("PHOTO");
        }
        photoView=findViewById(R.id.addEdit_photo_imageview);
        addLocationTag = findViewById(R.id.addLoc_button);
        addPersonTag = findViewById(R.id.addPerson_button);
        saveButton = findViewById(R.id.save_button);
        cancelButton=findViewById(R.id.cancel_button);
        defaultLocationText = findViewById(R.id.noTag);
        defaultPersonText=findViewById(R.id.noTag2);
        listOfLocationTagText=findViewById(R.id.current_location_tags);
        listOfPersonTagText=findViewById(R.id.current_person_tag);
        deleteLocationTag = findViewById(R.id.deleteLoc);
        deletePersonTag = findViewById(R.id.deletePerson_button);
        photoView.setScaleType(ImageView.ScaleType.FIT_XY);
        File imgFile = new File(photoLocation);
        if(imgFile.exists()){
            photoView.setImageURI(Uri.fromFile(imgFile));
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


        //Onclick listener for the save button
        saveButton.setOnClickListener(v ->{
            savePhoto();
        });

        cancelButton.setOnClickListener(v ->{
            setResult(RESULT_CANCELED);
            finish();
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

    }

    public void setTextViewText(ArrayList<String> list, TextView textView){
        String result="";
        for (String s:list){
            result=result+"  "+s;
        }
        textView.setText(result);
    }
    public void savePhoto(){
        Bundle bundle = new Bundle();
        bundle.putString("URL",photoLocation);
        bundle.putStringArrayList("LOCATION",locationTags);
        bundle.putStringArrayList("PERSON",personTags);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
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


}
