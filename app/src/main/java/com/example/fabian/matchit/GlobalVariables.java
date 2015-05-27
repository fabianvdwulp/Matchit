package com.example.fabian.matchit;

/**
 * Created by fabian on 30-03-15.
 */
public abstract class GlobalVariables {

    // Login
    public static boolean INGELOGD = false;

    // Match Online
    public static String BearerMatch                = "";
    public static String LANGUAGE                   = "";
    ////// URL
    public static final String URL_MATCH            = "http://web03.nextrelease.match-online.nl/api";
    public static String URL_MATCH_Token            = "http://web03.nextrelease.match-online.nl/Token";
    public static final String URL_FABIAN            = "http://www.fabianvanderwulp.nl";
    public static String URL_CATEGORIES             = URL_MATCH + "/Category";
    public static String URL_BARCODE                = URL_MATCH + "/Barcode?";
    public static String URL_PRODUCTS               = URL_MATCH + "/Product/Page?CategoryId=";
    public static String URL_PRODUCTSINGLE          = URL_MATCH + "/Product/PublicId?";
    public static String URL_ORDERS                 = URL_MATCH + "/Order";
    public static String URL_ORDERLINE              = URL_MATCH + "/Orderline";
    public static String URL_ORDERLINE_ID           = URL_MATCH + "/Orderline?OrderId=";
    public static String URL_SUBMIT                 = URL_MATCH + "/Order/Submit";
    public static String URL_PATHS                  = URL_FABIAN + "/paden.json";

    public static String SELECTED_SHOPPING_CART     = "0";
    public static String DELIVERYDETAILSID          = "0";
    public static String EARLIESTDELIVERYDATETIME   = "0";
    public static String URL_DELIVERY_DATES         = URL_MATCH + "/Delivery";

    public static String URL_PICTURES               = "http://img20.match-test.nl/";
    public static String URL_PICTUREID_SMALL        = URL_PICTURES + "16/";
    public static String URL_PICTUREID_MED          = URL_PICTURES + "92/";
    public static String URL_PICTUREID_LRG          = URL_PICTURES + "240/";
    public static String URL_PICTUREID              = URL_PICTURES + "Full/";


}


