package com.example.nasaspacesight.Room;

import androidx.room.Database;

import com.example.nasaspacesight.PojoModels.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.PojoModels.POJO_NIL.ItemOffline;
import com.example.nasaspacesight.PojoModels.Quiries.QueryAPOD;
import com.example.nasaspacesight.PojoModels.Quiries.QueryNIL;

@Database(entities = {ItemOffline.class, SingleApodResponse.class, QueryAPOD.class, QueryNIL.class},version = DatabaseInfo.DB_VERSION,exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase
{
    public abstract DaoRefrence getDao();
}
