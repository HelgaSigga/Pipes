package com.example.quima.pipes;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Quima on 20/03/2015.
 */
public class TextViewSpinnerAdapter<E> implements SpinnerAdapter {

    ArrayList<E> values;
    ArrayList<TextView> views;
    Context context;

    public TextViewSpinnerAdapter(Context context, ArrayList<E> values){
        this.context = context;
        this.values = values;
        this.views = new ArrayList<TextView>();
        for(int i = 0; i < values.size(); i++){
            TextView tv = new TextView(context);
            String s = values.get(i).toString();
            //String t = "Svæði " + s;
            tv.setText(s);  //prófa að breyta í t
            tv.setWidth(600);
            tv.setHeight(80);
            /* TODO: Width and Height should not be given absolute values. */
            views.add(tv);
        }
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int i) {
        return values.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(i < views.size()) {
            return views.get(i);
        } else {
            Log.e("TextViewSpinnerAdapter", "Attempted to access element " + i + " in a " + views.size() + " array.");
            TextView tv = new TextView(context);
            tv.setText("Supreme Failure.");
            return tv;
        }
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }
}
