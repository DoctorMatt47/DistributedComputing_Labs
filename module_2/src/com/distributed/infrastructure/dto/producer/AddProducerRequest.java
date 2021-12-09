package com.distributed.infrastructure.dto.producer;

import java.io.Serializable;

public record AddProducerRequest(String name) implements Serializable {
}
