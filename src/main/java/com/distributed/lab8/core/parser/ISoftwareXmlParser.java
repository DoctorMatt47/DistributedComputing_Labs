package com.distributed.lab8.core.parser;

import com.distributed.lab8.domain.entity.Producer;
import com.distributed.lab8.domain.entity.Product;

import java.util.ArrayList;

public interface ISoftwareXmlParser {

    ArrayList<Product> findProducts();

    ArrayList<Producer> findProducers();

    void save(ArrayList<Product> products, ArrayList<Producer> producers);
}
