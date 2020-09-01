package com.example.nasaspacesight.Executors;

import com.example.nasaspacesight.Util.Constants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static volatile AppExecutors appExecutors;

    public ScheduledExecutorService getworkIO() {
        return networkIO;
    }

    private final ScheduledExecutorService networkIO;


    private AppExecutors()
    {
        networkIO = Executors.newScheduledThreadPool(Constants.POOL_THREAD_NUMBER_FOR_NETWORK_IO);
    }

    public static AppExecutors getInstance()
    {
        if(appExecutors==null)
        {
            synchronized (AppExecutors.class)
            {
                appExecutors=new AppExecutors();
            }
        }
        return appExecutors;
    }

}
