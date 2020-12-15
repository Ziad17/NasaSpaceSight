package com.example.nasaspacesight.Room;

public interface DatabaseInfo {
    String DB_NAME="db_images";
    int DB_VERSION = 6;


    interface NilTable {
        String NIL_TABLE = "nil_table";
        String NIL_OBJECT_DATA = "nil_object_data";
        String NIL_OBJECT_HREF = "nil_object_href";
        String NIL_OBJECT_LINKS = "nil_object_links";
        String NIL_OBJECT_DATA_TITLE = "nil_object_data_title";
        String NIL_OBJECT_DATA_NASA_ID = "nil_object_data_nasa_id";
        String NIL_OBJECT_DATA_CENTER = "nil_object_data_center";
        String NIL_OBJECT_DATA_DATE_CREATED = "nil_object_data_date_created";
        String NIL_OBJECT_DATA_PHOTOGRAPHER = "nil_object_data_photographer";
        String NIL_OBJECT_MEDIA_TYPE = "nil_object_data_media_type";
        String NIL_OBJECT_DATA_DESCRIPTION = "nil_object_data_description";
        String NIL_OBJECT_IS_FAV = "nil_object_is_fav";
        String NIL_OBJECT_REL = "nil_object_rel";
        String NIL_OBJECT_HREF_LINKS = "nil_object_href_links";
        String NIL_OBJECT_RENDER = "nil_object_render";

    }
    interface ApodTable
    {
        String APOD_TABLE="apod_table";
        String APOD_OBJECT_COPYRIGHT="apod_object_copyright";
        String APOD_OBJECT_DATE="apod_object_date";
        String APOD_OBJECT_EXPLAINATION="apod_object_explanation";
        String APOD_OBJECT_HDURL="apod_object_hdurl";
        String APOD_OBJECT_MEDIA_TYPE="apod_object_media_type";
        String APOD_OBJECT_TITLE="apod_object_title";
        String APOD_OBJECT_URL="apod_object_url";
        String APOD_OBJECT_IS_FAV = "apod_object_is_fav";


    }


}
