package com.example.laundrymanagermobile.apiconnection;

public class Service {
    private int id;
    private String services_name, services_price, services_desc, item_name, category_name, store_name;

    public Service(int id, String services_name, String services_price, String services_desc, String item_name, String category_name, String store_name) {
        this.id = id;
        this.services_name = services_name;
        this.services_price = services_price;
        this.services_desc = services_desc;
        this.item_name = item_name;
        this.category_name = category_name;
        this.store_name = store_name;
    }

    public int getId() {
        return id;
    }

    public String getServices_name() {
        return services_name;
    }

    public String getServices_price() {
        return services_price;
    }

    public String getServices_desc() {
        return services_desc;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getStore_name() {
        return store_name;
    }
}
