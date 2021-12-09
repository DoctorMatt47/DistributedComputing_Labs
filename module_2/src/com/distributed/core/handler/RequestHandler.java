package com.distributed.core.handler;

import com.distributed.core.service.producer.IProducerService;
import com.distributed.core.service.product.IProductService;
import com.distributed.domain.valuetype.EntityCode;
import com.distributed.domain.valuetype.OperationCode;
import com.distributed.infrastructure.dto.producer.AddProducerRequest;
import com.distributed.infrastructure.dto.producer.UpdateProducerRequest;
import com.distributed.infrastructure.dto.product.AddProductRequest;
import com.distributed.infrastructure.dto.product.UpdateProductRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RequestHandler implements IRequestHandler {
    private final IProducerService producerService;
    private final IProductService productService;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    public RequestHandler(IProducerService producerService,
                          IProductService productService,
                          ObjectInputStream in,
                          ObjectOutputStream out) {
        this.producerService = producerService;
        this.productService = productService;
        this.in = in;
        this.out = out;
    }

    @Override
    public void handle() throws IOException {
        var entityCode = EntityCode.values()[in.readInt()];
        var operationCode = OperationCode.values()[in.readInt()];
        if (entityCode == EntityCode.PRODUCT) {
            switch (operationCode) {
                case ADD -> addProduct();
                case DELETE -> deleteProduct();
                case UPDATE -> updateProduct();
                case GET_ALL -> getAllProduct();
                case GET_BY_ID -> getByIdProduct();
            }
        }
        else if (entityCode == EntityCode.PRODUCER) {
            switch (operationCode) {
                case ADD -> addProducer();
                case DELETE -> deleteProducer();
                case UPDATE -> updateProducer();
                case GET_ALL -> getAllProducer();
                case GET_BY_ID -> getByIdProducer();
            }
        }
    }

    private void getAllProduct() throws IOException {
        var products = productService.getProducts();

        out.writeInt(products.size());
        for (var product : products)
            out.writeObject(product);
        out.flush();
    }

    private void getByIdProduct() throws IOException {
        var productId = in.readInt();
        var product = productService.getProductById(productId);

        out.writeObject(product);
        out.flush();
    }

    private void addProduct() throws IOException {
        AddProductRequest product = null;
        try {
            product = (AddProductRequest) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

       var id = productService.addProduct(product);

        out.writeObject(id);
        out.flush();
    }

    private void updateProduct() throws IOException {
        UpdateProductRequest product = null;
        try {
            product = (UpdateProductRequest) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        productService.updateProduct(product);

        out.writeObject(0);
        out.flush();
    }

    private void deleteProduct() throws IOException {
        var productId = in.readInt();

        productService.deleteProductById(productId);

        out.writeObject(0);
        out.flush();
    }

    private void getAllProducer() throws IOException {
        var producers = producerService.getProducers();

        out.writeInt(producers.size());
        for (var product : producers)
            out.writeObject(product);
        out.flush();
    }

    private void getByIdProducer() throws IOException {
        var producerId = in.readInt();
        var producer = producerService.getProducerById(producerId);

        out.writeObject(producer);
        out.flush();
    }

    private void addProducer() throws IOException {
        AddProducerRequest producer = null;
        try {
            producer = (AddProducerRequest) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        var id = producerService.addProducer(producer);

        out.writeObject(id);
        out.flush();
    }

    private void updateProducer() throws IOException {
        UpdateProducerRequest producer = null;
        try {
            producer = (UpdateProducerRequest) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        producerService.updateProducer(producer);

        out.writeObject(0);
        out.flush();
    }

    private void deleteProducer() throws IOException {
        var producerId = in.readInt();

        producerService.deleteProducerById(producerId);

        out.writeObject(0);
        out.flush();
    }
}
