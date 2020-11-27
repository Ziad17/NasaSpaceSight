package com.example.nasaspacesight.ApiData.Images;

import androidx.annotation.Nullable;

import com.example.nasaspacesight.POJO_NIL.FullApiResponse;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ImagesClientAPI {

    String NIL_QUERY="q";
    String NIL_KEY_WORDS="keywords";
    String NIL_DESCRIPTION="description";
    String NIL_LOCATION="location";
    String NIL_NASA_ID="nasa_id";
    String NIL_PHOTOGRAPHER="photographer";
    String NIL_PAGE_NUMBER="page";
    String NIL_TITLE="title";
    String NIL_MEDIA_TYPE="media_type";
    String NIL_START_YEAR="year_start";
    String NIL_END_YEAR="year_end";
     String NASA_IMAGES_BASE_URL="https://images-api.nasa.gov/";

  /*  @GET("search")
    Call<FullApiResponse> Search(@Nullable @Query("q") String textQuery,
                                 @Nullable @Query("keywords") String keyWordsSplitByComma,
                                 @Nullable @Query("description") String Description,
                                 @Nullable @Query("location") String location,
                                 @Nullable @Query("nasa_id") String nasaId,
                                 @Nullable @Query("photographer") String photographer,
                                 @Nullable @Query("title") String Title,
                                 @Nullable @Query("page") Integer page,
                                 @Nullable @Query("media_type") String media_Type);*/
//nullable quiries  keywords,description,location,nasa_id,photographer,title
//non nullable start_year,end_year,media_type,page


    @GET("search")
    Call<FullApiResponse> Search(@Nullable @QueryMap Map<String,Object> options);

    @GET
    Call<ArrayList<String>> getImageLinks(@Url String uri);
}
