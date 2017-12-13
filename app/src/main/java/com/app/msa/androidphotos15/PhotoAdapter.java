package com.app.msa.androidphotos15;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Photo;

/**
 * Created by shahe on 11/28/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    ArrayList<Photo> photoArrayList;
    Context context;
    int albumPosition;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView photoImageView;
        ImageButton deleteButton;
        ImageButton editButton;
        ImageButton moveButton;
        TextView caption;
        Activity activity;
        public ViewHolder(Context context,View view) {
            super(view);
            photoImageView = view.findViewById(R.id.photo_ImageView);
            deleteButton = view.findViewById(R.id.photo_deleteButton);
            editButton=view.findViewById(R.id.photo_editButton);
            moveButton = view.findViewById(R.id.photo_moveButton);
            caption=view.findViewById(R.id.photo_caption);
            view.setOnClickListener(this);
            activity=(Activity)getContext();
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION) {
                Photo p = photoArrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("PHOTO",p);
                Intent intent = new Intent(context,DisplayPhotoActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        }
    }

    /**
     *
     *   Bundle bundle = new Bundle();
     bundle.putInt("POSITION",position);
     bundle.putParcelableArrayList("LIST",photoArrayList);
     Intent intent = new Intent(getContext(), SlideshowActivity.class);
     intent.putExtras(bundle);
     activity.startActivity(intent);
     */
    public PhotoAdapter(ArrayList<Photo> l, Context c, int pos){
        photoArrayList=l;
        context=c;
        albumPosition=pos;
    }
    public Context getContext(){
        return context;
    }
    public int getAlbumPosition(){return albumPosition;}
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.photo_viewholder,parent,false);
        ViewHolder viewHolder = new ViewHolder(context,photoView);
        return viewHolder;

    }
    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {
        ImageView photoImageView = holder.photoImageView;
        Photo p = photoArrayList.get(position);
        photoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        File imgFile = new File(p.getUrl());
        if(imgFile.exists()){
            photoImageView.setImageURI(Uri.fromFile(imgFile));
        }
        TextView caption = holder.caption;
        caption.setText(imgFile.getName()+"");
        ImageButton deleteButton = holder.deleteButton;
        deleteButton.setOnClickListener( v->{
            //promots the user to confirm weather they really want to delete the photo and then calls the delete
            //photo method
            new AlertDialog.Builder(getContext()).setTitle("Confirm your Action")
                    .setMessage("Do you really want delete this photo?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            removeItems(position);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        });
        ImageButton editButton = holder.editButton;
        editButton.setOnClickListener(v->{
            Photo photoBeingEdited = photoArrayList.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("URL",photoBeingEdited.getUrl());
            bundle.putStringArrayList("LOCATION",photoBeingEdited.getLocation());
            bundle.putStringArrayList("PERSON",photoBeingEdited.getPerson());
            bundle.putInt("POSITION",position);
            Intent intent = new Intent(getContext(),EditPhotoActivity.class);
            intent.putExtras(bundle);
            holder.activity.startActivityForResult(intent,2);
        });
        ImageButton moveButton = holder.moveButton;
        moveButton.setOnClickListener(v->{
            Photo photoBeingMoved = photoArrayList.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("PHOTO",photoBeingMoved);
            bundle.putInt("PHOTOPOSITION",position);
            Log.e("Er",position+" adapter");
            bundle.putInt("ALBUMPOSITION",albumPosition);
            Intent intent = new Intent(getContext(),MovingPhotoActivity.class);
            intent.putExtras(bundle);
            holder.activity.startActivityForResult(intent,3);
        });

    }

    @Override
    public int getItemCount() {
        Log.e("Er",photoArrayList.size()+" ");
        return photoArrayList.size();
    }

    public void removeItems(int position){
        photoArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }
}
