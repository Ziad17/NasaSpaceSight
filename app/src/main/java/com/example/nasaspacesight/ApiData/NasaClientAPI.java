package com.example.nasaspacesight.ApiData;

import androidx.annotation.Nullable;

import com.example.nasaspacesight.POJO.FullApiResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface NasaClientAPI {
    @GET("search")
    Call<FullApiResponse> Search(@Nullable @Query("q") String textQuery,
                                 @Nullable @Query("keywords") String keyWordsSplitByComma,
                                 @Nullable @Query("description") String Description,
                                 @Nullable @Query("location") String location,
                                 @Nullable @Query("nasa_id") String nasaId,
                                 @Nullable @Query("photographer") String photographer,
                                 @Nullable @Query("title") String Title,
                                 @Nullable @Query("page") Integer page,
                                 @Nullable @Query("media_type") String media_Type);
//nullable quiries  keywords,description,location,nasa_id,photographer,title
//non nullable start_year,end_year,media_type,page

    @GET("search")
    Call<FullApiResponse> Search(@Nullable @Query("q") String textQuery,
                                 @Nullable @Query("keywords") String keyWordsSplitByComma,
                                 @Nullable  @Query("description") String Description,
                                 @Nullable  @Query("location") String location,
                                 @Nullable  @Query("nasa_id") String nasaId,
                                 @Nullable  @Query("photographer") String photographer,
                                 @Nullable  @Query("title") String Title,
                                 @Nullable  @Query("year_start") Integer start_year,
                                 @Nullable  @Query("year_end") Integer end_year,
                                 @Nullable @Query("page") Integer page,
                                 @Nullable @Query("media_type") String media_type);


    @GET
    Call<ArrayList<String>> getImageLinks(@Url String uri);
}
