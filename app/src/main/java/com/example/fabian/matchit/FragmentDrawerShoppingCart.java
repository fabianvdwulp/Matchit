//------------------------------------------------------------------------
// <copyright file="MyActivity.java" company="Advisor ICT Solutions">
// Advisor ICT Solutions, Mijdrecht, The Netherlands. All rights reserved.
// </copyright>
//------------------------------------------------------------------------

package com.example.fabian.matchit;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentDrawerShoppingCart extends Fragment{

    View v;

    // Order
    ListView lvShoppingCartItems;
    private ArrayList<HashMap<String, String>>      alOrders;
    private HashMap<String, String>                 hmOrders;
    public JSONObject                               joOrderURL;
    static String                                   stTotal, stCurrency, stName;
    private String                                  stEstimatedTrolleys,  stFunction = "";
    static double                                   dEsitmatedTrolleys;
    private TextView                                tvAmount;
    private lvAdapterShoppingCartSelected           aListShoppingCartSelected;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.drawer_shopping_cart, container, false);
        tvAmount = (TextView) v.findViewById(R.id.tvTotalShoppingCardAmount);
        lvShoppingCartItems = (ListView) v.findViewById(R.id.lvShoppingCartItems);

        new getProducts().execute();

        return v;
    }
    public class getProducts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            JSONObject joProducts = JSONURLObjectGET.getJSONfromURL(GlobalVariables.URL_SHOPPING);
            try {
                JSONArray jaOrders = joProducts.getJSONArray("Orders");
                if( jaOrders.length() != 0) {
                    JSONObject joOrder = jaOrders.getJSONObject(0);
                    GlobalVariables.SELECTED_SHOPPING_CART = joOrder.getString("Id");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("joprodu7cts", String.valueOf(joProducts));


            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            new getOrderId().execute();
        }


    }

    private class getOrderId extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            alOrders = new ArrayList<HashMap<String, String>>();

            try {
                joOrderURL = JSONURLObjectGET.getJSONfromURL(GlobalVariables.URL_ORDERS+"/"+GlobalVariables.SELECTED_SHOPPING_CART);
                Log.i("order", String.valueOf(joOrderURL));
                stName = joOrderURL.getString("Name");
                dEsitmatedTrolleys = Double.parseDouble(joOrderURL.getString("EstimatedTrolleys"));
                stEstimatedTrolleys.format("%.2f", dEsitmatedTrolleys);
                JSONObject joPriceProducts = joOrderURL.getJSONObject("PriceProducts");
                stCurrency = joPriceProducts.getString("Currency");
                stTotal = joPriceProducts.getString("Value");
                JSONObject joDelivery = joOrderURL.getJSONObject("Delivery");
                JSONObject joDeliveryDetails = joDelivery.getJSONObject("Details");
                GlobalVariables.DELIVERYDETAILSID = joDeliveryDetails.getString("Id");
                String stEDT = joDelivery.getString("EarliestDeliveryDateTime");
                GlobalVariables.EARLIESTDELIVERYDATETIME = stEDT.substring(0,stEDT.length()-6);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args)
        {
            new getOrderLine().execute();
        }
    }

    private class getOrderLine extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pdMessage(getResources().getString(R.string.getOrderline), true);
        }

        @Override
        protected Void doInBackground(Void... params) {

            alOrders = new ArrayList<HashMap<String, String>>();

            JSONObject joOrderURL = JSONURLObjectGET.getJSONfromURL(GlobalVariables.URL_ORDERLINE_ID + GlobalVariables.SELECTED_SHOPPING_CART);
            Log.i("orderurk", String.valueOf(joOrderURL));
            try {
                JSONArray jaItems = joOrderURL.getJSONArray("Items");
                for(int i = 0; i < jaItems.length(); i++){
                    JSONObject joItem = jaItems.getJSONObject(i);
                    JSONObject joPriceTotal = joItem.getJSONObject("PriceTotal");
                    JSONObject joProductLine = joItem.getJSONObject("ProductLine");
                    JSONObject joProduct = joProductLine.getJSONObject("Product");
                    String stId = joItem.getString("Id");
                    Log.i("prodiud", stId);
                    String stName = joProduct.getString("Name");
                    String stUnits = joItem.getString("Units");
                    JSONArray jaPictureId = joProductLine.getJSONArray("PictureIds");
                    String stPictureId = jaPictureId.getString(0);
                    String stValue = joPriceTotal.getString("Value");
                    String stCurrency = joPriceTotal.getString("Currency");
                    hmOrders = new HashMap<String, String>();
                    hmOrders.put("Id", stId);
                    hmOrders.put("Name", stName);
                    hmOrders.put("Value", stValue);
                    hmOrders.put("PictureId", stPictureId);
                    hmOrders.put("Currency", stCurrency);
                    hmOrders.put("Units", stUnits);
                    alOrders.add(hmOrders);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args)
        {
            stFunction = "getOrderId";
            tvAmount.setText(" " + stCurrency+ " " + stTotal);
            aListShoppingCartSelected = new lvAdapterShoppingCartSelected(getActivity(), alOrders);
            lvShoppingCartItems.setAdapter(aListShoppingCartSelected);

        }
    }




}