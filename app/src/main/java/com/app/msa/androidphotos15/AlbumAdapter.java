package com.app.msa.androidphotos15;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView dateTextView;
        TextView numOfPhotosTextView;
        ImageView albumPicture;
        ImageButton deleteButton;
        public ViewHolder(View view){
            super(view);
            nameTextView = view.findViewById(R.id.album_name);
            dateTextView = view.findViewById(R.id.album_date);
            numOfPhotosTextView = view.findViewById(R.id.album_count);
            albumPicture = view.findViewById(R.id.album_photo);
            deleteButton = view.findViewById(R.id.delete_button);
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

        TextView dateTextView = viewHolder.dateTextView;
        dateTextView.setText("Sat, November 3rd 2017");

        TextView numberTextVIew = viewHolder.numOfPhotosTextView;
        numberTextVIew.setText("49");

        ImageView albumPicture = viewHolder.albumPicture;
        albumPicture.setImageResource(R.drawable.ic_black_album);

        ImageButton deleteButton = viewHolder.deleteButton;
        deleteButton.setOnClickListener( v->{
            removeItems(position);
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


}
