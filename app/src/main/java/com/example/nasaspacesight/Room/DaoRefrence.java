package com.example.nasaspacesight.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.POJO_NIL.ItemOffline;

import java.util.List;


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

}
