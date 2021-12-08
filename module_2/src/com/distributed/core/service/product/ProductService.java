package com.distributed.core.service.product;

import com.distributed.core.context.IContext;
import com.distributed.domain.entity.Product;
import com.distributed.domain.exception.NotFoundException;
import com.distributed.infrastructure.dto.IdDto;
import com.distributed.infrastructure.dto.product.AddProductRequest;
import com.distributed.infrastructure.dto.product.ProductResponse;
import com.distributed.infrastructure.dto.product.UpdateProductRequest;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductService implements IProductService {
    private final IContext context;

    public ProductService(IContext context) {
        this.context = context;
    }

    @Override
    public ProductResponse getProductById(int id) {
        var productOptional = context.getProducts().stream()
                .filter(x -> x.getId() == id).findFirst();
        if (productOptional.isEmpty()) {
            throw new NotFoundException("It is not product with id=" + id);
        }
        var product = productOptional.get();
        return new ProductResponse(product.getId(),
                product.getProducerId(),
                product.getName(),
                product.getPrice(),
                product.getType());
    }

    @Override
    public ArrayList<ProductResponse> getProducts() {
        return context.getProducts().stream()
                .map(x -> new ProductResponse(x.getId(),
                        x.getProducerId(),
                        x.getName(),
                        x.getPrice(),
                        x.getType()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public IdDto addProduct(AddProductRequest request) {
        var isProducerExist = context.getProducers().stream()
                .anyMatch(x -> x.getId() == request.producerId());
        if (!isProducerExist) {
            throw new NotFoundException("It is not producer with id=" + request.producerId());
        }
        var products = context.getProducts();
        var newId = 0;
        var maxIdOptional = products.stream()
                .map(Product::getId)
                .max(Integer::compareTo);
        if (maxIdOptional.isPresent()) {
            newId = maxIdOptional.get() + 1;
        }
        var productToAdd = new Product(newId,
                request.producerId(),
                request.name(),
                request.price(),
                request.type());
        products.add(productToAdd);
        context.getProducers().stream()
                .filter(x -> x.getId() == productToAdd.getProducerId())
                .findFirst().ifPresent(producer -> producer.getProducts().add(productToAdd));
        context.save();
        return new IdDto(newId);
    }

    @Override
    public void updateProduct(UpdateProductRequest request) {
        var productOptional = context.getProducts().stream()
                .filter(x -> x.getId() == request.id()).findFirst();
        if (productOptional.isEmpty()) {
            throw new NotFoundException("It is not product with id=" + request.id());
        }
        var product = productOptional.get();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setType(request.type());
        context.save();
    }

    @Override
    public void deleteProductById(int id) {
        var products = context.getProducts();
        var productOptional = products.stream()
                .filter(x -> x.getId() == id).findFirst();
        if (productOptional.isEmpty()) {
            throw new NotFoundException("It is not product with id=" + id);
        }
        var product = productOptional.get();
        context.getProducers().stream()
                .filter(x -> x.getId() == product.getProducerId())
                .findFirst().ifPresent(x -> x.getProducts().remove(product));
        products.remove(product);
        context.save();
    }
}
