package com.distributed.core.service.producer;

import com.distributed.infrastructure.dto.IdDto;
import com.distributed.infrastructure.dto.producer.AddProducerRequest;
import com.distributed.infrastructure.dto.producer.ProducerResponse;
import com.distributed.infrastructure.dto.producer.UpdateProducerRequest;

import java.util.ArrayList;

public interface IProducerService {

    ProducerResponse getProducerById(int id);

    ArrayList<ProducerResponse> getProducers();

    IdDto addProducer(AddProducerRequest request);

    void updateProducer(UpdateProducerRequest request);

    void deleteProducerById(int id);
}
