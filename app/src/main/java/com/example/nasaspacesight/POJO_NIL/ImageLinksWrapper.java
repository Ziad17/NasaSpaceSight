package com.example.nasaspacesight.POJO_NIL;

import com.example.nasaspacesight.ViewModels.DataWrapperStatus;

public class ImageLinksWrapper extends DataWrapperStatus {
    private ImageLinks imageLinks;

    public ImageLinksWrapper(ImageLinks imageLinks, int status, String msg) {
        this.status = status;
        this.message = msg;
        this.imageLinks = imageLinks;
    }


}
