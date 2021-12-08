package com.distributed.infrastructure.dto.product;

import com.distributed.domain.valuetype.ProductType;

public record ProductResponse(int id,
                              int producerId,
                              String name,
                              int price,
                              ProductType type) {
}
