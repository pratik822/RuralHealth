package com.hgs.ruralhealth.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.model.masterdata.SymptompsOutputData;

import java.util.List;

/**
 * Created by rameshg on 10/3/2016.
 */
public class MorbidityAdapter extends BaseAdapter  {
    private static ClickListener clickListener;
    private Context context;
    private  List<SymptompsOutputData>morbidityList;

    public MorbidityAdapter(Context context, List<SymptompsOutputData> morbidityList) {
        this.context = context;
        this.morbidityList = morbidityList;
    }

    @Override
    public int getCount() {
        return morbidityList.size();
    }

    @Override
    public Object getItem(int position) {
        return morbidityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MorbidityAdapter.clickListener = clickListener;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v = View.inflate(context, R.layout.morbidity_child, null);
        Switch sw = (Switch)v.findViewById(R.id.sw);
        sw.setText(morbidityList.get(position).getSymptomName());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickListener.onItemClick(position, buttonView, isChecked);
            }
        });
        return v;
    }


    public interface ClickListener {
        void onItemClick(int position, CompoundButton buttonView, boolean isChecked);
    }
}
