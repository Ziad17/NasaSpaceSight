package com.example.nasaspacesight.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasaspacesight.Activites.APOD.QueryHistoryOperationsAPOD;
import com.example.nasaspacesight.PojoModels.Quiries.QueryAPOD;
import com.example.nasaspacesight.R;

import java.util.ArrayList;
import java.util.List;


public class QueryHistoryRecyclerAdapterAPOD extends RecyclerView.Adapter<QueryHistoryRecyclerAdapterAPOD.QueryViewHolderAPOD> {


    public List<QueryAPOD> getList() {
        return list;
    }

    public void setList(List<QueryAPOD> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    List<QueryAPOD> list;
    QueryHistoryOperationsAPOD operator;

    public QueryHistoryRecyclerAdapterAPOD(List<QueryAPOD> _list, QueryHistoryOperationsAPOD operator) {
        this.operator = operator;
        setList(_list);
    }

    @NonNull
    @Override
    public QueryViewHolderAPOD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.query_nil_item, parent, false);
        return new QueryViewHolderAPOD(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QueryViewHolderAPOD holder, int position) {

        QueryAPOD queryNIL = list.get(position);


        holder.date.setText(queryNIL.getSEARCH_DATE());



            holder.tagger.setText(queryNIL.getAPOD_START_DATE());


            if(queryNIL.getAPOD_END_DATE()!=null && !queryNIL.getAPOD_END_DATE().isEmpty())
            {
                holder.tagger.setText(holder.tagger.getText()+" To "+queryNIL.getAPOD_END_DATE());
            }

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    class QueryViewHolderAPOD extends RecyclerView.ViewHolder {

        View myView;
        ConstraintLayout mainLayout;
        ImageButton delete;
        ImageButton go;
        TextView tagger;
        TextView date;

        public QueryViewHolderAPOD(@NonNull View itemView) {
            super(itemView);
            BindViews(itemView);
            setClickListeners();

        }

        private void setClickListeners() {
            go.setOnClickListener(view -> operator.onHistorySearch(list.get(getAdapterPosition())));

            delete.setOnClickListener(view -> operator.onHistoryDeleted(list.get(getAdapterPosition())));

            mainLayout.setOnClickListener(view -> operator.onHistoryClicked(list.get(getAdapterPosition())));


        }

        private void BindViews(View itemView) {
            myView = itemView;
            mainLayout = myView.findViewById(R.id.query_nil_main);
            delete = myView.findViewById(R.id.delete);
            go = myView.findViewById(R.id.go);
            tagger = myView.findViewById(R.id.nil_query_tag);
            date = myView.findViewById(R.id.search_date);
        }
    }
}

