package com.distributed.infrastructure.dto.product;

import com.distributed.domain.valuetype.ProductType;

import java.io.Serializable;

public record UpdateProductRequest(int id,
                                   String name,
                                   int price,
                                   ProductType type) implements Serializable {
}
