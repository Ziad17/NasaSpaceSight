package com.example.nasaspacesight.Activites.APOD;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasaspacesight.Activites.HistoryBottomSheetDialog;
import com.example.nasaspacesight.Adapters.QueryHistoryRecycleAdapterNIL;
import com.example.nasaspacesight.Adapters.QueryHistoryRecyclerAdapterAPOD;
import com.example.nasaspacesight.PojoModels.Quiries.QueryAPOD;
import com.example.nasaspacesight.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryBottomSheetDialogAPOD extends HistoryBottomSheetDialog {

    private RecyclerView recyclerView;
    private List<QueryAPOD> queryAPODS;
    private QueryHistoryOperationsAPOD operatorContext;
    private QueryHistoryRecyclerAdapterAPOD adapter;
    Context context;


    public HistoryBottomSheetDialogAPOD(List<QueryAPOD> list, QueryHistoryOperationsAPOD queryHistoryOperationsAPOD, @NonNull Context _context, int theme)  {
        super(_context, theme);
        setContentView(R.layout.bottm_dialog_sheet);
        queryAPODS=list;
        operatorContext=queryHistoryOperationsAPOD;
        context=_context;
        init();

    }

    private void init()
    {
        BindViews();
        initRecycleView();

    }

    private void initRecycleView()
    {
        adapter=new QueryHistoryRecyclerAdapterAPOD(queryAPODS,operatorContext);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    private void BindViews()
    {
        recyclerView=findViewById(R.id.query_recycle_view);
    }


    public void updateList(List<QueryAPOD> list)
    {
        queryAPODS=list;
        adapter.setList((ArrayList<QueryAPOD>) queryAPODS);
    }
}
