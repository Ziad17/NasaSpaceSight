package com.example.nasaspacesight.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.nasaspacesight.PojoModels.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.R;

import java.util.List;


public class NormalImageRecyclerAdapterAPOD extends RecyclerView.Adapter<NormalImageRecyclerAdapterAPOD.ImageViewHolder> {


    public List<SingleApodResponse> getImages() {
        return images;
    }

    public void setImages(List<SingleApodResponse> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    List<SingleApodResponse> images;
    Context context;
    NormalImageRecycleAdapterNIL.OnImageClickLisetenr lisetenr;

    public NormalImageRecyclerAdapterAPOD(Context context, NormalImageRecycleAdapterNIL.OnImageClickLisetenr lisetenr) {
        this.context = context;
        this.lisetenr = lisetenr;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_item_normal_image_apod,parent,false),lisetenr);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        SingleApodResponse image=images.get(position);
        Glide.with(this.context)
                .asBitmap()
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .centerCrop()
                .load(image.getUrl())
                .apply(new RequestOptions().override(holder.image.getWidth(),holder.image.getHeight())).transform(new RoundedCorners(7))
                .into(holder.image);
        String TITLE = images.get(position).getTitle();
        holder.imageTitle.setText(TITLE);
        holder.dateTextView.setText(image.getDate());

    }

    public SingleApodResponse getItem(int position)
    {
        if(images!=null || images.size()==0)
        {
            return images.get(position);
        }
        return null;
    }


    @Override
    public int getItemCount() {
        if(images!=null) return images.size();
        return 0;
    }


    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout parentLinearLayout;
        ImageView image;
        NormalImageRecycleAdapterNIL.OnImageClickLisetenr listener;
        TextView imageTitle;
        TextView dateTextView;

        void bindViews(View view) {
            this.image = view.findViewById(R.id.normal_image);
            this.imageTitle = view.findViewById(R.id.normal_image_title);
            this.parentLinearLayout = view.findViewById(R.id.parent_linear_layout);
            this.dateTextView=view.findViewById(R.id.date_text_view);
            view.setOnClickListener(this);
        }

        public ImageViewHolder(@NonNull View itemView, NormalImageRecycleAdapterNIL.OnImageClickLisetenr onImageClick) {
            super(itemView);
            this.listener = onImageClick;
            bindViews(itemView);
        }

        @Override
        public void onClick(View v) {
            listener.onImageClick(getAdapterPosition());

        }
    }


}

