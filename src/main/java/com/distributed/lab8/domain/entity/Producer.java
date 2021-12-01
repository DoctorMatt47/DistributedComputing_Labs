package com.distributed.lab8.domain.entity;

import java.util.ArrayList;

public class Producer {
    private int id;
    private String name;

    private ArrayList<Product> products = new ArrayList<>();

    public Producer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Producer(int id, String name, ArrayList<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
