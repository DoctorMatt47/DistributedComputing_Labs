package com.distributed.infrastructure.dto.producer;

import java.io.Serializable;

public record UpdateProducerRequest(int id, String name) implements Serializable {
}
