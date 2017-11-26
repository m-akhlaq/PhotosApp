package com.app.msa.androidphotos15;

import android.content.Context;
import android.content.DialogInterface;
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

import java.util.List;

import model.Album;
import model.User;

/**
 * Created by shaheer on 11/19/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView numOfPhotosTextView;
        ImageView albumPicture;
        ImageButton deleteButton;
        ImageButton editButton;
        public ViewHolder(View view){
            super(view);
            nameTextView = view.findViewById(R.id.album_name);
            numOfPhotosTextView = view.findViewById(R.id.album_count);
            albumPicture = view.findViewById(R.id.album_photo);
            deleteButton = view.findViewById(R.id.delete_button);
            editButton = view.findViewById(R.id.edit_button);
        }
    }
    private User user;
    private Context context;

    public AlbumAdapter(User user,Context context){
        this.user=user;
        this.context=context;
    }

    private Context getContext(){
        return context;
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View albumView = inflater.inflate(R.layout.album_viewholder,parent,false);
        ViewHolder viewHolder = new ViewHolder(albumView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Album album = user.getAlbums().get(position);
        // Set item views based on your views and data model
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(album.getName());

        TextView numberTextVIew = viewHolder.numOfPhotosTextView;
        numberTextVIew.setText("49");

        ImageView albumPicture = viewHolder.albumPicture;
        albumPicture.setImageResource(R.drawable.ic_album_new);

        ImageButton deleteButton = viewHolder.deleteButton;
        deleteButton.setOnClickListener( v->{
            new AlertDialog.Builder(getContext()).setTitle("Confirm your Action")
                    .setMessage("Do you really want delete this album?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            removeItems(position);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        });

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
