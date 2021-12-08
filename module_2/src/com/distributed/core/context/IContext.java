package com.distributed.core.context;

import com.distributed.domain.entity.Producer;
import com.distributed.domain.entity.Product;

import java.util.ArrayList;

public interface IContext {

    ArrayList<Producer> getProducers();

    ArrayList<Product> getProducts();

    void save();
}
