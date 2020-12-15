package com.example.nasaspacesight.Activites.APOD;

import com.example.nasaspacesight.PojoModels.Quiries.QueryAPOD;
import com.example.nasaspacesight.PojoModels.Quiries.QueryNIL;

public interface QueryHistoryOperationsAPOD {
    void onHistoryDeleted(QueryAPOD queryAPOD);
    void onHistoryClicked(QueryAPOD queryAPOD);
    void onHistorySearch(QueryAPOD queryNIL);

}
