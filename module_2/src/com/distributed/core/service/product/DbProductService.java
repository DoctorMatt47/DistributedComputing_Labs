package com.distributed.core.service.product;

import com.distributed.core.context.IDatabaseContext;
import com.distributed.domain.valuetype.ProductType;
import com.distributed.infrastructure.dto.IdDto;
import com.distributed.infrastructure.dto.product.AddProductRequest;
import com.distributed.infrastructure.dto.product.ProductResponse;
import com.distributed.infrastructure.dto.product.UpdateProductRequest;

import java.sql.SQLException;
import java.util.ArrayList;

public class DbProductService implements IProductService {
    private final IDatabaseContext context;

    public DbProductService(IDatabaseContext context) {
        this.context = context;
    }

    @Override
    public ProductResponse getProductById(int id) {
        var result = context.executeQuery(
                String.format("select * from product where id = %d", id));
        try {
            result.next();
            var productId = result.getInt("id");
            var producerId = result.getInt("producerId");
            var productName = result.getString("name");
            var productPrice = result.getInt("price");
            var productType = result.getString("type");
            return new ProductResponse(productId, producerId, productName, productPrice,
                    ProductType.valueOf(productType));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<ProductResponse> getProducts() {
        var result = context.executeQuery("select * from product");
        var products = new ArrayList<ProductResponse>();
        try {
            while (result.next()) {
                var productId = result.getInt("id");
                var producerId = result.getInt("producerId");
                var productName = result.getString("name");
                var productPrice = result.getInt("price");
                var productType = result.getString("type");
                products.add(new ProductResponse(productId, producerId, productName, productPrice,
                        ProductType.valueOf(productType)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public IdDto addProduct(AddProductRequest request) {
        var maxIdResult = context.executeQuery("select max(id) from product");
        var producer = context.executeQuery(String.format("select 1 from producer where id = %d",
                request.producerId()));
        try {
            if (!producer.next() || producer.getInt(1) == 0) return null;
            maxIdResult.next();
            var maxId = maxIdResult.getInt("id");
            context.executeQuery(String.format("insert into producer values (%d, %d, '%s', %d, '%s')",
                    maxId + 1, request.producerId(), request.name(), request.price(), request.type()));
            return new IdDto(maxId + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateProduct(UpdateProductRequest request) {
        context.executeQuery(String.format("update product set " +
                        "name = '%s', price = %d, type = '%s' where id = %d",
                request.name(), request.price(), request.type().toString(), request.id()));
    }

    @Override
    public void deleteProductById(int id) {
        context.executeQuery(String.format("delete from product where id = %d", id));
    }
}
