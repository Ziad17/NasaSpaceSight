package com.example.nasaspacesight.PojoModels.Quiries;

import android.os.Build;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.nasaspacesight.ApiData.Images.ImagesClientAPI;
import com.google.gson.internal.bind.util.ISO8601Utils;

import org.w3c.dom.ls.LSOutput;

import java.util.HashMap;
import java.util.Map;

@Entity
public class QueryNIL extends Query{


    public QueryNIL() {
        super(new HashMap<String,Object>());
    }

    @Override
    public String toString() {
        return "QueryNIL{" +
                "NIL_QUERY='" + NIL_QUERY + '\'' +
                ", NIL_KEY_WORDS='" + NIL_KEY_WORDS + '\'' +
                ", NIL_DESCRIPTION='" + NIL_DESCRIPTION + '\'' +
                ", NIL_LOCATION='" + NIL_LOCATION + '\'' +
                ", NIL_PHOTOGRAPHER='" + NIL_PHOTOGRAPHER + '\'' +
                ", NIL_PAGE_NUMBER='" + NIL_PAGE_NUMBER + '\'' +
                ", NIL_TITLE='" + NIL_TITLE + '\'' +
                ", NIL_MEDIA_TYPE='" + NIL_MEDIA_TYPE + '\'' +
                '}';
    }

    @ColumnInfo(name = ImagesClientAPI.NIL_QUERY)
    String NIL_QUERY;
    @ColumnInfo(name = ImagesClientAPI.NIL_KEY_WORDS)
    String NIL_KEY_WORDS;
    @ColumnInfo(name = ImagesClientAPI.NIL_DESCRIPTION)
    String NIL_DESCRIPTION;
    @ColumnInfo(name = ImagesClientAPI.NIL_LOCATION)
    String NIL_LOCATION;
    @ColumnInfo(name = ImagesClientAPI.NIL_PHOTOGRAPHER)
    String NIL_PHOTOGRAPHER;
    @ColumnInfo(name = ImagesClientAPI.NIL_PAGE_NUMBER)
    String NIL_PAGE_NUMBER;
    @ColumnInfo(name = ImagesClientAPI.NIL_TITLE)
    String NIL_TITLE;
    @ColumnInfo(name = ImagesClientAPI.NIL_MEDIA_TYPE)
    String NIL_MEDIA_TYPE;

    public QueryNIL(HashMap<String, Object> map) {
        super(map);
        setNIL_QUERY((String) map.get(ImagesClientAPI.NIL_QUERY));
        setNIL_TITLE((String) map.get(ImagesClientAPI.NIL_TITLE));
        setNIL_DESCRIPTION((String) map.get(ImagesClientAPI.NIL_DESCRIPTION));
        setNIL_KEY_WORDS((String) map.get(ImagesClientAPI.NIL_KEY_WORDS));
        setNIL_LOCATION((String) map.get(ImagesClientAPI.NIL_LOCATION));
        setNIL_PHOTOGRAPHER((String) map.get(ImagesClientAPI.NIL_PHOTOGRAPHER));
        setNIL_PAGE_NUMBER( map.get(ImagesClientAPI.NIL_PAGE_NUMBER).toString());
        setNIL_MEDIA_TYPE((String) map.get(ImagesClientAPI.NIL_MEDIA_TYPE));

    }

    @Override
    public HashMap<String, Object> toMap() {
        //TODO: This Method doesn't include any search by start_year and end_year

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put(ImagesClientAPI.NIL_QUERY,NIL_QUERY);
        hashMap.put(ImagesClientAPI.NIL_KEY_WORDS,NIL_KEY_WORDS);
        hashMap.put(ImagesClientAPI.NIL_DESCRIPTION,NIL_DESCRIPTION);
        hashMap.put(ImagesClientAPI.NIL_LOCATION,NIL_LOCATION);
        hashMap.put(ImagesClientAPI.NIL_PHOTOGRAPHER,NIL_PHOTOGRAPHER);
        hashMap.put(ImagesClientAPI.NIL_PAGE_NUMBER,NIL_PAGE_NUMBER);
        hashMap.put(ImagesClientAPI.NIL_TITLE,NIL_TITLE);
        hashMap.put(ImagesClientAPI.NIL_MEDIA_TYPE,NIL_MEDIA_TYPE);


        return hashMap;
    }

    public String getNIL_QUERY() {
        return NIL_QUERY;
    }

    public void setNIL_QUERY(String NIL_QUERY) {
        this.NIL_QUERY = NIL_QUERY;
    }

    public String getNIL_KEY_WORDS() {
        return NIL_KEY_WORDS;
    }

    public void setNIL_KEY_WORDS(String NIL_KEY_WORDS) {
        this.NIL_KEY_WORDS = NIL_KEY_WORDS;
    }

    public String getNIL_DESCRIPTION() {
        return NIL_DESCRIPTION;
    }

    public void setNIL_DESCRIPTION(String NIL_DESCRIPTION) {
        this.NIL_DESCRIPTION = NIL_DESCRIPTION;
    }

    public String getNIL_LOCATION() {
        return NIL_LOCATION;
    }

    public void setNIL_LOCATION(String NIL_LOCATION) {
        this.NIL_LOCATION = NIL_LOCATION;
    }



    public String getNIL_PHOTOGRAPHER() {
        return NIL_PHOTOGRAPHER;
    }

    public void setNIL_PHOTOGRAPHER(String NIL_PHOTOGRAPHER) {
        this.NIL_PHOTOGRAPHER = NIL_PHOTOGRAPHER;
    }

    public String getNIL_PAGE_NUMBER() {
        return NIL_PAGE_NUMBER;
    }

    public void setNIL_PAGE_NUMBER(String NIL_PAGE_NUMBER) {
        this.NIL_PAGE_NUMBER = NIL_PAGE_NUMBER;
    }

    public String getNIL_TITLE() {
        return NIL_TITLE;
    }

    public void setNIL_TITLE(String NIL_TITLE) {
        this.NIL_TITLE = NIL_TITLE;
    }

    public String getNIL_MEDIA_TYPE() {
        return NIL_MEDIA_TYPE;
    }

    public void setNIL_MEDIA_TYPE(String NIL_MEDIA_TYPE) {
        this.NIL_MEDIA_TYPE = NIL_MEDIA_TYPE;
    }

}
