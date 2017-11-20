package com.app.msa.androidphotos15;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shaheer on 11/19/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView dateTextView;
        TextView numOfPhotosTextView;
        public ViewHolder(View view){
            super(view);
            nameTextView = view.findViewById(R.id.nameTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            numOfPhotosTextView = view.findViewById(R.id.dateTextView);
        }
    }
    private List<Album> listOfAlbums;
    private Context context;

    public AlbumAdapter(List<Album> listOfAlbums,Context context){
        this.listOfAlbums=listOfAlbums;
        this.context=context;
    }

    private Context getContext(){
        return context;
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View albumView = inflater.inflate(R.layout.albumrview,parent,false);
        ViewHolder viewHolder = new ViewHolder(albumView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Album contact = listOfAlbums.get(position);
        // Set item views based on your views and data model
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(contact.getName());
        TextView dateTextView = viewHolder.dateTextView;
        dateTextView.setText("Sat, November 3rd 2017");
        TextView numberTextVIew = viewHolder.numOfPhotosTextView;
        numberTextVIew.setText("49");

    }


    @Override
    public int getItemCount(){
        return listOfAlbums.size();
    }


}
