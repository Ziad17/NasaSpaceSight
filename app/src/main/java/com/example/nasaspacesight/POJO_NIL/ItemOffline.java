package com.example.nasaspacesight.POJO_NIL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import java.util.ArrayList;
import java.util.List;

import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_CENTER;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_DATE_CREATED;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_DESCRIPTION;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_NASA_ID;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_PHOTOGRAPHER;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_TITLE;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_HREF;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_HREF_LINKS;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_IS_FAV;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_MEDIA_TYPE;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_REL;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_RENDER;

/***
 * Adapter Class to match the ORM constraints in Room
 */
@Entity
public class ItemOffline{


    @ColumnInfo(name =NIL_OBJECT_DATA_TITLE )
    private String title;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = NIL_OBJECT_DATA_NASA_ID)
    private String nasaId;
    @ColumnInfo(name = NIL_OBJECT_DATA_CENTER)
    private String center;
    @ColumnInfo(name = NIL_OBJECT_DATA_DATE_CREATED)
    private String dateCreated;
    @ColumnInfo(name = NIL_OBJECT_DATA_PHOTOGRAPHER)
    private String photographer;
    @ColumnInfo(name = NIL_OBJECT_MEDIA_TYPE)
    private String mediaType;
    @ColumnInfo(name =NIL_OBJECT_DATA_DESCRIPTION )
    private String description;

    @ColumnInfo(name = NIL_OBJECT_HREF)
    private String href;


    @ColumnInfo(name = NIL_OBJECT_IS_FAV)
    private boolean Favorite;

    @ColumnInfo(name = NIL_OBJECT_REL)
    private String rel;

    @ColumnInfo(name = NIL_OBJECT_HREF_LINKS)
    private String href_links;

    @ColumnInfo(name = NIL_OBJECT_RENDER)
    private String render;


    @Ignore
    public ItemOffline() {
    }

    public ItemOffline(@NonNull String nasaId,String title, String href) {
        this.title = title;
        this.nasaId = nasaId;
        this.href = href;
    }

    public static ItemOffline convertNILtoNILoffline(Item item)
    {
        ItemOffline itemOffline=new ItemOffline();
        itemOffline.setTitle(item.getData().getTitle());
        itemOffline.setCenter(item.getData().getCenter());
        itemOffline.setDateCreated(item.getData().getDateCreated());
        itemOffline.setDescription(item.getData().getDescription());
        itemOffline.setFavorite(item.isFavorite());
        itemOffline.setRender(item.getLinks().getRender());
        itemOffline.setNasaId(item.getData().getNasaId());
        itemOffline.setHref(item.getHref());
        itemOffline.setHref_links(item.getLinks().getHref());
        itemOffline.setPhotographer(item.getData().getPhotographer());
        itemOffline.setMediaType(item.getData().getMediaType());
        itemOffline.setRel(item.getLinks().getRel());
        return itemOffline;
    }

    public Item toNILitem()
    {

        String HREF;
        List<Datum> DATA_MAIN=new ArrayList<>();
        List<Link> LINKS_MAIN=new ArrayList<>();
        Datum DATA=new Datum();
        Link LINK=new Link();
        DATA.setCenter(getCenter());
        DATA.setDateCreated(getDateCreated());
        DATA.setDescription(getDescription());
        DATA.setMediaType(getMediaType());
        DATA.setTitle(getTitle());
        DATA.setNasaId(getNasaId());
        DATA.setPhotographer(getPhotographer());

        LINK.setHref(getHref_links());
        LINK.setRel(getRel());
        LINK.setRender(getRender());

        HREF=getHref();

        DATA_MAIN.add(DATA);
        LINKS_MAIN.add(LINK);

        Item item=new Item();

        item.setFavorite(isFavorite());
        item.setData(DATA_MAIN);
        item.setLinks(LINKS_MAIN);
        item.setHref(HREF);

        return item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref_links() {
        return href_links;
    }

    public void setHref_links(String href_links) {
        this.href_links = href_links;
    }

    public String getRender() {
        return render;
    }

    public void setRender(String render) {
        this.render = render;
    }
}
