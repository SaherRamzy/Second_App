package com.example.saher.second_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saher on 1/11/2016.
 */
public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

    ArrayList<Restaurant> mylist;
    Context con ;
    public RestaurantAdapter(Context context, ArrayList<Restaurant> l) {
        super(context,R.layout.row_resturant,l);
        mylist=l;
        con=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layout = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =layout.inflate(R.layout.row_resturant,parent,false);


        ImageView iv_View = (ImageView) view.findViewById(R.id.iv_resturant_photo);
        TextView tvFName =  (TextView) view.findViewById(R.id.tv_resturant_name);
        TextView tvCuisines =  (TextView) view.findViewById(R.id.tv_cuisines);
        TextView tvAvgCost =  (TextView) view.findViewById(R.id.tv_avg_cost);
        TextView tvAddress =  (TextView) view.findViewById(R.id.tv_address);


        tvFName.setText(mylist.get(position).name);
        tvCuisines.setText("Cuisines: "+mylist.get(position).cuisines);
        tvAvgCost.setText("AVG. Cost for Two: " + mylist.get(position).avgCost + " " + mylist.get(position).currency);
        tvAddress.setText("Address: " + mylist.get(position).address);
        Picasso.with(con).load(mylist.get(position).img_url).into(iv_View);


        return view ;
    }
}
