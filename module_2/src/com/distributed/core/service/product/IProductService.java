package com.distributed.core.service.product;


import com.distributed.infrastructure.dto.IdDto;
import com.distributed.infrastructure.dto.product.AddProductRequest;
import com.distributed.infrastructure.dto.product.ProductResponse;
import com.distributed.infrastructure.dto.product.UpdateProductRequest;

import java.util.ArrayList;

public interface IProductService {

    ProductResponse getProductById(int id);

    ArrayList<ProductResponse> getProducts();

    IdDto addProduct(AddProductRequest request);

    void updateProduct(UpdateProductRequest request);

    void deleteProductById(int id);
}
