package com.distributed.infrastructure.dto.producer;

import java.io.Serializable;

public record ProducerResponse(int id, String name) implements Serializable {
}
