package com.example.nasaspacesight.POJO;

import android.util.Log;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

/****
 * TODO: this class absolutely needs to be refactored but it will do just fine for now :)
 */
public class ImageLinks {
    public ArrayList<String> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }
    public ImageLinks()
    {}

    public ArrayList<String> links;
    public String ORIGINAL;
    public String LARGE;

    public ImageLinks(ArrayList<String> arrayList)
    {
        this.links=arrayList;
            Log.d(TAG, "ImageLinks:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+links.size());
            for (String tempHolder:links)
            {
                if (tempHolder.contains("~orig"))
                {
                    ORIGINAL=tempHolder;

                }
                else if(tempHolder.contains("~large"))
                {
                    LARGE=tempHolder;

                }

            }

        checkIfLinkExist();
    }

    public boolean checkIfLinkExist()
    {
        if(ORIGINAL==null || ORIGINAL.equals(""))
        {
            return false;
        }
        return true;
    }
}
