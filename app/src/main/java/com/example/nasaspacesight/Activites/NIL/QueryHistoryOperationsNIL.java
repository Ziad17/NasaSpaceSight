package com.example.nasaspacesight.Activites.NIL;

import com.example.nasaspacesight.PojoModels.Quiries.QueryNIL;

public interface QueryHistoryOperationsNIL
{
    void onHistoryDeleted(QueryNIL queryNIL);
    void onHistoryClicked(QueryNIL queryNIL);
    void onHistorySearch(QueryNIL queryNIL);
}
