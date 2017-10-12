package arik.acb;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 10/12/2017.
 */
public class History {

    List<String> historyItemNameList, historyItemDescriptionList, historyItemTypeList;
    List<Drawable> historyItemImageList;


    public History(){
        historyItemDescriptionList = new ArrayList<>();
        historyItemNameList = new ArrayList<>();
        historyItemTypeList = new ArrayList<>();
        historyItemImageList = new ArrayList<>();


    }

    public void addToNameList(String name){
        this.historyItemNameList.add(name);
    }

    public List<String> getHistoryItemNameList() {
        return historyItemNameList;
    }

    public List<String> getHistoryItemDescriptionList() {
        return historyItemDescriptionList;
    }

    public List<String> getHistoryItemTypeList() {
        return historyItemTypeList;
    }

    public void addToDescriptionList(String desc){this.historyItemDescriptionList.add(desc);}

    public void addToTypeList(String type){this.historyItemTypeList.add(type);}

    public String getHistoryItemName(int i){return this.historyItemNameList.get(i);}

    public String getHistoryItemType(int i){return this.historyItemTypeList.get(i);}

    public String getHistoryItemDescription(int i){return this.historyItemDescriptionList.get(i);}

    public List<Drawable> getHistoryItemImageList() {
        return historyItemImageList;
    }

    public void addToHistoryItemImageList(Drawable d){this.historyItemImageList.add(d);}

    public Drawable getHistoryItemImage(int i){return this.historyItemImageList.get(i);}
}

