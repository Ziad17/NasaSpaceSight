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

import com.example.nasaspacesight.Activites.NIL.QueryHistoryOperationsNIL;
import com.example.nasaspacesight.PojoModels.Quiries.QueryNIL;
import com.example.nasaspacesight.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.nasaspacesight.Activites.NIL.ImageDetailsActivityNIL.TAG;

public class QueryHistoryRecycleAdapterNIL extends RecyclerView.Adapter<QueryHistoryRecycleAdapterNIL.QueryViewHolderNIL> {


    public List<QueryNIL> getList() {
        return list;
    }

    public void setList(List<QueryNIL> list) {
        this.list = list;
        Log.e(TAG, "setList: "+"Changed" );
        notifyDataSetChanged();
    }

    List<QueryNIL> list;
    QueryHistoryOperationsNIL operator;

    public QueryHistoryRecycleAdapterNIL(List<QueryNIL> _list, QueryHistoryOperationsNIL operator) {
        this.operator = operator;
        setList(_list);
    }

    @NonNull
    @Override
    public QueryViewHolderNIL onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.query_nil_item,parent,false);
        return  new QueryViewHolderNIL(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QueryViewHolderNIL holder, int position)

    {

        QueryNIL queryNIL=list.get(position);


        holder.date.setText(queryNIL.getSEARCH_DATE());









        ArrayList<String> taggerList=new ArrayList<>();
        taggerList.add(queryNIL.getNIL_QUERY());
        taggerList.add(queryNIL.getNIL_TITLE());
        taggerList.add(queryNIL.getNIL_KEY_WORDS());
        taggerList.add(queryNIL.getNIL_PHOTOGRAPHER());
        boolean EMPTY=true;
        try {
            for (String i :taggerList)
            {
                if(!i.isEmpty())
                {
                    holder.tagger.setText(i);
                    EMPTY=false;
                    break;
                }
            }
            if(EMPTY){holder.tagger.setText("Empty Tag");}
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if(list!=null)
        {return list.size();}
        return 0;
    }





    class QueryViewHolderNIL extends RecyclerView.ViewHolder
    {

        View myView;
        ConstraintLayout mainLayout;
        ImageButton delete;
        ImageButton go;
        TextView tagger;
        TextView date;

        public QueryViewHolderNIL(@NonNull View itemView) {
            super(itemView);
            BindViews(itemView);
            setClickListeners();

        }

        private void setClickListeners()
        {
            go.setOnClickListener(view -> operator.onHistorySearch(list.get(getAdapterPosition())));

            delete.setOnClickListener(view -> operator.onHistoryDeleted(list.get(getAdapterPosition())));

            mainLayout.setOnClickListener(view -> operator.onHistoryClicked(list.get(getAdapterPosition())));



        }

        private void BindViews(View itemView)
        {
            myView=itemView;
            mainLayout=myView.findViewById(R.id.query_nil_main);
            delete=myView.findViewById(R.id.delete);
            go=myView.findViewById(R.id.go);
            tagger=myView.findViewById(R.id.nil_query_tag);
            date=myView.findViewById(R.id.search_date);
        }
    }
}
