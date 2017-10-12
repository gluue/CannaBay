package arik.acb;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Jake on 8/13/2017.
 */

public class SplashScreenFragment extends Fragment {
    GoogleMap gmap;
    LatLng userLocation;
    LocationManager locationManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.splash_screen_layout, container, false);

        aSyncLoadData init = new aSyncLoadData();
        init.execute();


        return rootView;
    }

    private class aSyncLoadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            //LOAD CURRENT WHEREABOUTS

            return "executed";
        }

        @Override
        protected void onPreExecute() {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            try{
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListenerGPS(), null);

            }catch (SecurityException sec){
                sec.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
            transaction.replace(R.id.frameLayoutMain, new ProductFragment()).commit();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    public class LocationListenerGPS implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
            SuperVar.currentUser.setLocation(current);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
