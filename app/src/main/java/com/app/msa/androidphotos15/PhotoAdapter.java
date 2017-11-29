package com.app.msa.androidphotos15;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import model.Photo;

/**
 * Created by shahe on 11/28/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    ArrayList<Photo> photoArrayList;
    Context context;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(Context context,View view){
            super(view);
        }
    }

    public PhotoAdapter(ArrayList<Photo> l, Context c){
        photoArrayList=l;
        context=c;
    }
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.activity_album,parent,false);
        ViewHolder viewHolder = new ViewHolder(context,photoView);
        return viewHolder;

    }
    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
