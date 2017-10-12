package arik.acb;

import android.app.Application;
import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 7/26/2017.
 */

public class SuperVar extends Application {
    static History history;
    static User currentUser;
    static String productListType;
    static Product targetProduct;
    static ProductDetailFragment targetProductFragment;
    static Dispensary targetDispensary;
    static DispensaryDetailFragment targetDispensaryFragment;
    static FragmentManager supportFragmentManager;
    static String pageID = "";
    static String lastPageID = "";
    static Fragment lastFragment;
    static BottomNavigationView mainNavigationView;
    static boolean requestMap = false;
    static List<Dispensary> dispensaryList = new ArrayList<>();
    static List<User> userList = new ArrayList<>();
    static List<Product> productList = new ArrayList<>();
}
