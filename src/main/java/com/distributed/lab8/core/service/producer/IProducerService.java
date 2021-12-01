package com.distributed.lab8.core.service.producer;

import com.distributed.lab8.infrastructure.dto.producer.*;

import java.util.ArrayList;

public interface IProducerService {

    ProducerResponse getProducerById(int id);

    ArrayList<ProducerResponse> getProducers();

    AddProducerResponse addProducer(AddProducerRequest request);

    UpdateProducerResponse updateProducer(UpdateProducerRequest request);

    void deleteProducerById(int id);
}
