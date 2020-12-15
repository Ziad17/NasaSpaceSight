package com.example.nasaspacesight.Util;

import com.example.nasaspacesight.PojoModels.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.PojoModels.POJO_NIL.ItemOffline;
import com.example.nasaspacesight.PojoModels.DataWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.example.nasaspacesight.PojoModels.DataWrapperStatus.SUCCESSED;

public abstract class Constants {
    public static final int SEARCH_START_YEAR = 1900;
    public static final int POOL_THREAD_NUMBER_FOR_NETWORK_IO=3;
    public static final int NETWORK_REQUEST_TIME_OUT_IN_MS=10000;
    public static final int HTTP_OK_CODE=200;
    public static final int MY_PERMISSION_REQUEST=100;
    public static final int BYTE_READER_CAPACITY=4096;
    public static final String GITHUB_LINK="https://github.com/Ziad17/NasaSpaceSight";


    @Deprecated
    public static  List<ItemOffline> ARRAY;
    @Deprecated
    public static DataWrapper<List<ItemOffline>> getARRAY()
    {
        ARRAY=new ArrayList<ItemOffline>(){};
        ARRAY.add(new ItemOffline("2020-10-1","First Image","file:///android_asset/2.jpg"));
        ARRAY.add(new ItemOffline("2020-10-2","2 Image","file:///android_asset/3.jpg"));
        ARRAY.add(new ItemOffline("2020-10-3","3 Image","file:///android_asset/4.jpg"));
        ARRAY.add(new ItemOffline("2020-10-4","4 Image","file:///android_asset/7.jpg"));
        ARRAY.add(new ItemOffline("2020-10-5","5 Image","file:///android_asset/8.jpg"));
        return new DataWrapper<>(ARRAY,SUCCESSED,"");
    }
    @Deprecated
    public static  List<SingleApodResponse> ARRAY_1;
    @Deprecated
    public static DataWrapper<List<SingleApodResponse>> getARRAY_1()
    {
        ARRAY_1=new ArrayList<SingleApodResponse>(){};
        ARRAY_1.add(new SingleApodResponse("2020-10-1","First Image","file:///android_asset/2.jpg"));
        ARRAY_1.add(new SingleApodResponse("2020-10-2","2 Image","file:///android_asset/3.jpg"));
        ARRAY_1.add(new SingleApodResponse("2020-10-3","3 Image","file:///android_asset/4.jpg"));
        ARRAY_1.add(new SingleApodResponse("2020-10-4","4 Image","file:///android_asset/7.jpg"));
        ARRAY_1.add(new SingleApodResponse("2020-10-5","5 Image","file:///android_asset/8.jpg"));
        return new DataWrapper<>(ARRAY_1,SUCCESSED,"");
    }


}
