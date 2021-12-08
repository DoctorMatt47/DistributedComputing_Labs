package com.distributed.infrastructure.dto.product;

import com.distributed.domain.valuetype.ProductType;

public record UpdateProductRequest(int id,
                                   String name,
                                   int price,
                                   ProductType type) {
}
