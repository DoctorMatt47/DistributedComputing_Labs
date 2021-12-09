package com.distributed.client;

import com.distributed.domain.valuetype.ProductType;
import com.distributed.infrastructure.dto.producer.AddProducerRequest;
import com.distributed.infrastructure.dto.producer.UpdateProducerRequest;
import com.distributed.infrastructure.dto.product.AddProductRequest;
import com.distributed.infrastructure.dto.product.UpdateProductRequest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public class CustomJsonParser {
    public AddProducerRequest getAddProducer(String request) throws IOException {
        var parser = new JsonFactory().createParser(request);
        String name = "";
        while(!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();

            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = parser.getCurrentName();
                System.out.println(fieldName);

                jsonToken = parser.nextToken();

                if ("name".equals(fieldName)) {
                    name = parser.getValueAsString();
                }
            }
        }
        return new AddProducerRequest(name);
    }

    public AddProductRequest getAddProduct(String request) throws IOException {
        var parser = new JsonFactory().createParser(request);
        var producerId = -1;
        var name = "";
        var price = -1;
        var type = -1;
        while(!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();

            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = parser.getCurrentName();
                System.out.println(fieldName);
                parser.nextToken();

                if ("producerId".equals(fieldName)) {
                    producerId = parser.getValueAsInt();
                }
                if ("name".equals(fieldName)) {
                    name = parser.getValueAsString();
                }
                if ("price".equals(fieldName)) {
                    price = parser.getValueAsInt();
                }
                if ("type".equals(fieldName)) {
                    type = parser.getValueAsInt();
                }
            }
        }
        return new AddProductRequest(producerId, name, price, ProductType.values()[type]);
    }

    public UpdateProducerRequest getUpdateProducer(String request) throws IOException {
        var parser = new JsonFactory().createParser(request);
        var id = -1;
        var name = "";
        while(!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();

            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = parser.getCurrentName();
                System.out.println(fieldName);
                parser.nextToken();

                if ("id".equals(fieldName)) {
                    id = parser.getValueAsInt();
                }
                if ("name".equals(fieldName)) {
                    name = parser.getValueAsString();
                }
            }
        }
        return new UpdateProducerRequest(id, name);
    }

    public UpdateProductRequest getUpdateProduct(String request) throws IOException {
        var parser = new JsonFactory().createParser(request);
        var id = -1;
        var name = "";
        var price = -1;
        var type = -1;
        while(!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();

            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = parser.getCurrentName();
                System.out.println(fieldName);
                parser.nextToken();

                if ("id".equals(fieldName)) {
                    id = parser.getValueAsInt();
                }
                if ("name".equals(fieldName)) {
                    name = parser.getValueAsString();
                }
                if ("price".equals(fieldName)) {
                    price = parser.getValueAsInt();
                }
                if ("type".equals(fieldName)) {
                    type = parser.getValueAsInt();
                }
            }
        }
        return new UpdateProductRequest(id, name, price, ProductType.values()[type]);
    }

    public int getId(String request) throws IOException {
        var parser = new JsonFactory().createParser(request);
        var id = -1;
        while(!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();

            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = parser.getCurrentName();
                System.out.println(fieldName);
                parser.nextToken();

                if ("id".equals(fieldName)) {
                    id = parser.getValueAsInt();
                }
            }
        }
        return id;
    }

}
