package com.example.fabian.matchit;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fabian.matchit.JSONURL.JSONURLObjectGET;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Orderline {

    private List FListeners =                       new ArrayList();
    static String                                   stTotal, stCurrency;

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

    FragmentDrawerShoppingCart FShoppingCartSelected;
    private ArrayList<HashMap<String, String>> alOrders;
    private HashMap<String, String> hmOrders;
    public JSONObject joOrderURL;

    public ArrayList<HashMap<String, String>> getItems()
    {
        return alOrders;
    }

    public JSONObject getOrder(){
        return joOrderURL;
    }

    public Orderline(FragmentDrawerShoppingCart aShoppingCartSelected)
    {
       FShoppingCartSelected = aShoppingCartSelected;
    }

    public void Get()
    {
        new getOrderId().execute();
    }

    private class getOrderId extends AsyncTask<Void, Void, Void> {

        // boolean om te checken kijken of er een order is opgehaald
        boolean bGelukt = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            alOrders = new ArrayList<HashMap<String, String>>();

            try {
                joOrderURL = JSONURLObjectGET.getJSONfromURL(GlobalVariables.URL_ORDERS + "/" + GlobalVariables.SELECTED_SHOPPING_CART);
                JSONObject joPriceProducts = joOrderURL.getJSONObject("PriceProducts");
                stCurrency = joPriceProducts.getString("Currency");
                stTotal = joPriceProducts.getString("Value");
                JSONObject joDelivery = joOrderURL.getJSONObject("Delivery");
                JSONObject joDeliveryDetails = joDelivery.getJSONObject("Details");
                GlobalVariables.DELIVERYDETAILSID = joDeliveryDetails.getString("Id");
                String stEDT = joDelivery.getString("EarliestDeliveryDateTime");
                GlobalVariables.EARLIESTDELIVERYDATETIME = stEDT.substring(0,stEDT.length()-6);
            } catch (JSONException e) {
                // Als er wat mis gaat is er waarschijnlijk geen order aanwezig, die zijn verwijderd terwijl de app aanstaat. Er moeten wat dingen gerest worden en een nieuwe order
                // aangemaakt worden door de gebruiker.
                bGelukt = false;
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
            fireReadyEvent();
        }
    }

}

