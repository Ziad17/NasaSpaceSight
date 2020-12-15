package com.example.nasaspacesight.Activites.NIL;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasaspacesight.Activites.HistoryBottomSheetDialog;
import com.example.nasaspacesight.Adapters.QueryHistoryRecycleAdapterNIL;
import com.example.nasaspacesight.PojoModels.Quiries.QueryNIL;
import com.example.nasaspacesight.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryBottomSheetDialogNIL extends HistoryBottomSheetDialog {


    private RecyclerView recyclerView;
    private List<QueryNIL> queryNILS;
    private QueryHistoryOperationsNIL operatorContext;
    private QueryHistoryRecycleAdapterNIL adapter;
    Context context;


    public HistoryBottomSheetDialogNIL(List<QueryNIL> list,QueryHistoryOperationsNIL queryHistoryOperationsNIL, @NonNull Context _context, int theme)  {
        super(_context, theme);
        setContentView(R.layout.bottm_dialog_sheet);

        queryNILS=list;
        operatorContext=queryHistoryOperationsNIL;
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
        adapter=new QueryHistoryRecycleAdapterNIL(queryNILS,operatorContext);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    private void BindViews()
    {
        recyclerView=findViewById(R.id.query_recycle_view);
    }


    public void updateList(List<QueryNIL> list)
    {
        queryNILS=list;
        adapter.setList((ArrayList<QueryNIL>) queryNILS);
    }

}
