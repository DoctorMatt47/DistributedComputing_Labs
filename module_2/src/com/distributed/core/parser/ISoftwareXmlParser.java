package com.distributed.core.parser;

import com.distributed.domain.entity.Producer;
import com.distributed.domain.entity.Product;

import java.util.ArrayList;

public interface ISoftwareXmlParser {

    ArrayList<Product> findProducts();

    ArrayList<Producer> findProducers();

    void save(ArrayList<Product> products, ArrayList<Producer> producers);
}
