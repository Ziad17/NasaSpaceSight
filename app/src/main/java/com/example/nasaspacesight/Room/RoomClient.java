package com.example.nasaspacesight.Room;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.nasaspacesight.Executors.AppExecutors;
import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.POJO_NIL.ItemOffline;
import com.example.nasaspacesight.ViewModels.DataWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.example.nasaspacesight.ViewModels.DataWrapperStatus.FAILED;
import static com.example.nasaspacesight.ViewModels.DataWrapperStatus.LOADING;
import static com.example.nasaspacesight.ViewModels.DataWrapperStatus.SUCCESSED;

public class RoomClient {


    private static final String TAG = "RoomCLient";
    private static volatile RoomClient instance;

    public MutableLiveData<DataWrapper<List<SingleApodResponse>>> getArrayAPOD() {
        return arrayAPOD;
    }

    private MutableLiveData<DataWrapper<List<SingleApodResponse>>> arrayAPOD;


    public MutableLiveData<SingleApodResponse> getItemAPOD() {
        return itemAPOD;
    }

    private MutableLiveData<SingleApodResponse> itemAPOD;

    public MutableLiveData<ItemOffline> getItemNIL() {
        return itemNIL;
    }

    private MutableLiveData<ItemOffline> itemNIL;

    public static String getTAG() {
        return TAG;
    }

    public MutableLiveData<DataWrapper<List<Item>>> getArrayNIL() {
        return arrayNIL;
    }

    private MutableLiveData<DataWrapper<List<Item>>> arrayNIL;

    private RoomClient() {
        arrayAPOD = new MutableLiveData<>();
        arrayNIL = new MutableLiveData<>();
        itemAPOD=new MutableLiveData<>();
        itemNIL=new MutableLiveData<>();

    }

    public static RoomClient getInstance() {
        if (instance == null) {
            synchronized (RoomClient.class) {
                instance = new RoomClient();
            }
        }
        return instance;
    }


    public void searchCacheNIL(RoomDatabase database)
    {
                   arrayNIL.postValue(new DataWrapper<>(null,LOADING,""));

        AppExecutors.getInstance().getworkIO().submit(() -> {
            //try{
                List<ItemOffline> itemsOffline=database.getDao().getNILitems();
                List<Item> items=new ArrayList<>();
                for(ItemOffline i :itemsOffline)
                {
                    items.add(i.toNILitem());
                }
                arrayNIL.postValue(new DataWrapper<>(items,SUCCESSED,""));
           // }
            /*catch (Exception e)
            {

                arrayNIL.postValue(new DataWrapper<>(null,SUCCESSED,e.getMessage()));
            }*/

        });
    }
    public void searchCacheAPOD(RoomDatabase database)
    {
        arrayAPOD.postValue(new DataWrapper<>(null,LOADING,""));

        AppExecutors.getInstance().getworkIO().submit(() -> {
            try{
                List<SingleApodResponse> items=database.getDao().getAPODitems();
                arrayAPOD.postValue(new DataWrapper<>(items,SUCCESSED,""));
            }
            catch (Exception e)
            {
                arrayAPOD.postValue(new DataWrapper<>(null,FAILED,e.getMessage()));
            }

        });
    }
    public void insert(SingleApodResponse singleApodResponse,RoomDatabase database)
    {
        AppExecutors.getInstance().getworkIO().submit(() -> {
            try{
                database.getDao().insertImage(singleApodResponse);

            }
            catch (Exception e)
            {
                Log.e(TAG, "insert: ",e );
            }

        });
    }
    public void delete(SingleApodResponse singleApodResponse,RoomDatabase database)
    {
        AppExecutors.getInstance().getworkIO().submit(() -> {
            try{
                database.getDao().removeImage(singleApodResponse);

            }
            catch (Exception e)
            {
                Log.e(TAG, "insert: ",e );
            }

        });
    }
    public void insert(Item item,RoomDatabase database)
    {
        AppExecutors.getInstance().getworkIO().submit(() -> {
            try{
                database.getDao().insertImage(ItemOffline.convertNILtoNILoffline(item));

            }
            catch (Exception e)
            {
                Log.e(TAG, "insert: ",e );
            }

        });
    }
    public void delete(Item item,RoomDatabase database)
    {
        AppExecutors.getInstance().getworkIO().submit(() -> {
            try{
                database.getDao().removeImage(ItemOffline.convertNILtoNILoffline(item));

            }
            catch (Exception e)
            {
                Log.e(TAG, "insert: ",e );
            }

        });
    }


    public void checkAPODItem(String date, RoomDatabase database)
    {

        itemAPOD.postValue(null);
        AppExecutors.getInstance().getworkIO().submit(() -> itemAPOD.postValue(database.getDao().getAPODitem(date)));
    }

    public void checkNILItem(String nasa_id,RoomDatabase database) {
        itemNIL.postValue(null);
        AppExecutors.getInstance().getworkIO().submit(() -> itemNIL.postValue(database.getDao().getNILitem(nasa_id)));
    }
}
