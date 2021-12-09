package com.distributed.infrastructure.dto.product;

import com.distributed.domain.valuetype.ProductType;

import java.io.Serializable;

public record AddProductRequest(int producerId,
                                String name,
                                int price,
                                ProductType type) implements Serializable {
}
