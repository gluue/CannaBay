package arik.acb;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    OnSwipeTouchListener swipeTouchListener;
    List<Dispensary> dispensaryList;
    Dialog dialog;
    SharedPreferences prefs;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_product:
                    SuperVar.pageID = "productList";
                    fragmentManager.beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
                case R.id.navigation_dispensary:
                    SuperVar.pageID = "dispensaryList";
                    fragmentManager.beginTransaction().replace(R.id.frameLayoutMain, new DispensaryFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    SuperVar.pageID = "notification";
                    fragmentManager.beginTransaction().replace(R.id.frameLayoutMain, new HomePageFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        prefs = MainActivity.this.getPreferences(Context.MODE_PRIVATE);

        SuperVar.history = new History();

        User u1 = new User();
        u1.setUserName("admin");
        u1.setUserPassword("root");
        u1.setUserAvater(getResources().getDrawable(R.drawable.avatar_default));

        User u2 = new User();
        u2.setUserAvater(getResources().getDrawable(R.drawable.avatar_default));
        u2.setUserName("u");
        u2.setUserPassword("p");

        SuperVar.userList.add(u1);
        SuperVar.userList.add(u2);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationMain);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        dispensaryList = new ArrayList<>();

        Integer resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode == ConnectionResult.SUCCESS) {
            //Google play services is up to date, BOOOOYAKASHA!
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                //This dialog will help the user update to the latest GooglePlayServices
                dialog.show();
            }
        }

        if(prefs.getBoolean("firstTime", true)){
            prefs.edit().putBoolean("firstTime", false).apply();
            aSyncDispensaryCompiler compileTask = new aSyncDispensaryCompiler();
            compileTask.execute();
        }else{
            aSyncQuickCompile quickCompileTask = new aSyncQuickCompile();
            quickCompileTask.execute();
        }

        SuperVar.mainNavigationView = navigation;

        SuperVar.productListType = "f";
        SuperVar.pageID = "productList";
        SuperVar.supportFragmentManager = getSupportFragmentManager();

        swipeTouchListener = new OnSwipeTouchListener(MainActivity.this) {
                @Override
                public void onSwipeLeft() {
                    //FORWARD SWIPE
           //     Toast.makeText(MainActivity.this, "YOU'RE SWIPING LEFT", Toast.LENGTH_LONG).show();

                    if(!SuperVar.pageID.equals("dispensaryMap")){
                        switch (SuperVar.pageID){
                            case "productList":
                                if(SuperVar.lastPageID.equals("productDetail")){
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                                    transaction.replace(R.id.frameLayoutMain, SuperVar.targetProductFragment).commit();
                                }else{
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                                    transaction.replace(R.id.frameLayoutMain, new DispensaryFragment()).commit();
                                }

                                break;
                            case "dispensaryList":
                                if(SuperVar.lastPageID.equals("dispensaryDetail")||SuperVar.targetDispensary!=null){
                                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                                    transaction1.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                                    transaction1.replace(R.id.frameLayoutMain, SuperVar.targetDispensaryFragment).commit();
                                }else{
                                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                                    transaction1.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                                    transaction1.replace(R.id.frameLayoutMain, new NotificationFragment()).commit();
                                }

                                break;
                            case "notification":
                                break;
                            case "productDetail":
                                if(SuperVar.lastPageID.equals("dispensaryDetail")){
                                    SuperVar.targetDispensary = SuperVar.targetProduct.getDispensary();
                                    FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                                    transaction4.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                                    transaction4.replace(R.id.frameLayoutMain, new DispensaryDetailFragment()).commit();
                                }
                                break;
                            case "dispensaryDetail":
                                if(SuperVar.lastPageID.equals("dispensaryProductDetail")||SuperVar.targetProductFragment!=null){
                                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                                    transaction3.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                                    transaction3.replace(R.id.frameLayoutMain, SuperVar.targetProductFragment).commit();
                                }
                                break;
                            case "dispensaryProductDetail":
                                break;

                        }
                    }

            }
                @Override
                public void onSwipeRight () {
                    //BACK SWIPE
             //   Toast.makeText(MainActivity.this, "YOU'RE SWIPING RIGHT", Toast.LENGTH_LONG).show();
              //      System.out.println("ON THE " + SuperVar.pageID + " PAGE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                    if(!SuperVar.pageID.equals("dispensaryMap")){
                        switch (SuperVar.pageID){

                            case "productList":
                                break;
                            case "dispensaryList":
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_right);
                                transaction.replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                                break;
                            case "notification":
                                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                                transaction1.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_right);
                                transaction1.replace(R.id.frameLayoutMain, new DispensaryFragment()).commit();
                                break;

                            case "productDetail":
                                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                                transaction2.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_right);
                                transaction2.replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                                break;
                            case "dispensaryDetail":
                                if(SuperVar.lastPageID.equals("productDetail")){
                                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                                    transaction3.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_right);
                                    transaction3.replace(R.id.frameLayoutMain, new ProductDetailFragment()).commit();
                                }else{
                                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                                    transaction3.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_right);
                                    transaction3.replace(R.id.frameLayoutMain, new DispensaryFragment()).commit();
                                }
                                break;
                        }
                    }
            }
        };

        fragmentManager.beginTransaction().replace(R.id.frameLayoutMain, new LogInFragment()).commit();

        final FrameLayout mainFrameLayout = (FrameLayout)findViewById(R.id.frameLayoutMain);
        mainFrameLayout.setOnTouchListener(swipeTouchListener);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        swipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public void generateRandomDatabase(int numOfDispensary){
        Random random = new Random();
        int totalProducts = 0;

        for(int i = 0; i < numOfDispensary; i++){
            Dispensary dispensary = new Dispensary();
            dispensary.setDispensaryName("Random dispensary " + Integer.toString(i));

            for(int k = 0; k < random.nextInt(15); k++){
                Product p = new Product();
                p.setDispensary(dispensary);
                totalProducts++;
                switch (random.nextInt(3)){
                    case 0:
                        p.setProductType("f");
                        p.setImage(getResources().getDrawable(R.drawable.flower));
                        p.setIconImage(getResources().getDrawable(R.drawable.flower_image_icon));
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        break;
                    case 1:
                        p.setProductType("c");
                        p.setImage(getResources().getDrawable(R.drawable.concentrate));
                        p.setIconImage(getResources().getDrawable(R.drawable.concentrate_image_icon));
                        p.addToStatList((double)random.nextInt(90) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(90) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(90) + random.nextDouble());
                        break;
                    case 2:
                        p.setProductType("e");
                        p.setImage(getResources().getDrawable(R.drawable.edible));
                        p.setIconImage(getResources().getDrawable(R.drawable.edible_image_icon));
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        break;
                }
                p.setRating(random.nextInt(5)+1);
                p.setProductName("Random product " + Integer.toString(totalProducts));
                dispensary.addToProductList(p);
            }
            dispensary.setDispensaryAddress("Some random damn place");
            dispensary.setDispensaryWebsite("www.bonerchamp.com");
            dispensary.setImage(getResources().getDrawable(R.drawable.dispensary_image));
            dispensary.setIconImage(getResources().getDrawable(R.drawable.dispensary_image_icon));
            dispensary.setDispensaryRating(random.nextInt(5)+1);
            SuperVar.dispensaryList.add(dispensary);
        }
    }

    public static void compileInfo(Context context){
        String dataString = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("dispJSON.txt")));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            dataString = builder.toString();


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            JSONObject jon = new JSONObject(dataString);
            JSONArray jay = jon.getJSONArray("dispensaryList");
            for (int i = 0; i < jay.length(); i++) {
                JSONObject job = jay.getJSONObject(i);
                Dispensary disp = new Dispensary();
                disp.setDispensaryName(job.getString("name"));
                disp.setDispensaryAddress(job.getString("address"));
                disp.setPhoneNumber(job.getString("phone"));
                disp.setWebsite(job.getString("website"));
                SuperVar.dispensaryList.add(disp);
            }
        } catch (JSONException jex) {
            jex.printStackTrace();
        }

        System.out.println("OK WE FILLED THE DISPENSARY LIST");

        List<LatLng> locList = new ArrayList<>();
        Geocoder coder = new Geocoder(context);
        System.out.println("STARTING THE DISPENSARY LATLNG PROCESS");

        for(int i = 0; i < SuperVar.dispensaryList.size(); i++){
            List<Address> address;
            LatLng p1 = null;
            try {
                address = coder.getFromLocationName(SuperVar.dispensaryList.get(i).getDispensaryAddress(), 5);
                if (address == null) {
                    System.out.println(SuperVar.dispensaryList.get(i).getDispensaryName() + " HAS NO ADDRESS WHATTTt");
                }
                Address location = address.get(0);

                //HAD TO REVERSE THIS TO GET IT TO WORK RIGHT... FOR SOME REASON
                SuperVar.dispensaryList.get(i).setLatitude(location.getLongitude());
                SuperVar.dispensaryList.get(i).setLongitude(location.getLatitude());

            } catch (Exception ex) {
                System.out.println("THAT ADDRESS FOR " + SuperVar.dispensaryList.get(i).getDispensaryName()+" DONT WORK DAMNNNNN");
                //ex.printStackTrace();
            }
        }
        //SuperVar.totalDispensaryList = dispensaryList;
        WriteToJSON(SuperVar.dispensaryList, context);
    }

    public static void WriteToJSON(List<Dispensary> list, Context context){
        String tempString = "{\"dispensaryList\":[";
        for(int i=0; i <list.size();i++){
            tempString += "{\"name\":\""+list.get(i).getDispensaryName() +"\", \"address\":\""+list.get(i).getDispensaryAddress()+"\", \"phone\":\""
                    + list.get(i).getPhoneNumber()+"\", \"website\":\""+list.get(i).getWebsite()+"\",";
            if(list.get(i).getLongitude()!=0||list.get(i).getLatitude()!=0){
                tempString += " \"longitude\":\""+list.get(i).getLongitude()+"\",\"latitude\":\""+list.get(i).getLatitude()+"\"},";
            }else{
                tempString += " \"longitude\":\""+"N/A"+"\",\"latitude\":\""+"N/A"+"\"},";
            }
        }
        tempString = tempString.substring(0,tempString.length()-1);
        tempString += "]}";

        String fileDestination = context.getFilesDir().getAbsolutePath()+"Completed.txt";

        try{
            FileOutputStream stream = new FileOutputStream(fileDestination);
            try {
                stream.write(tempString.getBytes());
                System.out.println("SAVED THE OTHER DATA DATA");
            }finally {
                stream.close();
            }

        }catch(IOException io){
            io.printStackTrace();
        }
    }

    private class aSyncDispensaryCompiler extends AsyncTask<String, Void, String> {
        Dialog dialog = new Dialog(MainActivity.this);

        @Override
        protected String doInBackground(String... params) {
            compileInfo(getApplicationContext());
            return "executed";
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Compiling Dispensary Data");
            dialog.setContentView(R.layout.loading_alert_layout);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected void onPostExecute(String result) {
            aSyncQuickCompile quickComp = new aSyncQuickCompile();
            quickComp.execute();
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    public void generateRandomDispensaryProducts(){
        //also adds stock images to dispensary
        for(Dispensary dispensary : SuperVar.dispensaryList){
            int totalProducts = 0;
            Random random = new Random();
            for(int k = 0; k < random.nextInt(15); k++){
                Product p = new Product();
                p.setDispensary(dispensary);
                totalProducts++;
                switch (random.nextInt(3)){
                    case 0:
                        p.setProductType("f");
                        p.setImage(getResources().getDrawable(R.drawable.flower));
                        p.setIconImage(getResources().getDrawable(R.drawable.flower_image_icon));
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        break;
                    case 1:
                        p.setProductType("c");
                        p.setImage(getResources().getDrawable(R.drawable.concentrate));
                        p.setIconImage(getResources().getDrawable(R.drawable.concentrate_image_icon));
                        p.addToStatList((double)random.nextInt(90) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(90) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(90) + random.nextDouble());
                        break;
                    case 2:
                        p.setProductType("e");
                        p.setImage(getResources().getDrawable(R.drawable.edible));
                        p.setIconImage(getResources().getDrawable(R.drawable.edible_image_icon));
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        p.addToStatList((double)random.nextInt(35) + random.nextDouble());
                        break;
                }
                p.setRating(random.nextInt(5)+1);
                p.setProductName("Random product " + Integer.toString(totalProducts));
                dispensary.addToProductList(p);
            }
            dispensary.setImage(getResources().getDrawable(R.drawable.dispensary_image));
            dispensary.setIconImage(getResources().getDrawable(R.drawable.dispensary_image_icon));
            dispensary.setDispensaryRating(random.nextInt(5)+1);
        }
    }

    private class aSyncQuickCompile extends AsyncTask<String, Void, String> {
        Dialog dialog = new Dialog(MainActivity.this);

        @Override
        protected String doInBackground(String... params) {
            String jsonString = "";
            try{

                File file = new File(getFilesDir().getAbsoluteFile()+"Completed.txt");
                StringBuilder otherBuilder = new StringBuilder();
                BufferedReader fileReader = new BufferedReader(new FileReader(file));

                String line1;
                try{
                    while((line1 = fileReader.readLine())!=null){
                        otherBuilder.append(line1);
                    }
                    fileReader.close();
                    jsonString = otherBuilder.toString();
                }catch (IOException io){
                    io.printStackTrace();
                }

            }catch (FileNotFoundException fi){
                fi.printStackTrace();
            }

            try{
                JSONObject jo = new JSONObject(jsonString);
                JSONArray ja = jo.getJSONArray("dispensaryList");
                for(int i=0;i<ja.length();i++){
                    JSONObject jo1 = ja.getJSONObject(i);
                    if(!jo1.getString("longitude").equals("N/A")){
                        Dispensary disp = new Dispensary();
                        disp.setDispensaryName(jo1.getString("name"));
                        disp.setDispensaryAddress(jo1.getString("address"));
                        disp.setPhoneNumber(jo1.getString("phone"));
                        disp.setWebsite(jo1.getString("website"));
                        disp.setLongitude(Double.parseDouble(jo1.getString("longitude")));
                        disp.setLatitude(Double.parseDouble(jo1.getString("latitude")));
                        SuperVar.dispensaryList.add(disp);
                    }else{
                        Dispensary disp = new Dispensary();
                        disp.setDispensaryName(jo1.getString("name"));
                        disp.setDispensaryAddress(jo1.getString("address"));
                        disp.setPhoneNumber(jo1.getString("phone"));
                        disp.setWebsite(jo1.getString("website"));
                        SuperVar.dispensaryList.add(disp);
                    }
                }
            }catch (JSONException jex){
                jex.printStackTrace();
            }

            generateRandomDispensaryProducts();
            return "executed";
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Compiling Dispensary Data");
            dialog.setContentView(R.layout.loading_alert_layout);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


}
