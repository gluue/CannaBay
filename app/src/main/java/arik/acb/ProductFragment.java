package arik.acb;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jake on 7/26/2017.
 */

public class ProductFragment extends Fragment {

    RecyclerView recyclerView;
    BottomNavigationView productNavigationView;
    List<Product> list;
    Context context;
    int REL_SWIPE_MIN_DISTANCE, REL_SWIPE_MAX_OFF_PATH, REL_SWIPE_THRESHOLD_VELOCITY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_fragment_layout, container, false);
        context = rootView.getContext();
        SuperVar.lastPageID = SuperVar.pageID;
        SuperVar.pageID = "productList";
        list = new ArrayList<>();
        SuperVar.mainNavigationView.getMenu().getItem(0).setChecked(true);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        REL_SWIPE_MIN_DISTANCE = (int)(120.0f * dm.densityDpi / 160.0f + 0.5);
        REL_SWIPE_MAX_OFF_PATH = (int)(250.0f * dm.densityDpi / 160.0f + 0.5);
        REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 160.0f + 0.5);

        for(Dispensary d : SuperVar.dispensaryList){
            for(int i = 0; i < d.getProductList().size(); i++){
                list.add(d.getProductList().get(i));
                SuperVar.productList.add(d.getProductList().get(i));
            }
        }

        productNavigationView = (BottomNavigationView)rootView.findViewById(R.id.productNavigationBar);
        productNavigationView.setOnNavigationItemSelectedListener(productNavigationItemSelectedListener);

        switch(SuperVar.productListType){
            case "f":
                productNavigationView.getMenu().getItem(0).setChecked(true);
                break;
            case "c":
                productNavigationView.getMenu().getItem(1).setChecked(true);
                break;
            case "e":
                productNavigationView.getMenu().getItem(2).setChecked(true);
                break;
        }

        recyclerView = (RecyclerView)rootView.findViewById(R.id.rvProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        //Aight what the fuck
        // /recyclerView.addOnItemTouchListener(new MyGestureDetector());
        recyclerView.setAdapter(new ProductListAdapter(rootView.getContext(), list, getFragmentManager()));

        return rootView;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener productNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.productMenuFlower:
                    SuperVar.productListType = "f";
                    recyclerView.setAdapter(new ProductListAdapter(context, list, getFragmentManager()));
                    //getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
                case R.id.productMenuConcentrate:
                    SuperVar.productListType = "c";
                    recyclerView.setAdapter(new ProductListAdapter(context, list, getFragmentManager()));
                    //getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
                case R.id.productMenuEdible:
                    SuperVar.productListType = "e";
                    recyclerView.setAdapter(new ProductListAdapter(context, list, getFragmentManager()));
                    //getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        // Detect a single-click and call my own handler.
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            RecyclerView rv = recyclerView;
            int pos = rv.findChildViewUnder(e.getX(), e.getY()).getId();
            myOnItemClick(pos);
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH)
                return false;
            if(e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
                onRTLFling();
            }  else if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
                onLTRFling();
            }
            return false;
        }

    }

    private void myOnItemClick(int position) {
        String str = MessageFormat.format("Item clicked = {0,number}", position);
        Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    private void onLTRFling() {
        Toast.makeText(getActivity().getApplicationContext(), "Left-to-right fling", Toast.LENGTH_SHORT).show();
    }

    private void onRTLFling() {
        Toast.makeText(getActivity().getApplicationContext(), "Right-to-left fling", Toast.LENGTH_SHORT).show();
    }
}
