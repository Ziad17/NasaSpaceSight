package com.example.nasaspacesight.ApiData.APOD;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.nasaspacesight.Executors.AppExecutors;
import com.example.nasaspacesight.PojoModels.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.Util.Constants;
import com.example.nasaspacesight.PojoModels.DataWrapper;
import com.example.nasaspacesight.PojoModels.DataWrapperStatus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Response;

import static com.example.nasaspacesight.Util.Constants.HTTP_OK_CODE;
import static com.example.nasaspacesight.PojoModels.DataWrapperStatus.FAILED;
import static com.example.nasaspacesight.PojoModels.DataWrapperStatus.LOADING;
import static com.example.nasaspacesight.PojoModels.DataWrapperStatus.SUCCESSED;

public class ApodClient {

    private static final String TAG = "Sssss";
    private static volatile ApodClient instance;

    public MutableLiveData<DataWrapper<List<SingleApodResponse>>> getItems() {
        return items;
    }

    private MutableLiveData<DataWrapper<List<SingleApodResponse>>> items;

    public MutableLiveData<DataWrapper<SingleApodResponse>> getSingleItem() {
        return singleItem;
    }

    private MutableLiveData<DataWrapper<SingleApodResponse>> singleItem;

    private ApodClient()
    {
        items=new MutableLiveData<>();
        singleItem=new MutableLiveData<>();
    }


    public static ApodClient getInstance() {
        if (instance == null) {
            synchronized (ApodClient.class) {
                instance = new ApodClient();
            }
        }
        return instance;
    }


    public void searchForImages(Map<String,Object> objectMap)
    {
        items.postValue(new DataWrapper<>(null, LOADING, null));

        Future future= AppExecutors.getInstance().getworkIO().submit(() -> {
            try {
                boolean CANCELED=false;
                Response response = ApodResources.getInstance().connect().getImages(objectMap).execute();

                if (CANCELED) {
                    items.postValue(new DataWrapper<>(items.getValue().getCollection(), DataWrapperStatus.CANCELED, "Request Canceled"));
                    return;
                }
                if (response.code() == HTTP_OK_CODE) {

                    List<SingleApodResponse> fullApiResponse = (List<SingleApodResponse>) response.body();
                    items.postValue(new DataWrapper<>(fullApiResponse, SUCCESSED, null));
                    System.out.println(response.raw());
                }
                else if(response.code()!=HTTP_OK_CODE)
                {                items.postValue(new DataWrapper<>(items.getValue().getCollection(), FAILED, response.message()));
                }

            } catch (Exception e) {
                items.postValue(new DataWrapper<>(items.getValue().getCollection(), FAILED, "Connection Is Down"));
                Log.e(TAG, "searchForImages: ",e );
            }
        });


        AppExecutors.getInstance().getworkIO().schedule(() -> {
            future.cancel(true);
        }, Constants.NETWORK_REQUEST_TIME_OUT_IN_MS, TimeUnit.MILLISECONDS);



    }

   /* public void searchForImage(Map<String,Object> objectMap)
    {

        Future future= AppExecutors.getInstance().getworkIO().submit(() -> {
            try {
                boolean CANCELED=false;
                Response response = ApodResources.getInstance().connect().getImage(objectMap).execute();
                if (CANCELED) {
                    singleItem.postValue(new DataWrapper<SingleApodResponse>(null, DataWrapperStatus.CANCELED,null));
                    return;
                }
                if (response.code() == HTTP_OK_CODE) {
                    SingleApodResponse fullApiResponse = (SingleApodResponse) response.body();
                    singleItem.postValue(new DataWrapper<SingleApodResponse>(fullApiResponse,SUCCESSED,null));
                    System.out.println(response.raw());
                }

            } catch (IOException e) {
                singleItem.postValue(new DataWrapper<SingleApodResponse>(null,FAILED,null));
            }
        });



        //TODO: Write Code For Cancellation
            }


*/

}
