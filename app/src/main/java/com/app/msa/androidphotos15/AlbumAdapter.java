package com.app.msa.androidphotos15;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.Photo;
import model.User;

/**
 * @author Muhammad Akhlaq
 * This class is the custom adapter to the recyclerview used in AlbumActivity
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    /**
     * This inner class defines the Viewholder for each element of the recyclerview
     */
    private User user;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nameTextView;
        TextView numOfPhotosTextView;
        ImageView albumPicture;
        ImageButton deleteButton;
        ImageButton editButton;
        Context context;
        Activity activity;

        /**
         *
         * @param view the inflated view in which all the widgets and elements reside
         */
        public ViewHolder(View view, Context context){
            super(view);
            nameTextView = view.findViewById(R.id.album_name);
            numOfPhotosTextView = view.findViewById(R.id.album_count);
            albumPicture = view.findViewById(R.id.album_photo);
            deleteButton = view.findViewById(R.id.delete_button);
            editButton = view.findViewById(R.id.edit_button);
            this.context=context;
            view.setOnClickListener(this);
            activity=(Activity)getContext();
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION) {
                Bundle bundle = new Bundle();
                Album a = user.getAlbums().get(position);
                ArrayList<Photo> list = a.getPhotosList();
                bundle.putParcelableArrayList("PHOTOS",list);
                bundle.putInt("POSITION",position);
                Intent intent = new Intent(getContext(),PhotoActivity.class);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent,1);
                }
        }


    }
    /**
     * initializes the context and the user
     * @param user the user whose albums these are
     * @param context the context Acitivity in which all this is happening
     */
    public AlbumAdapter(User user,Context context){
        this.user=user;
        this.context=context;
    }

    /**
     * returns the context for easy access
     * @return Context of the view
     */
    private Context getContext(){
        return context;
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View albumView = inflater.inflate(R.layout.album_viewholder,parent,false);
        ViewHolder viewHolder = new ViewHolder(albumView,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.ViewHolder viewHolder, int position) {
        // Get the album based on the position
        Album album = user.getAlbums().get(position);
        // Set item views to the name of the album
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(album.getName());
        //sets the text for the number of photos in the album
        TextView numberTextView = viewHolder.numOfPhotosTextView;
        String numOfPhotos="Contains " + album.getPhotosList().size() + " photos";
        numberTextView.setText(numOfPhotos);
        //sets the text for the static album icon
        ImageView albumPicture = viewHolder.albumPicture;
        albumPicture.setImageResource(R.drawable.ic_album_new);
        //sets up the delete button and its click listener
        ImageButton deleteButton = viewHolder.deleteButton;
        deleteButton.setOnClickListener( v->{
            //promots the user to confirm weather they really want to delete the photo and then calls the delete
            //photo method
            new AlertDialog.Builder(getContext()).setTitle("Confirm your Action")
                    .setMessage("Do you really want delete this album?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            removeItems(position);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        });
        //sets up the edit button and pops up a dialog to get the edited name
        ImageButton editButton = viewHolder.editButton;
        editButton.setOnClickListener(v->{
            String originalName = album.getName();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Enter New Album Name");
            final EditText input = new EditText(getContext());
            input.setText(originalName);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String result = input.getText().toString();
                    if (result!=null && result.trim().length()>0){
                        if (user.nameExists(result)==false) {
                            album.setName(result);
                            notifyDataSetChanged();
                        }else if(result.toLowerCase().equals(originalName.toLowerCase())){
                            //do nothing
                        }else{
                            showDuplicateNameDialog();
                        }

                    }else{
                        showInvalidNameDialog();
                    }
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


        

    }


    @Override
    public int getItemCount(){
        return user.getAlbums().size();
    }

    private void removeItems(int position){
        user.getAlbums().remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }

    private void showInvalidNameDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please enter a valid name!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void showDuplicateNameDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("The album name already exists!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}
