package arik.acb;

import android.app.Fragment;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Jake on 8/7/2017.
 */

public class ProductStatFragment extends Fragment {
    ImageView thcBar, cbdBar, cbcBar;
    double thc, cbc, cbd;
    TextView statText1, statText2, statText3;
    RelativeLayout statBoxLayout1, statBoxLayout2, statBoxLayout3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_stat_layout, container, false);
        thcBar = (ImageView)rootView.findViewById(R.id.imageViewStatsTHC);
        cbdBar = (ImageView)rootView.findViewById(R.id.imageViewStatsCBD);
        cbcBar = (ImageView)rootView.findViewById(R.id.imageViewStatsCBC);
        statText1 = (TextView)rootView.findViewById(R.id.textViewProductStatBox1);
        statText2 = (TextView)rootView.findViewById(R.id.textViewProductStatBox2);
        statText3 = (TextView)rootView.findViewById(R.id.textViewProductStatBox3);
        statBoxLayout1 = (RelativeLayout)rootView.findViewById(R.id.relativeLayoutStatBox1);
        statBoxLayout2 = (RelativeLayout)rootView.findViewById(R.id.relativeLayoutStatBox2);
        statBoxLayout3 = (RelativeLayout)rootView.findViewById(R.id.relativeLayoutStatBox3);
        statBoxLayout1.animate().scaleX(0).scaleY(0);
        statBoxLayout2.animate().scaleX(0).scaleY(0);
        statBoxLayout3.animate().scaleX(0).scaleY(0);


        thcBar.setScaleX(0);
        cbdBar.setScaleX(0);
        cbcBar.setScaleX(0);
        thcBar.setPivotX(0);
        thcBar.setPivotY(0);
        cbcBar.setPivotX(0);
        cbcBar.setPivotY(0);
        cbdBar.setPivotX(0);
        cbdBar.setPivotY(0);

        thc = SuperVar.targetProduct.getStatList().get(0);
        cbc = SuperVar.targetProduct.getStatList().get(2);
        cbd = SuperVar.targetProduct.getStatList().get(1);

        initStatViews();

        //transition by scaleBy value + 50% of scaleBy value

        thcBar.animate().scaleXBy(getStatBarScaleValue(thc)).setDuration(250).withEndAction(new Runnable() {
            @Override
            public void run() {
                statBoxLayout1.setVisibility(View.VISIBLE);
                statBoxLayout1.animate().scaleX(1).scaleY(1).setDuration(50).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        statBoxLayout2.setVisibility(View.VISIBLE);
                        statBoxLayout2.animate().scaleX(1).scaleY(1).setDuration(50).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                statBoxLayout3.setVisibility(View.VISIBLE);
                                statBoxLayout3.animate().scaleX(1).scaleY(1).setDuration(50);
                            }
                        });
                    }
                });
            }
        });

        cbdBar.animate().scaleXBy(getStatBarScaleValue(cbd)).setDuration(250);
        cbcBar.animate().scaleXBy(getStatBarScaleValue(cbc)).setDuration(250);




        return rootView;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public void initStatViews(){

        double total = thc + cbc + cbd;

        if(thc>10){
            statText1.setText(Integer.toString((int)thc)+"%");
        }else{
            statText1.setText(Double.toString(round(thc, 2))+"%");
        }
        if(cbd>10){
            statText2.setText(Integer.toString((int)cbd)+"%");
        }else{
            statText2.setText(Double.toString(round(cbd, 2))+"%");
        }
        if(cbc>10){
            statText3.setText(Integer.toString((int)cbc)+"%");
        }else{
            statText3.setText(Double.toString(round(cbc, 2))+"%");
        }

        if(thc/total>.5){
            statText1.setTextColor(Color.GREEN);
            thcBar.setBackgroundResource(R.drawable.green_dark_gloss_bar);
        }else if(thc/total>.25){
            thcBar.setBackgroundResource(R.drawable.yellow_gloss_bar);
            statText1.setTextColor(Color.YELLOW);
        }else{
            thcBar.setBackgroundResource(R.drawable.red_gloss_bar);
            statText1.setTextColor(Color.RED);
        }

        if(cbc/total>.5){
            statText3.setTextColor(Color.GREEN);
            cbcBar.setBackgroundResource(R.drawable.green_dark_gloss_bar);
        }else if(cbc/total>.25){
            cbcBar.setBackgroundResource(R.drawable.yellow_gloss_bar);
            statText3.setTextColor(Color.YELLOW);
        }else{
            cbcBar.setBackgroundResource(R.drawable.red_gloss_bar);
            statText3.setTextColor(Color.RED);
        }

        if(cbd/total>.5){
            cbdBar.setBackgroundResource(R.drawable.green_dark_gloss_bar);
            statText2.setTextColor(Color.GREEN);
        }else if(cbd/total>.25){
            cbdBar.setBackgroundResource(R.drawable.yellow_gloss_bar);
            statText2.setTextColor(Color.YELLOW);
        }else{
            cbdBar.setBackgroundResource(R.drawable.red_gloss_bar);
            statText2.setTextColor(Color.RED);
        }
    }

    public int getStatBarScaleValue(double stat){
        double total = thc + cbc + cbd;
        double percentageAmount = (stat)/total;
        double scaledLength = percentageAmount*22;
        int convertedInt = (int) scaledLength;

        System.out.println(Double.toString(stat) + " gave us a value of " + Integer.toString(convertedInt));

        if(convertedInt<2){
            convertedInt = 1;
        }

        return convertedInt;
    }






}
