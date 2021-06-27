package com.example.laundrymanagermobile.apiconnection;

public class Manager {
    private int id;
    private String store_username, store_email_address, store_password, store_name, store_owner, store_contact_number, store_address;

    public Manager(int id, String store_username, String store_email_address, String store_password, String store_name, String store_owner, String store_contact_number, String store_address) {
        this.id = id;
        this.store_username = store_username;
        this.store_email_address = store_email_address;
        this.store_password = store_password;
        this.store_name = store_name;
        this.store_owner = store_owner;
        this.store_contact_number = store_contact_number;
        this.store_address = store_address;
    }

    public int getId() {
        return id;
    }

    public String getStore_username() {
        return store_username;
    }

    public String getStore_email_address() {
        return store_email_address;
    }

    public String getStore_password() {
        return store_password;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStore_owner() {
        return store_owner;
    }

    public String getStore_contact_number() {
        return store_contact_number;
    }

    public String getStore_address() {
        return store_address;
    }


}
