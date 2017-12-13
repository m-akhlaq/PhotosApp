package com.app.msa.androidphotos15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Photo;

/**
 * Created by shahe on 12/12/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgView;
        Activity activity;
        public ViewHolder(Context context, View view){
            super(view);
            imgView=view.findViewById(R.id.result_Image);
            view.setOnClickListener(this);
            activity=(Activity)context;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Photo p = photoArrayList.get(position);
            if (position!=RecyclerView.NO_POSITION) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("PHOTO",p);
                Intent intent = new Intent(context,DisplayPhotoActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        }
    }

    ArrayList<Photo> photoArrayList = new ArrayList<>();
    Context context;
    public SearchAdapter(ArrayList<Photo> p, Context c){
        photoArrayList=p;
        context=c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.search_result_viewholder,parent,false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(context,photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView imgView = holder.imgView;
        Photo p = photoArrayList.get(position);
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        File imgFile = new File(p.getUrl());
        if(imgFile.exists()){
            imgView.setImageURI(Uri.fromFile(imgFile));
        }

    }
    @Override
    public int getItemCount() {
        return photoArrayList.size();
    }
}
