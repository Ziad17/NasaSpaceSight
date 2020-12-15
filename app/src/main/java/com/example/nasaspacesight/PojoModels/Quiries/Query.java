package com.example.nasaspacesight.PojoModels.Quiries;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public abstract class Query implements QueryMapper {

    public static final String Search_Date="search_date";

    public String getSEARCH_DATE() {
        return SEARCH_DATE;
    }




    public void setSEARCH_DATE(String SEARCH_DATE) {
        this.SEARCH_DATE = SEARCH_DATE;
    }

    public HashMap<String, Object> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, Object> hashMap) {
        this.hashMap = hashMap;
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Search_Date)
    protected String SEARCH_DATE;

    public static String getSearch_Date() {
        return Search_Date;
    }

    @Ignore
    protected HashMap<String,Object> hashMap;

    public Query(HashMap<String,Object> map)
    {


        setHashMap(map);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setSEARCH_DATE(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString());
        }
    }
}
