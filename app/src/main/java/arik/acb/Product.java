package arik.acb;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 7/26/2017.
 */

public class Product {
    int rating, homeRank;
    String productName, productType, dispensaryAddress, dispensaryName, productDescription;
    Drawable image, iconImage;
    List<Double> statList;
    Dispensary dispensary;


    public void setHomeRank(int homeRank) {
        this.homeRank = homeRank;
    }

    public int getHomeRank() {
        return homeRank;
    }

    public Product(){
        statList = new ArrayList<>();
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setDispensary(Dispensary dispensary) {
        this.dispensary = dispensary;
    }

    public Dispensary getDispensary() {
        return dispensary;
    }

    public List<Double> getStatList() {
        return statList;
    }
    public void addToStatList(Double stat){
        statList.add(stat);
    }


    public void setIconImage(Drawable iconImage) {
        this.iconImage = iconImage;
    }

    public Drawable getIconImage() {
        return iconImage;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }

    public void setDispensaryAddress(String dispensaryAddress) {
        this.dispensaryAddress = dispensaryAddress;
    }

    public String getDispensaryAddress() {
        return dispensary.getDispensaryAddress();
    }

    public void setDispensaryName(String dispensaryName) {
        this.dispensaryName = dispensaryName;
    }

    public String getDispensaryName() {
        return dispensary.getDispensaryName();
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
}
