package com.example.fabian.matchit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.example.fabian.matchit.JSONURL.JSONURLObjectGET;
import com.example.fabian.matchit.JSONURL.JSONURLObjectPOST;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityProductSingle extends ABaseActivity {

    // Layout
    private CircularProgressButton      circularButton1;
    private TextView                    tvShoppingCounter, tvProductName, tvTotalPrice, tvAvailable;;
    private EditText                    etBuyAmount;
    private ImageView                   ivProductImage, ivHeader;

    // Product
    private String                      stCategoryId, stProductId, stProductName, stPictureId, stCurrency = null, stRememberAmount, stCurrencyISO = null, stValue = null, stUnit = null;;
    private int                         iPiecesPerUnit, iPiecesAvailable, iLayersPerTrolley, iUnitsPerLayer, iStemsPerBunch;

    // ProductDetails
    private HashMap<String, String>            hmProducts;
    private ArrayList<HashMap<String, String>> FCategoryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_single);

        createToolbar();
        getShopping();

        Intent in = getIntent();
        String nfcData = in.getStringExtra("json");

        try {
            JSONObject joNFC = new JSONObject(nfcData);
            stProductId = joNFC.getString("publicId");
            stCategoryId = joNFC.getString("categoryId");
            Log.i("stProductId", stProductId);
            Log.i("stCategoryId", stCategoryId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        createToolbar();
        // Set the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentTabsProduct fragment = new FragmentTabsProduct();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();

        // Layout
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvAvailable = (TextView) findViewById(R.id.tvAvailable);
        ivProductImage = (ImageView) findViewById(R.id.product_image);
        ivHeader = (ImageView) findViewById(R.id.header_image);
        tvShoppingCounter = (TextView) findViewById(R.id.tvShoppingCounter);
        tvTotalPrice = (TextView) findViewById(R.id.tvPrijs);
        etBuyAmount = (EditText) findViewById(R.id.etBuyAmount);
        circularButton1 = (CircularProgressButton) findViewById(R.id.btnProgress);
        // Set button listener
        circularButton1.setProgress(0);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*
                BigDecimal shoppingCardAmount = new BigDecimal(tvShoppingCounter.getText().toString());
                String stBuyAmount = etBuyAmount.getText().toString();
                BigDecimal buyAmount = new BigDecimal(stBuyAmount);
                shoppingCardAmount = shoppingCardAmount.add(buyAmount);

                tvShoppingCounter.setText(shoppingCardAmount.toString());
                */
                new postOrderline().execute();

            }
        });
        // Veld leegmaken als klant aantal wilt invullen. Scheelt een handeling
        etBuyAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    if (etBuyAmount.getText().toString().equals("0")) {
                        etBuyAmount.setText("");
                    }
                }
            }
        });

        new getProducts().execute();

    }

    public class getProducts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdMessage(getResources().getString(R.string.getProducten), true);
        }

        @Override
        protected Void doInBackground(Void... params) {

            FCategoryItems = new ArrayList<HashMap<String, String>>();
            JSONObject joProducts = JSONURLObjectGET.getJSONfromURL(GlobalVariables.URL_PRODUCTSINGLE + "PublicId=" + stProductId + "&CategoryId=" + stCategoryId);
            Log.i("joprodu7cts", String.valueOf(joProducts));
            try {
                JSONArray jaProductItems = joProducts.getJSONArray("Items");

                JSONObject joCategory = jaProductItems.getJSONObject(0);
                JSONObject joItems = joCategory.getJSONObject("Product");

                // Product foto ophalen
                JSONArray jaPictureId = joCategory.getJSONArray("PictureIds");
                stPictureId = jaPictureId.getString(0);
                // Productnaam ophalen
                stProductName = joItems.getString("Name");

                // Product informatie hoeveelheden er beschikbaar zijn en wat ze kosten
                JSONArray jaPrices = joCategory.getJSONArray("Prices");

                iPiecesPerUnit = joCategory.getInt("PiecesPerUnit");
                iPiecesAvailable = joCategory.optInt("PiecesAvailable");
                iLayersPerTrolley = joCategory.getInt("LayersPerTrolley");
                iUnitsPerLayer = joCategory.getInt("UnitsPerLayer");
                iStemsPerBunch = joCategory.getInt("StemsPerBunch");

                for (int i = 0; i < jaPrices.length(); i++) {
                    JSONObject joPrice = jaPrices.getJSONObject(0);
                    stCurrencyISO = joPrice.getString("CurrencyISO");
                    stCurrency = joPrice.getString("Currency");
                    stValue = joPrice.getString("Value");
                    stUnit = joPrice.getString("Unit");
                }

                String stPacking = null, stColor= null, stGrower = null, stQuality = null ;
                JSONObject joPacking = joCategory.optJSONObject("Packing");
                if(joPacking != null){
                    stPacking = joPacking.optString("Name");
                    hmProducts = new HashMap<String, String>();
                    hmProducts.put("Name", "Packing");
                    hmProducts.put("Value", stPacking);
                    FCategoryItems.add(hmProducts);
                }
                JSONObject joColor = joCategory.optJSONObject("Color");
                if(joColor != null){
                    stColor = joColor.optString("Name");
                    hmProducts = new HashMap<String, String>();
                    hmProducts.put("Name", "Color");
                    hmProducts.put("Value", stColor);
                    FCategoryItems.add(hmProducts);
                }
                JSONObject joGrower = joCategory.optJSONObject("Grower");
                if(joGrower != null){
                    stGrower = joGrower.optString("Name");
                    hmProducts = new HashMap<String, String>();
                    hmProducts.put("Name", "Grower");
                    hmProducts.put("Value", stGrower);
                    FCategoryItems.add(hmProducts);
                }
                JSONObject joQuality = joCategory.optJSONObject("Quality");
                if(joQuality != null){
                    stQuality = joQuality.optString("Name");
                    hmProducts = new HashMap<String, String>();
                    hmProducts.put("Name", "Quality");
                    hmProducts.put("Value", stQuality);
                    FCategoryItems.add(hmProducts);
                }
                String stLength = joCategory.optString("Length");
                hmProducts = new HashMap<String, String>();
                hmProducts.put("Name", "Length");
                hmProducts.put("Value", stLength);
                FCategoryItems.add(hmProducts);
                String sPotsize = joCategory.optString("PotSize");
                hmProducts = new HashMap<String, String>();
                hmProducts.put("Name", "PotSize");
                hmProducts.put("Value", sPotsize);
                FCategoryItems.add(hmProducts);

                JSONArray jaProperties = joCategory.getJSONArray("Properties");

                for (int i = 0; i < jaProperties.length(); i++) {
                    JSONObject joProperty = jaProperties.getJSONObject(i);
                    String stPropertyName = joProperty.getString("Name");
                    String stPropertyValue = joProperty.getString("Value");
                    hmProducts = new HashMap<String, String>();
                    hmProducts.put("Name", stPropertyName);
                    hmProducts.put("Value", stPropertyValue);
                    FCategoryItems.add(hmProducts);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            pdMessage(getResources().getString(R.string.getProducten), false);
        Log.i("st", stCurrency);
            tvProductName.setText(stProductName);
            tvTotalPrice.setText("P.p.s: \u20ac" + stValue);
            tvAvailable.setText("Aantal beschikbaar: " + iPiecesAvailable);

            // Foto's op de server vallen tegen, ik overrule ze hier met mijn eigen foto's, de juiste url code voor de toekomst staat er wel bij
            if(stProductId.equals("PA3262")){
                ivProductImage.setImageResource(R.drawable.abelia_klein);
            }
            //Picasso.with(getBaseContext()).load(GlobalVariables.URL_PICTUREID + stPictureId + ".jpg").into(ivProductImage);
            //Picasso.with(getBaseContext()).load(GlobalVariables.URL_PICTUREID + stPictureId + ".jpg").into(ivHeader);
            Log.i("url", GlobalVariables.URL_PICTUREID + stPictureId + ".jpg");
        }
    }


    public class postOrderline extends AsyncTask<Void, Void, Void> {

        JSONObject joToegevoegd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdMessage(getResources().getString(R.string.addProduct), true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("hoi", GlobalVariables.URL_ORDERLINE_ID + GlobalVariables.SELECTED_SHOPPING_CART +"&ProductId="+stProductId+"&Unit=1"+"&Amount="+etBuyAmount.getText().toString());
            JSONObject joProducts = JSONURLObjectPOST.getJSONfromURL(GlobalVariables.URL_ORDERLINE_ID + GlobalVariables.SELECTED_SHOPPING_CART + "&ProductId=" + stProductId + "&Unit=0" + "&Amount=" + etBuyAmount.getText().toString());
            joToegevoegd = joProducts.optJSONObject("PriceTotal");
            Log.i("joprodgelukt", String.valueOf(joProducts));
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            pdMessage(getResources().getString(R.string.addProduct), false);

            if(joToegevoegd == null){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.addProductFalse), Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.addProductTrue), Toast.LENGTH_SHORT).show();
                circularButton1.setProgress(100);
                etBuyAmount.setText("0");
            }
        }
    }



}
