package arik.acb;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jake on 7/26/2017.
 */

public class DispensaryFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap gmap;
    RecyclerView dispensaryList;
    BottomNavigationView dispensaryNavigationView;
    List<Dispensary> list;
    Context context;
    FrameLayout frameLayoutMap;
    RelativeLayout searchLayout;
    SupportMapFragment mapFragment;
    LocationManager locationManager;
    Bundle savedInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dispensary_fragment_layout, container, false);
        dispensaryNavigationView = (BottomNavigationView)rootView.findViewById(R.id.dispensaryNavigationView);
        dispensaryList = (RecyclerView)rootView.findViewById(R.id.rvDispensaryList);
        dispensaryList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        searchLayout = (RelativeLayout)rootView.findViewById(R.id.relativeLayoutDispensarySearch);
        frameLayoutMap = (FrameLayout)rootView.findViewById(R.id.frameLayoutDispensaryMap);
        SuperVar.lastPageID = SuperVar.pageID;
        SuperVar.pageID = "dispensaryList";

        SuperVar.mainNavigationView.getMenu().getItem(1).setChecked(true);
        context = rootView.getContext();


        dispensaryList.setAdapter(new DispensaryListAdapter(context, SuperVar.dispensaryList, getFragmentManager()));
        dispensaryNavigationView.setOnNavigationItemSelectedListener(dispensaryNavigationItemSelectedListener);

        if(SuperVar.requestMap){
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.onCreate(savedInstance);
            mapFragment.getMapAsync(DispensaryFragment.this);
            //mapAroundUser();

            //SuperVar.dispensaryMap = mapFragment;

            SuperVar.supportFragmentManager.beginTransaction().replace(R.id.frameLayoutDispensaryMap, mapFragment).commit();


            dispensaryList.setVisibility(View.GONE);
            frameLayoutMap.setVisibility(View.VISIBLE);
            SuperVar.requestMap = false;

            if(searchLayout.getVisibility()==View.VISIBLE){
                searchLayout.setVisibility(View.GONE);
            }

            dispensaryNavigationView.getMenu().getItem(1).setChecked(true);
            SuperVar.pageID = "dispensaryMap";
        }

        return rootView;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener dispensaryNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.dispensaryMenuList:
                    SuperVar.lastPageID = SuperVar.pageID;
                    SuperVar.pageID = "dispensaryList";
                    if(frameLayoutMap.getVisibility()==View.VISIBLE){
                        frameLayoutMap.setVisibility(View.GONE);
                    }

                    if(dispensaryList.getVisibility() == View.GONE){
                        dispensaryList.setVisibility(View.VISIBLE);
                    }else{
                        if(searchLayout.getVisibility() == View.GONE){
                            searchLayout.setVisibility(View.VISIBLE);
                        }else{
                            searchLayout.setVisibility(View.GONE);
                        }
                    }

                    return true;
                case R.id.dispensaryMenuMap:
                    //maybe add this or the previous fragment to the backstack since swiping no longer navigates

                    SuperVar.lastPageID = SuperVar.pageID;
                    SuperVar.pageID = "dispensaryMap";
                    mapFragment = SupportMapFragment.newInstance();
                    mapFragment.onCreate(savedInstance);
                    mapFragment.getMapAsync(DispensaryFragment.this);

                    SuperVar.supportFragmentManager.beginTransaction().replace(R.id.frameLayoutDispensaryMap, mapFragment).commit();


                    dispensaryList.setVisibility(View.GONE);
                    frameLayoutMap.setVisibility(View.VISIBLE);

                    if(searchLayout.getVisibility()==View.VISIBLE){
                        searchLayout.setVisibility(View.GONE);
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onMapReady(GoogleMap map)
    {
        try{
            if(!SuperVar.requestMap){
                for(Dispensary disp : SuperVar.dispensaryList){
                    if(disp.getLatitude()!=0||disp.getLongitude()!=0){
                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(disp.getLongitude(), disp.getLatitude()))
                                .title(disp.getDispensaryName()));
                    }

                }
                //enabling location my location enabled eats up data
                //gmap.setMyLocationEnabled(true);
            }else{
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(SuperVar.targetDispensary.getLongitude(), SuperVar.targetDispensary.getLatitude()))
                        .title(SuperVar.targetDispensary.getDispensaryName()));
            }

            gmap = map;

        }catch (SecurityException sec){
            System.out.println("SECURITY EXCEPTION");
        }

        if(SuperVar.currentUser.getLocation()!=null){
            gmap.moveCamera(CameraUpdateFactory.newLatLng(SuperVar.currentUser.getLocation()));  //CATCH A NULL POINTER HERE. LOCATION LISTENER FAILURE
            gmap.animateCamera(CameraUpdateFactory.zoomTo(8));
        }else{

            try{
                gmap.setMyLocationEnabled(true);
            }catch (SecurityException sec){
                sec.printStackTrace();
            }

        }

    }




}
