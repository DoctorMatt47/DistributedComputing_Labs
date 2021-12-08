package com.distributed.lab8.infrastructure.dto.product;

import com.distributed.lab8.domain.valuetype.ProductType;

public record UpdateProductRequest(int id,
                                   String name,
                                  int price,
                                  ProductType type) {
}
