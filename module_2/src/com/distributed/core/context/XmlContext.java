package com.distributed.core.context;

import com.distributed.core.parser.ISoftwareXmlParser;
import com.distributed.core.validator.IXmlValidator;
import com.distributed.domain.entity.Producer;
import com.distributed.domain.entity.Product;
import com.distributed.domain.exception.InvalidXmlException;

import java.util.ArrayList;

public class XmlContext implements IXmlContext {
    private ArrayList<Producer> producers;
    private ArrayList<Product> products;
    private final IXmlValidator validator;
    private final ISoftwareXmlParser parser;
    private final String xmlPath;

    public XmlContext(IXmlValidator validator, ISoftwareXmlParser parser, String xmlPath) {
        this.validator = validator;
        this.parser = parser;
        this.xmlPath = xmlPath;
        init();
    }

    private void init() {
        if (!validator.validate(xmlPath)) {
            throw new InvalidXmlException("Xml " + xmlPath +
                    " does not correlate with xsd scheme");
        }
        producers = parser.findProducers();
        products = parser.findProducts();
        for (var producer : producers) {
            products.stream().filter(x -> x.getProducerId() == producer.getId())
                    .forEach(x -> producer.getProducts().add(x));
        }
    }

    @Override
    public ArrayList<Producer> getProducers() {
        return producers;
    }

    @Override
    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public void save() {
        this.parser.save(this.products, this.producers);
    }
}
