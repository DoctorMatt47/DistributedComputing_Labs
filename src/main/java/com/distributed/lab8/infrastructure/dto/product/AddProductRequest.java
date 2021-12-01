package com.distributed.lab8.infrastructure.dto.product;

import com.distributed.lab8.domain.entity.ProductType;

public record AddProductRequest(int producerId,
                                String name,
                                int price,
                                ProductType type) {
}
