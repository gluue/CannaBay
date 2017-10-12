package arik.acb;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 8/7/2017.
 */

public class User {
    String userName, userPassword;
    Drawable userAvater;
    List<Product> productCartList;
    LatLng location;

    public User(){
        this.productCartList = new ArrayList<>();
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserAvater(Drawable userAvater) {
        this.userAvater = userAvater;
    }

    public Drawable getUserAvater() {
        return userAvater;
    }

    public List<Product> getProductCartList() {
        return productCartList;
    }

    public void addToProductList(Product p){
        this.productCartList.add(p);
    }
}
