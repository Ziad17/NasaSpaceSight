package com.example.nasaspacesight.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasaspacesight.R;

import java.util.ArrayList;

public class PagesReyclerAdapterNIL extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    Context context;
    OnPageNumberClickListner listner;

    public ArrayList<Integer> getPages() {
        return pages;
    }

    ArrayList<Integer> pages;
    int SELECTED_PAGE;
    int PREVIOUS_PAGE;

    public PagesReyclerAdapterNIL(Context context, OnPageNumberClickListner listner) {
        this.context = context;
        this.listner = listner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.page_number_card,parent,false);
        PageViewHolder pageViewHolder=new PageViewHolder(view);
        return pageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(pages!=null)
        {
            PageViewHolder holder1=(PageViewHolder)holder;
            holder1.page_number.setText(String.valueOf(pages.get(position)));
            holder1.page_number.bringToFront();
            if(position+1==SELECTED_PAGE && SELECTED_PAGE!=PREVIOUS_PAGE)
            {
             //   holder1.page_number.setTextColor(context.getColor(R.color.colorAccent));
            }
            else
            {
                //holder1.page_number.setTextColor(context.getColor(R.color.colorPrimary));

            }

        }

    }

    @Override
    public int getItemCount() {
        if(pages!=null)
        {
            return pages.size();
        }
        return 0;
    }

    public void setPages(ArrayList<Integer> pages, int current_page) {
        this.pages = pages;
        SELECTED_PAGE=current_page;
        notifyDataSetChanged();
    }

    class PageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public Button page_number;
        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            page_number=itemView.findViewById(R.id.page_number_button);
            page_number.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listner.OnPageClick(getAdapterPosition());
            PREVIOUS_PAGE=SELECTED_PAGE;
            SELECTED_PAGE=getAdapterPosition()+1;

        }
    }


    public interface OnPageNumberClickListner
    {
        void OnPageClick(int postion);
    }

}

