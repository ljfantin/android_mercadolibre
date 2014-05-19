package com.leandrofantin.ejerciciomercadolibre.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lfantin (lea)
 * @see http://developer.android.com/reference/android/os/Parcelable.html
 */
public class Product implements Parcelable{

    private String id=null;
    private String title=null;
    private String price =null;
    private String subtitle =null;
    private String thumbnail =null;
    private String availableQuantity=null;
    private String soldQuantity=null;
    private String condition= null;

    public Product(String id, String title, String price, String subtitle, String thumbnail, String availableQuantity, String soldQuantity, String condition) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.subtitle = subtitle;
        this.thumbnail = thumbnail;
        this.availableQuantity = availableQuantity;
        this.soldQuantity = soldQuantity;
        this.condition = condition;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(String soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getId() {
        return id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.subtitle);
        dest.writeString(this.thumbnail);
        dest.writeString(this.availableQuantity);
        dest.writeString(this.soldQuantity);
        dest.writeString(this.condition);
    }

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.price = in.readString();
        this.subtitle = in.readString();
        this.thumbnail = in.readString();
        this.availableQuantity = in.readString();
        this.soldQuantity = in.readString();
        this.condition = in.readString();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", availableQuantity='" + availableQuantity + '\'' +
                ", soldQuantity='" + soldQuantity + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
