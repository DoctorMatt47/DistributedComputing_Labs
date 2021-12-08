package com.distributed.core.service.producer;

import com.distributed.core.context.IXmlContext;
import com.distributed.domain.entity.Producer;
import com.distributed.domain.exception.NotFoundException;
import com.distributed.infrastructure.dto.IdDto;
import com.distributed.infrastructure.dto.producer.AddProducerRequest;
import com.distributed.infrastructure.dto.producer.ProducerResponse;
import com.distributed.infrastructure.dto.producer.UpdateProducerRequest;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class XmlProducerService implements IProducerService {

    private final IXmlContext context;

    public XmlProducerService(IXmlContext context) {
        this.context = context;
    }

    @Override
    public ProducerResponse getProducerById(int id) {
        var producerOptional = context.getProducers().stream()
                .filter(x -> x.getId() == id).findFirst();
        if (producerOptional.isEmpty()) {
            throw new NotFoundException("It is not producer with id=" + id);
        }
        var producerEntity = producerOptional.get();
        return new ProducerResponse(producerEntity.getId(), producerEntity.getName());
    }

    @Override
    public ArrayList<ProducerResponse> getProducers() {
        return context.getProducers().stream()
                .map(x -> new ProducerResponse(x.getId(), x.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public IdDto addProducer(AddProducerRequest request) {
        var producers = context.getProducers();
        var newId = 0;
        var maxIdOptional = producers.stream()
                .map(Producer::getId)
                .max(Integer::compareTo);
        if (maxIdOptional.isPresent()) {
            newId = maxIdOptional.get() + 1;
        }
        var producerToAdd = new Producer(newId, request.name());
        producers.add(producerToAdd);
        context.save();
        return new IdDto(newId);
    }

    @Override
    public void updateProducer(UpdateProducerRequest request) {
        var producerOptional = context.getProducers().stream()
                .filter(x -> x.getId() == request.id()).findFirst();
        if (producerOptional.isEmpty()) {
            throw new NotFoundException("It is not producer with id=" + request.id());
        }
        var producerEntity = producerOptional.get();
        producerEntity.setName(request.name());
        context.save();
    }

    @Override
    public void deleteProducerById(int id) {
        var producers = context.getProducers();
        var producerOptional = producers.stream()
                .filter(x -> x.getId() == id).findFirst();
        if (producerOptional.isEmpty()) {
            throw new NotFoundException("It is not producer with id=" + id);
        }
        var producer = producerOptional.get();
        for (var product : producer.getProducts()) {
            context.getProducts().remove(product);
        }
        producers.remove(producer);
        context.save();
    }
}
