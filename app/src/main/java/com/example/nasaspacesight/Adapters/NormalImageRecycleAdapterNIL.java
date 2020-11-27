package com.example.nasaspacesight.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.R;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NormalImageRecycleAdapterNIL extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private static int NORMAL_IMAGE_TYPE=1;
    private static int LOADING_TYPE=2;

    public void setArrayOfItems(ArrayList<Item> arrayOfItems) {

        this.arrayOfItems=arrayOfItems;
        notifyDataSetChanged();
    }

    private ArrayList<Item> arrayOfItems;
    private Context context;
    private OnImageClickLisetenr onImageClickLisetenr;

    public NormalImageRecycleAdapterNIL(Context context, OnImageClickLisetenr _onImageClickLisetenr) {
        this.context = context;
        this.onImageClickLisetenr = _onImageClickLisetenr;
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayOfItems.get(position)==null)
        {
            return LOADING_TYPE;
        }
        else return NORMAL_IMAGE_TYPE;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if(viewType==LOADING_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_loading, parent, false);
            LoadingViewHolder normaImageViewHolder = new LoadingViewHolder(view);
            return normaImageViewHolder;
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_normal_image_nil, parent, false);
        NormaImageViewHolder normaImageViewHolder = new NormaImageViewHolder(view, onImageClickLisetenr);
        return normaImageViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        //TODO; how to convert from the images from glide to drawable


        if(getItemViewType(position)==LOADING_TYPE)
        {



        }
        else {
            try {
                String IMAGE_LINK = arrayOfItems.get(position).getLinks().getHref();
                NormaImageViewHolder imageHolder=(NormaImageViewHolder)holder;
                Glide.with(this.context)
                        .asBitmap()
                        .placeholder(R.drawable.ic_broken_image_black_24dp)
                        .centerCrop()
                        .load(IMAGE_LINK)
                        .apply(new RequestOptions().override(imageHolder.image.getWidth(),imageHolder.image.getHeight())).transform(new RoundedCorners(7))
                        .into(imageHolder.image);
                String TITLE = arrayOfItems.get(position).getData().getTitle();
                imageHolder.imageTitle.setText(TITLE);
                Log.d(TAG, "onBindViewHolder: ");
            }
            catch (NullPointerException e)
            {
                Toast.makeText(context,"Something Went Wrong, Please Check Your Internet Connection!!",Toast.LENGTH_LONG).show();
            }


        }





    }


    @Override
    public int getItemCount() {
        if(arrayOfItems!=null)
        {return arrayOfItems.size();}
        return 0;
    }

    public Item getItem(int position)
    {
        if(arrayOfItems!=null || arrayOfItems.size()==0)
        {
            return arrayOfItems.get(position);
        }
        return null;
    }



    class LoadingViewHolder extends RecyclerView.ViewHolder
    {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }



    class NormaImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        LinearLayout parentLinearLayout;
        ImageView image;
        OnImageClickLisetenr listener;
        TextView imageTitle;

        void bindViews(View view) {
            this.image = view.findViewById(R.id.normal_image);
            this.imageTitle = view.findViewById(R.id.normal_image_title);
            this.parentLinearLayout = view.findViewById(R.id.parent_linear_layout);
            view.setOnClickListener(this);


        }

        public NormaImageViewHolder(@NonNull View itemView, OnImageClickLisetenr onImageClick) {
            super(itemView);
            this.listener = onImageClick;
            bindViews(itemView);
            Log.d(TAG, "NormaImageViewHolder: ");
        }

        @Override
        public void onClick(View v) {
            listener.onImageClick(getAdapterPosition());

        }
    }

    public interface OnImageClickLisetenr {
        void onImageClick(int postion);


    }


}
