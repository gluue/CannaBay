package arik.acb;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Craig on 7/26/2017.
 */

public class Dispensary {
    String dispensaryName, dispensaryAddress, dispensaryWebsite, phoneNumber, website;
    int rating, homeRank;
    Drawable image, iconImage;
    List<Product> productList;
    double latitude, longitude;

    public void setHomeRank(int homeRank) {
        this.homeRank = homeRank;
    }

    public int getHomeRank() {
        return homeRank;
    }

    public Dispensary(){
        productList = new ArrayList<>();
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addToProductList(Product p){
        productList.add(p);
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }

    public void setIconImage(Drawable iconImage) {
        this.iconImage = iconImage;
    }

    public Drawable getIconImage() {
        return iconImage;
    }

    public void setDispensaryAddress(String dispensaryAddress) {
        this.dispensaryAddress = dispensaryAddress;
    }

    public String getDispensaryAddress() {
        return dispensaryAddress;
    }

    public void setDispensaryName(String dispensaryName) {
        this.dispensaryName = dispensaryName;
    }

    public String getDispensaryName() {
        return dispensaryName;
    }

    public void setDispensaryWebsite(String dispensaryWebsite) {
        this.dispensaryWebsite = dispensaryWebsite;
    }

    public String getDispensaryWebsite() {
        return dispensaryWebsite;
    }

    public void setDispensaryRating(int dispensaryRating) {
        this.rating = dispensaryRating;
    }

    public int getDispensaryRating() {
        return rating;
    }
}
