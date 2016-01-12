package com.example.saher.second_app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    ProgressDialog dialog ;
    ListView lv;
    ArrayList<Restaurant> lstRestaurant;
    RestaurantAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= (ListView)findViewById(R.id.lv);
        lstRestaurant = new ArrayList<Restaurant>();
        getDataFromApi();
    }

    private void getDataFromApi() {

        new AsyncTask<Void, Void, ArrayList<Restaurant>>()
        {
            protected void onPreExecute() {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("Loading");
                dialog.setMessage("PLEASE WAIT...");
                dialog.show();

            }

            @Override
            protected ArrayList<Restaurant> doInBackground(Void... arg0) {

                ServiceHandler handler = new ServiceHandler();

                String jsonData = handler.makeServiceCall("https://developers.zomato.com/api/v2.1/search?entity_type=city/", ServiceHandler.GET,  null);
                try {
                    JSONObject jsonObj = new JSONObject(jsonData);
                    JSONArray jsonArary = jsonObj.getJSONArray("restaurants");

                    for (int i = 0; i < jsonArary.length(); i++) {
                        JSONObject jsonob2 = jsonArary.getJSONObject(i);
                        JSONObject jsonob3 = jsonob2.getJSONObject("restaurant");
                        JSONObject jsonob4 = jsonob3.getJSONObject("location");

                        String str1 = jsonob3.getString("name");
                        String str2 = jsonob3.getString("cuisines");
                        String str3 = jsonob3.getString("average_cost_for_two");
                        String str4 = jsonob3.getString("currency");
                        String str5 = jsonob3.getString("thumb");
                        String str6 = jsonob4.getString("address");

                        Restaurant restaurant = new Restaurant();
                        restaurant.name = str1;
                        restaurant.cuisines = str2;
                        restaurant.avgCost = str3;
                        restaurant.currency = str4;
                        restaurant.img_url = str5;
                        restaurant.address = str6;
                        //jk

                        lstRestaurant.add(restaurant);

                    }
                } catch (Exception ex) {}
                return lstRestaurant;
            }

            protected void onPostExecute(ArrayList<Restaurant> result) {
                dialog.dismiss();
                adapter = new RestaurantAdapter(MainActivity.this,result);
                lv.setAdapter(adapter);
            }

        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
