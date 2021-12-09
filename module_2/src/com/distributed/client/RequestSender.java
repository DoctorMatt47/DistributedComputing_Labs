package com.distributed.client;

import com.fasterxml.jackson.core.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RequestSender {
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final CustomJsonParser parser;

    public RequestSender(ObjectInputStream in, ObjectOutputStream out, CustomJsonParser parser) {
        this.in = in;
        this.out = out;
        this.parser = parser;
    }

    public String send(int source, int entity, int operation, int connection, String request) throws IOException {
        return switch (connection) {
            case 0 -> sendBySocket(source, entity, operation, request);
            default -> throw new IllegalStateException("Unexpected value: " + connection);
        };
    }

    private String sendBySocket(int source, int entity, int operation, String request) throws IOException {
        out.writeInt(source);
        return switch (entity) {
            case 0 -> sendProducerBySocket(operation, request);
            case 1 -> sendProductBySocket(operation, request);
            default -> throw new IllegalStateException("Unexpected value: " + entity);
        };
    }

    private String sendProducerBySocket(int operation, String request) throws IOException {
        return switch (operation) {
            case 0 -> getAllProducerBySocket();
            case 1 -> getByIdProducerBySocket(request);
            case 2 -> addProducerBySocket(request);
            case 3 -> updateProducerBySocket(request);
            case 4 -> deleteProducerBySocket(request);
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        };
    }

    private String sendProductBySocket(int operation, String request) throws IOException {
        return switch (operation) {
            case 0 -> getAllProductBySocket();
            case 1 -> getByIdProductBySocket(request);
            case 2 -> addProductBySocket(request);
            case 3 -> updateProductBySocket(request);
            case 4 -> deleteProductBySocket(request);
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        };
    }

    private String getAllProductBySocket() throws IOException {
        out.writeInt(1);
        out.writeInt(0);
        out.flush();
        var stringBuilder = new StringBuilder();
        try {
            var size = in.readInt();
            for (int i = 0; i < size; i++) {
                var object = in.readObject();
                stringBuilder.append(object.toString());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String getByIdProductBySocket(String request) throws IOException {
        out.writeInt(1);
        out.writeInt(1);
        out.writeInt(-1);
        out.flush();
        try {
            var object = in.readObject();
            return object.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String addProductBySocket(String request) throws IOException {
        out.writeInt(1);
        out.writeInt(2);
        out.writeObject(parser.getAddProduct(request));
        out.flush();
        try {
            var object = in.readObject();
            return object.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String updateProductBySocket(String request) throws IOException {
        out.writeInt(1);
        out.writeInt(3);
        out.writeObject(parser.getAddProduct(request));
        out.flush();
        try {
            var object = in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String deleteProductBySocket(String request) throws IOException {
        out.writeInt(1);
        out.writeInt(4);
        out.writeInt(parser.getId(request));
        out.flush();
        try {
            in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getAllProducerBySocket() throws IOException {
        out.writeInt(0);
        out.writeInt(0);
        out.flush();
        var stringBuilder = new StringBuilder();
        try {
            var size = in.readInt();
            for (int i = 0; i < size; i++) {
                var object = in.readObject();
                stringBuilder.append(object.toString());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String getByIdProducerBySocket(String request) throws IOException {
        out.writeInt(0);
        out.writeInt(1);
        out.writeInt(parser.getId(request));
        out.flush();
        try {
            var object = in.readObject();
            return object.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String addProducerBySocket(String request) throws IOException {
        out.writeInt(0);
        out.writeInt(2);
        out.writeObject(parser.getAddProducer(request));
        out.flush();
        try {
            var object = in.readObject();
            return object.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String updateProducerBySocket(String request) throws IOException {
        out.writeInt(0);
        out.writeInt(3);
        out.writeObject(parser.getUpdateProducer(request));
        out.flush();
        var object = in.readInt();
        return "";
    }

    private String deleteProducerBySocket(String request) throws IOException {
        out.writeInt(0);
        out.writeInt(4);
        out.writeInt(parser.getId(request));
        out.flush();
        try {
            in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}
