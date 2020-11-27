package com.example.nasaspacesight.Room;

import androidx.room.Database;

import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.POJO_NIL.ItemOffline;

@Database(entities = {ItemOffline.class, SingleApodResponse.class},version = DatabaseInfo.DB_VERSION,exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase
{
    public abstract DaoRefrence getDao();
}
