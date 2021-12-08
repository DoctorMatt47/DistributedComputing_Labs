package com.distributed.core.service.producer;

import com.distributed.core.context.IDatabaseContext;
import com.distributed.infrastructure.dto.IdDto;
import com.distributed.infrastructure.dto.producer.AddProducerRequest;
import com.distributed.infrastructure.dto.producer.ProducerResponse;
import com.distributed.infrastructure.dto.producer.UpdateProducerRequest;

import java.sql.SQLException;
import java.util.ArrayList;

public class DbProducerService implements IProducerService{
    private final IDatabaseContext context;

    public DbProducerService(IDatabaseContext context) {
        this.context = context;
    }

    @Override
    public ProducerResponse getProducerById(int id) {
        var result = context.executeQuery(String.format("select * from producer where id = %d", id));
        try {
            result.next();
            var producerId = result.getInt("id");
            var producerName = result.getString("name");
            return new ProducerResponse(producerId, producerName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<ProducerResponse> getProducers() {
        var result = context.executeQuery("select * from producer");
        var producers = new ArrayList<ProducerResponse>();
        try {
            while (result.next()) {
                var producerId = result.getInt("id");
                var producerName = result.getString("name");
                producers.add(new ProducerResponse(producerId, producerName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producers;
    }

    @Override
    public IdDto addProducer(AddProducerRequest request) {
        var maxIdResult = context.executeQuery("select max(id) from producer");
        try {
            maxIdResult.next();
            var maxId = maxIdResult.getInt("id");
            context.executeQuery(String.format("insert into producer values (%d, '%s')", maxId + 1, request.name()));
            return new IdDto(maxId + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateProducer(UpdateProducerRequest request) {
        context.executeQuery(String.format("update producer set name = '%s' where id = %d",
                request.name(), request.id()));
    }

    @Override
    public void deleteProducerById(int id) {
        context.executeQuery(String.format("delete from producer where id = %d", id));
    }
}
