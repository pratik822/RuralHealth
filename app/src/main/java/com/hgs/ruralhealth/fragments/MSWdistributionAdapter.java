package com.hgs.ruralhealth.fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.model.MSW_Distribution.Items;
import com.hgs.ruralhealth.model.masterdata.ProductsOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pratikb on 13-09-2016.
 */
public class MSWdistributionAdapter extends BaseAdapter  {
    Context context;
    List<Integer>addquantity;
    List<ProductsOutputData>productsOutputDataList;
    public static List<Items>itemsList;

    Boolean val=false;

    Items items;
    public MSWdistributionAdapter(Context myctx, List<ProductsOutputData>productlist){
        this.context=myctx;
        addquantity=new ArrayList<>();
        this.productsOutputDataList=productlist;
        itemsList=new ArrayList<>();

    }

    public int calculation(List<Integer> a){
        int b=0;
        for (int i=0;i<a.size();i++){
           b+=a.get(i);
        }
        return b;
    }

    @Override
    public int getCount() {
        return productsOutputDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.msw_activity_layer,null);
        final EditText editText3=(EditText)view.findViewById(R.id.editText3);
        final TextView textView2=(TextView)view.findViewById(R.id.textView2);
        final TextView tvUnits=(TextView)view.findViewById(R.id.tvUnit);
        textView2.setText(productsOutputDataList.get(position).getProductName());
        tvUnits.setText(productsOutputDataList.get(position).getUnit());




       editText3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                items=new Items();
                if(!hasFocus){
                    val=false;


                    try {
                        if(!editText3.getText().toString().isEmpty()){
                            Log.d("myeditval",editText3.getEditableText().toString());
                                if(Double.parseDouble(productsOutputDataList.get(position).getQty())> Double.parseDouble(editText3.getText().toString())){

                                    if (val==false){
                                        items.setItemName(textView2.getText().toString());
                                        items.setQty(editText3.getText().toString());
                                        items.setUnit(productsOutputDataList.get(0).getUnit());
                                        itemsList.add(items);
                                        val=true;
                                    }


                                }else{
                                    Toast.makeText(context,"You dont have that much quantity",Toast.LENGTH_LONG).show();
                                }

                        }
                    }catch (NumberFormatException ex){
                        ex.printStackTrace();
                    }


                }else{
                    for (int k=0;k<itemsList.size();k++){
                        if(itemsList.get(k).getItemName().equalsIgnoreCase(textView2.getText().toString())){
                            if(val==false) {
                                items = new Items();
                                items.setItemName(itemsList.get(k).getItemName());
                                items.setQty(editText3.getText().toString());
                                items.setUnit(productsOutputDataList.get(0).getUnit());
                                itemsList.remove(k);
                                val = true;
                            }
                        }
                    }
                }
                Log.d("mytext",new Gson().toJson(itemsList));


            }
        });
     /*   editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                 items=new Items();
                Log.d("myqty",productsOutputDataList.get(position).getQty());
                Log.d("myqty1",editText3.getText().toString());
                try {
                    if(!editText3.getText().toString().isEmpty()){
                        if(Double.parseDouble(productsOutputDataList.get(position).getQty())> Double.parseDouble(editText3.getText().toString())){

                            items.setItemName(textView2.getText().toString());
                            items.setQty(editText3.getText().toString());
                            items.setUnit(productsOutputDataList.get(0).getUnit());
                            itemsList.add(items);
                        }else{
                            Toast.makeText(context,"You dont have that much quantity",Toast.LENGTH_LONG).show();
                            editText3.setText(null);
                        }
                    }

                }catch (NumberFormatException ex){
                    ex.printStackTrace();
                }



            }
        });
*/
        return view;
    }



}
