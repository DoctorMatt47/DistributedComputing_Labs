package com.distributed.lab8.core.context;

import com.distributed.lab8.domain.entity.Producer;
import com.distributed.lab8.domain.entity.Product;

import java.util.ArrayList;

public interface IContext {

    ArrayList<Producer> getProducers();

    ArrayList<Product> getProducts();

    void save();
}
