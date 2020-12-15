package com.example.nasaspacesight.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nasaspacesight.PojoModels.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.PojoModels.POJO_NIL.ItemOffline;
import com.example.nasaspacesight.PojoModels.Quiries.QueryAPOD;
import com.example.nasaspacesight.PojoModels.Quiries.QueryNIL;

import java.util.List;

import retrofit2.http.DELETE;


@Dao
public interface DaoRefrence{
    @Insert
    void insertImage(ItemOffline image);
    @Insert
    void insertImage(SingleApodResponse image);

    @Delete
    void removeImage(SingleApodResponse singleApodResponse);
    @Delete
    void removeImage(ItemOffline singleApodResponse);

    @Query("select * from itemoffline")
    List<ItemOffline> getNILitems();
    @Query("select * from singleapodresponse")
    List<SingleApodResponse> getAPODitems();

    @Query("select * from singleapodresponse where "+DatabaseInfo.ApodTable.APOD_OBJECT_DATE+" = :date")
    SingleApodResponse getAPODitem(String date);

    @Query("select * from itemoffline where "+DatabaseInfo.NilTable.NIL_OBJECT_DATA_NASA_ID+" = :nasa_id")
    ItemOffline getNILitem(String nasa_id);


    @Query("delete from itemoffline")
    void deleteAllNIL();

    @Query("delete from singleapodresponse")
    void deleteAllAPOD();


    @Query("select * from QueryNIL")
    List<QueryNIL> searchHistoryNIL();

    @Query("select * from QueryAPOD")
    List<QueryAPOD> searchHistoryAPOD();


    @Delete
    void deleteHistoryAPOD(QueryAPOD apod);

    @Delete
    void deleteHistoryNIL(QueryNIL apod);

    @Query("delete from QueryAPOD")
    void deleteAllHistoryAPOD();

    @Query("delete from QueryNIL")
    void deleteAllHistoryNIL();

    @Insert
    void insertHistoryAPOD(QueryAPOD queryAPOD);

    @Insert
    void insertHistoryNIL(QueryNIL queryNIL);
}
