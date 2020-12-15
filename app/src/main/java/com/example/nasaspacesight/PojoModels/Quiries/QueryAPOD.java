package com.example.nasaspacesight.PojoModels.Quiries;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.nasaspacesight.ApiData.APOD.ApodClientAPI;
import com.example.nasaspacesight.ApiData.Images.ImagesClientAPI;

import java.util.HashMap;
import java.util.Map;

@Entity
public class QueryAPOD extends Query{



    @ColumnInfo(name = ApodClientAPI.APOD_START_DATE)
    String APOD_START_DATE;
    @ColumnInfo(name = ApodClientAPI.APOD_END_DATE)
    String APOD_END_DATE;





    public QueryAPOD(HashMap<String,Object> map)
    {
        super(map);
        setAPOD_END_DATE((String) map.get(ApodClientAPI.APOD_END_DATE));
        setAPOD_START_DATE((String) map.get(ApodClientAPI.APOD_START_DATE));

    }

    public QueryAPOD() {
        super(null);
    }





    public String getAPOD_START_DATE() {
        return APOD_START_DATE;
    }

    public void setAPOD_START_DATE(String APOD_START_DATE) {
        this.APOD_START_DATE = APOD_START_DATE;
    }

    public String getAPOD_END_DATE() {
        return APOD_END_DATE;
    }

    public void setAPOD_END_DATE(String APOD_END_DATE) {
        this.APOD_END_DATE = APOD_END_DATE;
    }

    @Override
    public HashMap<String, Object> toMap() {


        //TODO: This Method doesn't include any search by start_year and end_year

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put(ApodClientAPI.APOD_START_DATE,APOD_START_DATE);
        hashMap.put(ApodClientAPI.APOD_END_DATE,APOD_END_DATE);
        hashMap.put(ApodClientAPI.APOD_API_KEY,ApodClientAPI.APOD_API_KEY_VALUE);


        return hashMap;
    }
}
