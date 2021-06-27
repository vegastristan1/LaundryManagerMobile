package com.example.laundrymanagermobile.apiconnection;

public class Api {
    //globe
    //private static final String ROOT_URL = "http://192.168.254.136/HeroApi/v1/Api.php?apicall=";
    //private static final String ROOT_URL = "http://192.168.254.136/LaundryAppApi/v1/Api.php?apicall=";
    //converge
    //private static final String ROOT_URL = "http://192.168.1.15/HeroApi/v1/Api.php?apicall=";
    private static final String ROOT_URL = "http://192.168.88.125/LaundryManagerAppApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";

    //Customer Data
    public static final String URL_CREATE_CUSTOMER = ROOT_URL + "createcustomer";
    public static final String URL_READ_CUSTOMERS = ROOT_URL + "getcustomer";
    public static final String URL_UPDATE_CUSTOMER = ROOT_URL + "updatecustomer";
    public static final String URL_DELETE_CUSTOMER = ROOT_URL + "deleteacustomer&id=";

    //Manager Data
    public static final String URL_READ_STORE_MANAGERS = ROOT_URL + "getstoremanagers";
    public static final String URL_UPDATE_STORE_MANAGER = ROOT_URL + "updatestoremanager";
    public static final String URL_DELETE_STORE_MANAGER = ROOT_URL + "deleteastoremanager&id=";

    //Services Data
    public static final String URL_CREATE_SERVICE = ROOT_URL + "createservice";
    public static final String URL_READ_SERVICE = ROOT_URL + "getallservices";
    public static final String URL_READ_SERVICE_TO_ID = ROOT_URL + "getservicestoid";
    public static final String URL_READ_SERVICE_All_INFO = ROOT_URL + "getallinfoservices";
    public static final String URL_UPDATE_SERVICE = ROOT_URL + "updateservice";
    public static final String URL_DELETE_SERVICE = ROOT_URL + "deleteservice&id=";
    public static final String URL_POPULATE_SPINNER_CATEGORIES = ROOT_URL + "populatespinnercategories";
}
