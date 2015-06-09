package com.example.fabian.matchit;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fabian.matchit.JSONURL.JSONURLObjectGET;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Orders {

    private List FListeners = new ArrayList();

    public synchronized void addReadyEventListener(ReadyListener listener)  {
        FListeners.add(listener);
    }
    public synchronized void removeReadyEventListener(ReadyListener listener)   {
        FListeners.remove(listener);
    }

    // call this method whenever you want to notify
    //the event listeners of the particular event
    private synchronized void fireReadyEvent() {
        ReadyEvent event = new ReadyEvent(this);
        Iterator i = FListeners.iterator();
        while(i.hasNext())  {
            ((ReadyListener) i.next()).ready(event) ;
        }
    }

    FragmentDrawerShoppingCart FShoppingCartSelected2;

    public Orders(FragmentDrawerShoppingCart aShoppingCartSelected)
    {
       FShoppingCartSelected2 = aShoppingCartSelected;
    }

    public void Get()
    {
            new getOrders().execute();
    }

    public class getOrders extends AsyncTask<Void, Void, Void> {

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
                    JSONObject joDelivery = joOrder.getJSONObject("Delivery");
                    Log.i("delivery", String.valueOf(joDelivery));
                    JSONObject joDeliveryDetails = joDelivery.getJSONObject("Details");
                    String stEDT = joDelivery.getString("EarliestDeliveryDateTime");
                    GlobalVariables.DELIVERYDETAILSID = joDeliveryDetails.getString("Id");
                    GlobalVariables.EARLIESTDELIVERYDATETIME = stEDT.substring(0, stEDT.length() - 6);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            fireReadyEvent();
        }


    }


}
